package com.game.app_pokemon.domain

import kotlin.math.max
import kotlin.random.Random

private const val MIN_FACTOR = 0.85
private const val MAX_FACTOR = 1.0


class DamageCalculator(private val random: Random) {

    fun calculate(attack: Int, defense: Int, power: Int): Int {

        val randomFactor = random.nextDouble(0.85, 1.0)
        val damage = (attack.toDouble() / defense) * randomFactor * power

        return max(1, damage.toInt())
    }

    fun range(attack: Int, defense: Int, power: Int): IntRange {
        val base = (attack.toDouble() / defense) * power

        return max(1, (base * MIN_FACTOR).toInt())..max(1, (base * MAX_FACTOR).toInt())
    }


}

