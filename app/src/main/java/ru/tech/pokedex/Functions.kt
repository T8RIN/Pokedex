package ru.tech.pokedex

import ru.tech.pokedex.utils.Values.TypeBug
import ru.tech.pokedex.utils.Values.TypeDark
import ru.tech.pokedex.utils.Values.TypeDragon
import ru.tech.pokedex.utils.Values.TypeElectric
import ru.tech.pokedex.utils.Values.TypeFairy
import ru.tech.pokedex.utils.Values.TypeFighting
import ru.tech.pokedex.utils.Values.TypeFire
import ru.tech.pokedex.utils.Values.TypeFlying
import ru.tech.pokedex.utils.Values.TypeGhost
import ru.tech.pokedex.utils.Values.TypeGrass
import ru.tech.pokedex.utils.Values.TypeGround
import ru.tech.pokedex.utils.Values.TypeIce
import ru.tech.pokedex.utils.Values.TypeNormal
import ru.tech.pokedex.utils.Values.TypePoison
import ru.tech.pokedex.utils.Values.TypePsychic
import ru.tech.pokedex.utils.Values.TypeRock
import ru.tech.pokedex.utils.Values.TypeSteel
import ru.tech.pokedex.utils.Values.TypeWater

object Functions {

    fun String.containsAt(subString: String): Int {
        val d = 256
        val q = 101
        var h = 1
        var i: Int
        var j: Int
        var p = 0
        var t = 0
        val m = subString.length
        val n = this.length
        if (m <= n) {
            i = 0
            while (i < m - 1) {
                h = h * d % q
                ++i
            }
            i = 0
            while (i < m) {
                p = (d * p + subString[i].code) % q
                t = (d * t + this[i].code) % q
                ++i
            }
            i = 0
            while (i <= n - m) {
                if (p == t) {
                    j = 0
                    while (j < m) {
                        if (this[i + j] != subString[j]) break
                        ++j
                    }
                    if (j == m) return i
                }
                if (i < n - m) {
                    t = (d * (t - this[i].code * h) + this[i + m].code) % q
                    if (t < 0) t += q
                }
                ++i
            }
        } else return q
        return q
    }

    fun String.getColor(): Int {
        return when (this.lowercase()) {
            "fire" -> TypeFire
            "water" -> TypeWater
            "electric" -> TypeElectric
            "grass" -> TypeGrass
            "ice" -> TypeIce
            "fighting" -> TypeFighting
            "poison" -> TypePoison
            "ground" -> TypeGround
            "flying" -> TypeFlying
            "psychic" -> TypePsychic
            "bug" -> TypeBug
            "rock" -> TypeRock
            "ghost" -> TypeGhost
            "dragon" -> TypeDragon
            "dark" -> TypeDark
            "steel" -> TypeSteel
            "fairy" -> TypeFairy
            else -> TypeNormal
        }
    }

}