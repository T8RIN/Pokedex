package ru.tech.pokedex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.google.android.material.color.DynamicColors
import ru.tech.pokedex.databinding.ActivityMainBinding
import ru.tech.pokedex.fragment.pokelist.PokemonListFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DynamicColors.applyToActivitiesIfAvailable(application)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, PokemonListFragment(), "pokemon_list").commit()
        }
    }

}