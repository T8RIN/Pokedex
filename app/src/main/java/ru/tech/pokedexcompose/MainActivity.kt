package ru.tech.pokedexcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import ru.tech.pokedexcompose.composition.PokemonListScreen
import ru.tech.pokedexcompose.ui.theme.PokedexComposeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexComposeTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = LIST_SCREEN) {
                    composable(LIST_SCREEN) {
                        PokemonListScreen(navController)
                    }

                    composable(
                        "$DETAILS_SCREEN/{$DOMINANT_COLOR}/{$POKEMON_NAME}",
                        arguments = listOf(
                            navArgument(DOMINANT_COLOR) { type = NavType.IntType },
                            navArgument(POKEMON_NAME) { type = NavType.StringType }
                        )
                    ) {
                        val dominantColor = remember {
                            val color = it.arguments?.getInt(DOMINANT_COLOR)
                            color?.let { Color(it) } ?: Color.White
                        }
                        val pokemonName = remember {
                            it.arguments?.getString(POKEMON_NAME)
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val DOMINANT_COLOR = "dominantColor"
        const val POKEMON_NAME = "pokemonName"
        const val LIST_SCREEN = "pokemon_list_screen"
        const val DETAILS_SCREEN = "pokemon_details_screen"
    }
}