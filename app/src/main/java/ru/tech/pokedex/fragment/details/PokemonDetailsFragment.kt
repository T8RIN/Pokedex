package ru.tech.pokedex.fragment.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import coil.size.Scale
import coil.transform.CircleCropTransformation
import dagger.hilt.android.AndroidEntryPoint
import ru.tech.pokedex.databinding.PokemonDetailsFragmentBinding

@AndroidEntryPoint
class PokemonDetailsFragment : Fragment() {

    private var _binding: PokemonDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<PokemonDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PokemonDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val color = requireArguments().getInt("color")
        val pokemonName = requireArguments().getString("name", "")!!

        viewModel.pokemonInfo.observe(viewLifecycleOwner) {
            binding.pokemonName.text = "#${it.id} $pokemonName"
            binding.pokemonImage.load(it.sprites.front_default) {
                crossfade(300)
                transformations(CircleCropTransformation())
                scale(Scale.FILL)
            }
        }

        viewModel.getPokemonInfo(pokemonName)

        binding.rootView.setBackgroundColor(color)

        binding.goBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStackImmediate()
        }

    }


}