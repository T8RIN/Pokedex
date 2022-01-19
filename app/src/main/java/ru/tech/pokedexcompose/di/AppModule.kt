package ru.tech.pokedexcompose.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.tech.pokedexcompose.data.remote.PokeApi
import ru.tech.pokedexcompose.repository.PokemonRepository
import ru.tech.pokedexcompose.utils.Values.POKEMON_URL
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePokemonRepository(
        api: PokeApi
    ) = PokemonRepository(api)

    @Singleton
    @Provides
    fun providePokeApi(): PokeApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(POKEMON_URL)
            .build()
            .create(PokeApi::class.java)
    }
}