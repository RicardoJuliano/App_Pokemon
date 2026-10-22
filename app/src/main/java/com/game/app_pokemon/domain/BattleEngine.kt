package com.game.app_pokemon.domain

import kotlin.random.Random

private const val ATTACK_POWER = 35

data class ActionResult(val actor: Combatant, val target: Combatant, val log: String)

enum class BattleOutcome { ONGOING, PLAYER_WON, OPPONENT_WON }

data class TurnResult(
    val player: Combatant,
    val opponent: Combatant,
    val log: List<String>,
    val outcome: BattleOutcome
)

class BattleEngine(
    private val damageCalculator: DamageCalculator,
    private val random: Random
) {
    fun performAction(actor: Combatant, target: Combatant, action: BattleAction): ActionResult =
        when (action) {
            BattleAction.PhysicalAttack -> {
                val damage = damageCalculator.calculate(actor.attack, target.defense, ATTACK_POWER)
                ActionResult(
                    actor = actor,
                    target = target.takeDamage(damage),
                    log = "${actor.name} causou $damage de dano!"
                )
            }

            BattleAction.SpecialAttack -> {
                val damage = damageCalculator.calculate(
                    actor.specialAttack,
                    target.specialDefense,
                    ATTACK_POWER
                )
                ActionResult(
                    actor = actor,
                    target = target.takeDamage(damage),
                    log = "${actor.name} causou $damage de dano!"
                )
            }

            BattleAction.Heal -> {
                val healedActor = actor.heal()
                val log = if (healedActor == actor) {
                    "${actor.name} não tem mais curas!"
                } else {
                    "${actor.name} se curou!"
                }
                ActionResult(actor = healedActor, target = target, log = log)
            }


        }

    private fun chooseOpponentAction(opponent: Combatant): BattleAction {
        val isLowHp = opponent.currentHp < opponent.hpMax * 30 / 100
        return if (isLowHp && opponent.healsLeft > 0) {
            BattleAction.Heal
        } else if (random.nextBoolean()) {
            BattleAction.PhysicalAttack
        } else {
            BattleAction.SpecialAttack
        }
    }
    fun playTurn(player: Combatant, opponent: Combatant, playerAction: BattleAction): TurnResult {
        val opponentAction = chooseOpponentAction(opponent)

        if (player.speed >= opponent.speed) {
            val first = performAction(player, opponent, playerAction)
            if (first.target.isFainted) {
                return TurnResult(first.actor, first.target, listOf(first.log), BattleOutcome.PLAYER_WON)
            }
            val second = performAction(first.target, first.actor, opponentAction)
            val outcome = if (second.target.isFainted) BattleOutcome.OPPONENT_WON else BattleOutcome.ONGOING
            return TurnResult(second.target, second.actor, listOf(first.log, second.log), outcome)
        } else {
            val first = performAction(opponent, player, opponentAction)
            if (first.target.isFainted){
                return TurnResult(first.target, first.actor, listOf(first.log), BattleOutcome.OPPONENT_WON)
            }
            val second = performAction(first.target, first.actor, playerAction)
            val outcome = if (second.target.isFainted) BattleOutcome.PLAYER_WON else BattleOutcome.ONGOING
            return TurnResult(second.actor, second.target, listOf(first.log, second.log), outcome)
        }
    }


}

