package ru.tech.pokedex.extensions

object Extensions {

    fun String.capitalize(): String {
        return lowercase().replaceFirstChar { it.titlecase() }
    }

}