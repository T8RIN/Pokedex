package ru.tech.pokedex.fragment.pokelist

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.tech.pokedex.data.model.PokedexListEntry
import ru.tech.pokedex.extensions.Extensions.capitalized
import ru.tech.pokedex.functions.Functions.containsAt
import ru.tech.pokedex.repository.PokemonRepository
import ru.tech.pokedex.utils.Resource
import ru.tech.pokedex.utils.Values.PAGE_SIZE
import ru.tech.pokedex.utils.Values.SPRITE_URL
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private var currentPage = 0

    var isFinding = false

    val pokemonList: MutableLiveData<ArrayList<PokedexListEntry>> by lazy {
        MutableLiveData<ArrayList<PokedexListEntry>>()
    }

    val searchList: MutableLiveData<ArrayList<PokedexListEntry>> by lazy {
        MutableLiveData<ArrayList<PokedexListEntry>>()
    }

    private var allPokemons: ArrayList<PokedexListEntry> = ArrayList()

    val loadError: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }

    var isLoading = false
    var endReached = false

    init {
        loadPokemonList()
        initAllPokemons()
    }

    private fun initAllPokemons() {
        viewModelScope.launch {
            when (val result = repository.getPokemonList(1118, 0)) {
                is Resource.Success -> {
                    val pokedexEntries = result.data!!.results.mapIndexed { _, entry ->
                        val number = if (entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url.takeLastWhile { it.isDigit() }
                        }
                        val url = SPRITE_URL.replace("*", number)
                        PokedexListEntry(entry.name.capitalized(), url, number.toInt())
                    }
                    allPokemons = ArrayList(pokedexEntries)
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                }
            }
        }
    }

    fun loadPokemonList() {
        viewModelScope.launch {
            isLoading = true
            when (val result = repository.getPokemonList(PAGE_SIZE, currentPage * PAGE_SIZE)) {
                is Resource.Success -> {
                    endReached = currentPage * PAGE_SIZE >= result.data!!.count
                    val pokedexEntries = result.data.results.mapIndexed { _, entry ->
                        val number = if (entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url.takeLastWhile { it.isDigit() }
                        }
                        val url = SPRITE_URL.replace("*", number)
                        PokedexListEntry(entry.name.capitalized(), url, number.toInt())
                    }
                    currentPage++

                    loadError.value = ""
                    isLoading = false
                    if (pokemonList.value == null) {
                        pokemonList.value = ArrayList(pokedexEntries)
                    } else {
                        pokemonList.value = ArrayList(pokemonList.value!! + pokedexEntries)
                    }

                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading = false
                }
            }
        }
    }

    fun searchForPokemon(query: String) {
        viewModelScope.launch {
            isLoading = true
            val tempArr: ArrayList<PokedexListEntry> = ArrayList()
            val pairList: ArrayList<Pair<PokedexListEntry, Int>> = ArrayList()

            allPokemons.forEach {
                val index = it.pokemonName.lowercase().containsAt(query.lowercase())
                if (index != 101) {
                    pairList.add(Pair(it, index))
                }
            }

            pairList.sortBy { it.second }
            pairList.forEach { tempArr.add(it.first) }

            searchList.value = tempArr
            isLoading = false
        }
    }

    fun getDominantColor(drawable: Drawable, onFinish: (Int) -> Unit) {
        val bitmap = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bitmap).generate { palette ->
            palette?.getDominantColor(Color.WHITE)?.let { onFinish(it) }
        }
    }

}