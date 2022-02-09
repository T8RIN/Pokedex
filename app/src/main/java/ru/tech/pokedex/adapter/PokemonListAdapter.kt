package ru.tech.pokedex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.request.ImageRequest
import com.google.android.material.card.MaterialCardView
import ru.tech.pokedex.data.model.PokedexListEntry
import ru.tech.pokedex.databinding.PokemonItemBinding
import ru.tech.pokedex.fragment.pokelist.PokemonListFragment
import ru.tech.pokedex.fragment.pokelist.PokemonListViewModel

class PokemonListAdapter(
    private val context: Context,
    private val viewModel: PokemonListViewModel,
    private val fragment: PokemonListFragment,
    private val pokemonList: List<PokedexListEntry>
) : RecyclerView.Adapter<PokemonListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PokemonItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = pokemonList[position]

        ImageRequest.Builder(context)
            .data(entry.imageUrl)
            .target {
                viewModel.getDominantColor(it) { dominantColor ->
                    holder.itemView.setOnClickListener {
                        fragment.openDetails(dominantColor, entry.pokemonName)
                    }
                    holder.card.setBackgroundColor(dominantColor)
                    holder.number.text = entry.number.toString()
                    holder.pokemonName.text = entry.pokemonName
                }
            }
            .build()

    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    inner class ViewHolder(binding: PokemonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val card: MaterialCardView = binding.card
        val pokemonName: TextView = binding.pokemonName
        val pokemonImage: ImageView = binding.pokemonImage
        val number: TextView = binding.number
    }

}