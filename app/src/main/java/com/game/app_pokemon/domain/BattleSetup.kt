package com.game.app_pokemon.domain

import kotlin.random.Random



// Constantes (const val) usam MAIÚSCULAS_COM_UNDERLINE: valores fixos do jogo, sem números soltos no código.
// As curas voltam a este valor a cada batalha; a vida do jogador NÃO é restaurada entre as batalhas (decisão do projeto).
private const val HEALS_PER_BATTLE = 3
private const val OPPONENTS_COUNT = 3

private val trainerNames = listOf("Rafa", "Marina", "Tião", "Duda", "Bia", "Caio", "Léo", "Nina")

// Pokémon -> Combatant com vida cheia. Cria com vida 0 e usa copy para colocar hpMax:
// assim a regra "vida máxima = hp x 2" fica em um lugar só (no Combatant).
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

// Um adversário do jogo: nome inventado do treinador + o lutador dele.
data class Opponent(val trainerName: String, val combatant: Combatant)

// Sorteia os adversários. O Random entra pelo construtor para o sorteio poder ser previsível nos testes.
class OpponentGenerator(private val random: Random) {

    fun generate(pokemons: List<Pokemon>, player: Pokemon): List<Opponent> {

        // Tira o Pokémon do jogador, embaralha e pega os 3 primeiros (como shuffle + slice em JS).
        val chosenPokemons = pokemons
            .filter { it.id != player.id }
            .shuffled(random)
            .take(OPPONENTS_COUNT)
        val names = trainerNames.shuffled(random).take(OPPONENTS_COUNT)
        // zip junta as duas listas em pares (Pokémon, nome); map transforma cada par em um Opponent.
        return chosenPokemons.zip(names).map { (pokemon, name) ->
            Opponent(trainerName = name, combatant = pokemon.toCombatant())
        }
    }
}