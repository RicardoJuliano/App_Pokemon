package com.game.app_pokemon.ui.battle


import com.game.app_pokemon.domain.Combatant
import com.game.app_pokemon.domain.Opponent

// Situações possíveis do jogo (conjunto fechado, como o ListUiState): ainda não começou, jogando ou terminou.
sealed interface GameState {
    object NotStarted : GameState

    // Partida em andamento: o jogador, o adversário atual, qual das 3 batalhas é e o log acumulado.
    data class Playing(
        val player: Combatant,
        val opponent: Opponent,
        val battleNumber: Int,
        val log: List<String>
    ) : GameState

    // Fim de jogo: won = true é vitória (venceu as 3), false é Game over.
    data class Finished(val won: Boolean, val log: List<String>) : GameState
}