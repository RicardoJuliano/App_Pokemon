package com.game.app_pokemon.domain

sealed interface BattleAction {
    object PhysicalAttack : BattleAction
    object SpecialAttack : BattleAction
    object Heal : BattleAction
}




