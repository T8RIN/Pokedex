package ru.tech.pokedex.fragment.pokelist

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.tech.pokedex.data.model.PokedexListEntry
import ru.tech.pokedex.extensions.Extensions.capitalize
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

    var pokemonList: ArrayList<PokedexListEntry> = ArrayList()
    var loadError = ""
    var isLoading = false
    var endReached = false

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
                        PokedexListEntry(entry.name.capitalize(), url, number.toInt())
                    }
                    currentPage++

                    loadError = ""
                    isLoading = false
                    pokemonList.addAll(pokedexEntries)
                }
                is Resource.Error -> {
                    loadError = result.message!!
                    isLoading = false
                }
            }
        }
    }

    fun getDominantColor(drawable: Drawable, onFinish: (Int) -> Unit) {
        val bitmap = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bitmap).generate { palette ->
            palette?.getDominantColor(Color.WHITE)?.let { onFinish(it) }
        }
    }

}