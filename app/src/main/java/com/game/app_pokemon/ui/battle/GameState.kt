package com.game.app_pokemon.ui.battle


import com.game.app_pokemon.domain.Combatant
import com.game.app_pokemon.domain.Opponent

sealed interface GameState {
    object NotStarted : GameState

    data class Playing(
        val player: Combatant,
        val opponent: Opponent,
        val battleNumber: Int,
        val log: List<String>
    ) : GameState

    data class Finished(val won: Boolean, val log: List<String>) : GameState
}