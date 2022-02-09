package ru.tech.pokedex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.clear
import coil.load
import coil.request.CachePolicy
import com.google.android.material.card.MaterialCardView
import com.google.android.material.progressindicator.CircularProgressIndicator
import ru.tech.pokedex.R
import ru.tech.pokedex.data.model.PokedexListEntry
import ru.tech.pokedex.databinding.PokemonItemBinding
import ru.tech.pokedex.fragment.pokelist.PokemonListFragment
import ru.tech.pokedex.fragment.pokelist.PokemonListViewModel

class PokemonListAdapter(
    private val context: Context,
    private val viewModel: PokemonListViewModel,
    private val fragment: PokemonListFragment,
    var pokemonList: ArrayList<PokedexListEntry> = ArrayList()
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
        holder.number.text = entry.number.toString()
        holder.pokemonName.text = entry.pokemonName
        holder.itemView.setOnClickListener {
            fragment.openDetails(entry.pokemonName)
        }
        holder.pokemonImage.load(entry.imageUrl) {
            memoryCachePolicy(CachePolicy.ENABLED)
            listener { _, _ ->
                holder.loading.visibility = GONE
                viewModel.getDominantColor(holder.pokemonImage.drawable) { dominantColor ->
                    holder.card.setCardBackgroundColor(dominantColor)
                }
            }
        }

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
        val loading: CircularProgressIndicator = binding.loading
    }

    fun addData(listItems: ArrayList<PokedexListEntry>) {
        val size = pokemonList.size
        for (i in size..listItems.lastIndex) {
            pokemonList.add(listItems[i])
        }
        val sizeNew = pokemonList.size
        notifyItemRangeChanged(size, sizeNew)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.pokemonImage.clear()
        holder.card.setCardBackgroundColor(
            ContextCompat.getColor(
                context,
                R.color.manualBackground
            )
        )
        holder.loading.visibility = VISIBLE
    }
}