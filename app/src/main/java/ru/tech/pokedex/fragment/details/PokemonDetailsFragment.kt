package ru.tech.pokedex.fragment.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import coil.size.Scale
import coil.transform.CircleCropTransformation
import dagger.hilt.android.AndroidEntryPoint
import ru.tech.pokedex.Functions.getColor
import ru.tech.pokedex.databinding.PokemonDetailsFragmentBinding
import ru.tech.pokedex.extensions.Extensions.capitalized

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
        val pokemonNameStr = requireArguments().getString("name", "")!!

        viewModel.pokemonInfo.observe(viewLifecycleOwner) { pokemon ->

            binding.apply {
                try {
                    val type0 = pokemon.types[0].type.name.capitalized()
                    elementText.text = type0
                    element.setImageResource(type0.getColor())

                    val type1 = pokemon.types[1].type.name.capitalized()
                    typeText.text = type1
                    type.setImageResource(type1.getColor())

                } catch (e: Exception) {
                    typeText.visibility = GONE
                    type.visibility = GONE
                }

                val max = pokemon.stats.maxOf { it.base_stat }.toFloat()

                hpProgress.max = max
                attackProgress.max = max
                defProgress.max = max
                spAtkProgress.max = max
                spDefProgress.max = max
                speedProgress.max = max

                hpProgress.setOnProgressChangeListener {
                    hpProgress.labelText = "${it.toInt()}"
                }
                attackProgress.setOnProgressChangeListener {
                    attackProgress.labelText = "${it.toInt()}"
                }
                defProgress.setOnProgressChangeListener {
                    defProgress.labelText = "${it.toInt()}"
                }
                spAtkProgress.setOnProgressChangeListener {
                    spAtkProgress.labelText = "${it.toInt()}"
                }
                spDefProgress.setOnProgressChangeListener {
                    spDefProgress.labelText = "${it.toInt()}"
                }
                speedProgress.setOnProgressChangeListener {
                    speedProgress.labelText = "${it.toInt()}"
                }

                hpProgress.progress = pokemon.stats[0].base_stat.toFloat()
                attackProgress.progress = pokemon.stats[1].base_stat.toFloat()
                defProgress.progress = pokemon.stats[2].base_stat.toFloat()
                spAtkProgress.progress = pokemon.stats[3].base_stat.toFloat()
                spDefProgress.progress = pokemon.stats[4].base_stat.toFloat()
                speedProgress.progress = pokemon.stats[5].base_stat.toFloat()

                pokemonName.text = "#${pokemon.id} $pokemonNameStr"
                weight.text = "${pokemon.weight / 10f} kg"
                height.text = "${pokemon.height / 10f} m"
                pokemonImage.load(pokemon.sprites.front_default) {
                    crossfade(300)
                    transformations(CircleCropTransformation())
                    scale(Scale.FILL)
                }
            }
        }

        viewModel.getPokemonInfo(pokemonNameStr)

        binding.rootView.setBackgroundColor(color)

        binding.goBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStackImmediate()
        }

    }


}