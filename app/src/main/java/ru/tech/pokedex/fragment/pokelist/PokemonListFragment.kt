package ru.tech.pokedex.fragment.pokelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.request.ImageRequest
import dagger.hilt.android.AndroidEntryPoint
import ru.tech.pokedex.databinding.PokemonListFragmentBinding

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}