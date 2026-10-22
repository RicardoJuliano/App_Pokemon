package com.game.app_pokemon.domain

import kotlin.math.max
import kotlin.random.Random

// Calcula o dano de um golpe. O Random entra pelo construtor (injeção) para os testes
// poderem usar um Random com semente fixa (ex.: Random(42)) e obter sempre o mesmo resultado.
class DamageCalculator(private val random: Random) {

    fun calculate(attack: Int, defense: Int, power: Int): Int{

        // Fator aleatório entre 0,85 e 1,0, como na fórmula combinada.
        val randomFactor = random.nextDouble(0.85, 1.0)


        // toDouble() ANTES da divisão: com dois Int, 100 / 101 daria 0 (a parte decimal é cortada).
        // Fórmula: (ataque / defesa) x poder x aleatório.
        val damage = (attack.toDouble() / defense) * randomFactor * power

        // Dano mínimo de 1; toInt() corta a parte decimal.
        return max(1, damage.toInt())
    }


}

