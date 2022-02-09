package ru.tech.pokedex.fragment.pokelist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.tech.pokedex.R
import ru.tech.pokedex.activity.MainActivity
import ru.tech.pokedex.adapter.PokemonListAdapter
import ru.tech.pokedex.databinding.PokemonListFragmentBinding
import ru.tech.pokedex.fragment.details.PokemonDetailsFragment

@AndroidEntryPoint
class PokemonListFragment : Fragment() {

    private var _binding: PokemonListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<PokemonListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PokemonListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PokemonListAdapter(requireContext(), viewModel, this)
        binding.pokemonRecycler.itemAnimator = DefaultItemAnimator()
        binding.pokemonRecycler.adapter = adapter
        val layoutManager = binding.pokemonRecycler.layoutManager as GridLayoutManager

        viewModel.pokemonList.observe(viewLifecycleOwner) {
            adapter.addData(it)
        }

        viewModel.searchList.observe(viewLifecycleOwner) {
            if (!viewModel.isFinding) {
                adapter.pokemonList = viewModel.pokemonList.value!!
            } else {
                adapter.pokemonList = it
            }
            adapter.notifyDataSetChanged()
        }

        binding.pokemonRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!viewModel.isLoading && !viewModel.endReached) {
                    val curPos = layoutManager.findLastCompletelyVisibleItemPosition()
                    if (curPos == viewModel.pokemonList.value!!.size - 1) {
                        viewModel.loadPokemonList()
                    }
                }
            }
        })

        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                layoutManager.scrollToPositionWithOffset(positionStart, 0)
            }
        })

        binding.searchField.addTextChangedListener(onTextChanged = { s, _, _, _ ->
            val search = s.toString().trim().replace("null", "")
            viewModel.isFinding = search.isNotEmpty()
            searchDebounced(search)
        })

    }

    private var searchJob: Job? = null

    private fun searchDebounced(searchText: String) {
        searchJob?.cancel()
        searchJob = viewModel.viewModelScope.launch {
            delay(300)
            viewModel.searchForPokemon(searchText)
        }
    }

    fun openDetails(pokemonName: String, dominantColor: Int) {
        val fragment = PokemonDetailsFragment()
        fragment.arguments = bundleOf(Pair("name", pokemonName), Pair("color", dominantColor))

        val tag = "pokemon_info"
        (requireActivity() as MainActivity).supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                fragment,
                tag
            )
            .addToBackStack(tag)
            .commit()
    }

}