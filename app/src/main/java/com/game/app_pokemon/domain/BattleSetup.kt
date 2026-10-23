package com.game.app_pokemon.domain

import kotlin.random.Random



private const val HEALS_PER_BATTLE = 3
private const val OPPONENTS_COUNT = 3

private val trainerNames = listOf("Rafa", "Marina", "Tião", "Duda", "Bia", "Caio", "Léo", "Nina")

fun Pokemon.toCombatant(healsLeft: Int = HEALS_PER_BATTLE): Combatant {

    val rested = Combatant(
        name = name,
        hp = hp,
        attack = attack,
        defense = defense,
        specialAttack = specialAttack,
        specialDefense = specialDefense,
        speed = speed,
        currentHp = 0,
        healsLeft = healsLeft
    )
    return rested.copy(currentHp = rested.hpMax)
}

data class Opponent(val trainerName: String, val combatant: Combatant)

class OpponentGenerator(private val random: Random) {

    fun generate(pokemons: List<Pokemon>, player: Pokemon): List<Opponent> {

        val chosenPokemons = pokemons
            .filter { it.id != player.id }
            .shuffled(random)
            .take(OPPONENTS_COUNT)
        val names = trainerNames.shuffled(random).take(OPPONENTS_COUNT)
        return chosenPokemons.zip(names).map { (pokemon, name) ->
            Opponent(trainerName = name, combatant = pokemon.toCombatant())
        }
    }
}