package ru.tech.pokedexcompose.repository

import dagger.hilt.android.scopes.ActivityScoped
import ru.tech.pokedexcompose.data.remote.PokeApi
import ru.tech.pokedexcompose.data.remote.response.Pokemon
import ru.tech.pokedexcompose.data.remote.response.PokemonList
import ru.tech.pokedexcompose.utils.Resource
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val api: PokeApi
) {
    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList> {
        val response = try {
            api.getPokemonList(limit, offset)
        } catch (e: Exception) {
            return Resource.Error(message = e.stackTrace.toString())
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        val response = try {
            api.getPokemonInfo(pokemonName)
        } catch (e: Exception) {
            return Resource.Error(message = e.stackTrace.toString())
        }
        return Resource.Success(response)
    }
}