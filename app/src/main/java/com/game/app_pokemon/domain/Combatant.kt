package com.game.app_pokemon.domain

import kotlin.math.max
import kotlin.math.min

data class Combatant(
    val name: String,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int,
    val currentHp: Int,
    val healsLeft: Int,

    ) {
    val isFainted: Boolean
        get() = currentHp <= 0

    val hpMax: Int
        get() = hp * 2

    fun takeDamage(amount: Int): Combatant {
        val newHp = max(0, currentHp - amount)
        return copy(currentHp = newHp)
    }

    fun heal(): Combatant {
        if (healsLeft <= 0) return this


        val healAmount = hpMax * 20 / 100


        val newHp = min(hpMax, currentHp + healAmount)

        return copy(currentHp = newHp, healsLeft = healsLeft - 1)
    }
}
