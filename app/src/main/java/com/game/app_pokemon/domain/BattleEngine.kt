package com.game.app_pokemon.domain

import kotlin.random.Random

// Poder fixo dos dois ataques (físico e especial). Escolhido pela conta: hpMax ~120, razão ataque/defesa ~1,
// então o dano fica perto de 35 x 0,85..1,0 (~30 a 35): batalha de uns 4 golpes e dano maior que a cura (24).
private const val ATTACK_POWER = 35

// Resultado de UMA ação: quem agiu, quem recebeu e a frase do log. Ambos já atualizados (cópias novas).
data class ActionResult(val actor: Combatant, val target: Combatant, val log: String)

// Estado da batalha depois do turno: continua, jogador venceu ou adversário venceu.
enum class BattleOutcome { ONGOING, PLAYER_WON, OPPONENT_WON }

// Resultado de um turno inteiro: os dois lutadores novos, o log do turno e o estado da batalha.
// Sempre o jogador primeiro e o adversário depois.
data class TurnResult(
    val player: Combatant,
    val opponent: Combatant,
    val log: List<String>,
    val outcome: BattleOutcome
)

// Motor da batalha: Kotlin puro, sem Android. Recebe o DamageCalculator e o Random pelo construtor
// (injeção) para poder ser testado com valores previsíveis.
class BattleEngine(
    private val damageCalculator: DamageCalculator,
    private val random: Random
) {


    // Faixa de dano de uma ação, para mostrar nos botões. A cura não causa dano: devolve null (IntRange? = pode ser nulo).
    fun previewDamage(actor: Combatant, target: Combatant, action: BattleAction): IntRange? =
        when (action) {
            BattleAction.PhysicalAttack -> damageCalculator.range(
                actor.attack,
                target.defense,
                ATTACK_POWER
            )

            BattleAction.SpecialAttack -> damageCalculator.range(
                actor.specialAttack,
                target.specialDefense,
                ATTACK_POWER
            )

            BattleAction.Heal -> null
        }

    // Executa uma ação e devolve o resultado. O when sobre a sealed interface não tem else:
    // o compilador sabe que as três ações cobrem tudo. Nada é alterado no lugar: sempre cópias novas.
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
                // Se a cura não aconteceu (sem curas), o lutador é igual ao de antes: a data class compara por valor.
                val healedActor = actor.heal()
                val log = if (healedActor == actor) {
                    "${actor.name} não tem mais curas!"
                } else {
                    "${actor.name} se curou!"
                }
                ActionResult(actor = healedActor, target = target, log = log)
            }


        }

    // IA do adversário: cura se a vida está abaixo de 30% do máximo e ainda tem curas;
    // senão sorteia entre ataque físico e especial.
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

    // Um turno completo. Quem tem maior speed age primeiro (empate: o jogador).
    // Se o primeiro golpe derrota o alvo, o outro lutador não age e a batalha acaba.
    fun playTurn(player: Combatant, opponent: Combatant, playerAction: BattleAction): TurnResult {
        val opponentAction = chooseOpponentAction(opponent)

        if (player.speed >= opponent.speed) {
            val first = performAction(player, opponent, playerAction)
            if (first.target.isFainted) {
                return TurnResult(
                    first.actor,
                    first.target,
                    listOf(first.log),
                    BattleOutcome.PLAYER_WON
                )
            }
            val second = performAction(first.target, first.actor, opponentAction)
            val outcome =
                if (second.target.isFainted) BattleOutcome.OPPONENT_WON else BattleOutcome.ONGOING
            return TurnResult(second.target, second.actor, listOf(first.log, second.log), outcome)
        } else {
            val first = performAction(opponent, player, opponentAction)
            if (first.target.isFainted) {
                return TurnResult(
                    first.target,
                    first.actor,
                    listOf(first.log),
                    BattleOutcome.OPPONENT_WON
                )
            }
            val second = performAction(first.target, first.actor, playerAction)
            val outcome =
                if (second.target.isFainted) BattleOutcome.PLAYER_WON else BattleOutcome.ONGOING
            return TurnResult(second.actor, second.target, listOf(first.log, second.log), outcome)
        }
    }


}

