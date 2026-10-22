package com.game.app_pokemon.domain

// As ações possíveis do jogador. sealed interface = conjunto fechado: o compilador só aceita essas três
// e, num when, obriga a tratar todas (parecido com o ListUiState: Loading/Success/Error).
sealed interface BattleAction {
    object PhysicalAttack : BattleAction
    object SpecialAttack : BattleAction
    object Heal : BattleAction
}




