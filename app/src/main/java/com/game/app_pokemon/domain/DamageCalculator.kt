package com.game.app_pokemon.domain

import kotlin.math.max
import kotlin.random.Random

class DamageCalculator(private val random: Random) {

    fun calculate(attack: Int, defense: Int, power: Int): Int{

        val randomFactor = random.nextDouble(0.85, 1.0)


        val damage = (attack.toDouble() / defense) * randomFactor * power

        return max(1, damage.toInt())
    }


}

