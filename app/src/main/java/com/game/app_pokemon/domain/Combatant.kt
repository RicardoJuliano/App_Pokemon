package com.game.app_pokemon.domain

import kotlin.math.max
import kotlin.math.min

// Um lutador na batalha. É imutável (tudo val): para mudar a vida, criamos uma cópia nova com copy().
// Como o objeto original não muda, fica mais fácil prever o estado e testar (parecido com o estado no React).
data class Combatant(
    val name: String,
    // Endereço da imagem, vindo do Pokémon, para a tela de batalha mostrar a foto.
    val imageUrl: String,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int,
    val currentHp: Int,
    val healsLeft: Int,

    ) {
    // Propriedades calculadas (get()): derivadas de outros campos, então nunca ficam desatualizadas.
    // <= 0 em vez de == 0: cobre também uma vida negativa por algum bug.
    val isFainted: Boolean
        get() = currentHp <= 0

    // Regra combinada: vida máxima da batalha = stat de hp da API x 2 (para a luta durar mais de 2 golpes).
    val hpMax: Int
        get() = hp * 2

    // Devolve um Combatant novo; o original continua intacto.
    // max(0, ...) impede a vida de ficar negativa. Atenção: amount negativo curaria (caso limite ainda não protegido).
    fun takeDamage(amount: Int): Combatant {
        val newHp = max(0, currentHp - amount)
        return copy(currentHp = newHp)
    }

    // Cura 20% do hpMax, sem passar do máximo, e gasta 1 uso.
    // Sem curas sobrando é situação normal do jogo: devolve o próprio lutador, sem mudar nada.
    // Atenção: a soma usa currentHp (vida atual), não hp (stat base da API, que nunca muda).
    fun heal(): Combatant {
        if (healsLeft <= 0) return this


        // Multiplica antes de dividir: com Int, 20 / 100 daria 0.
        val healAmount = hpMax * 20 / 100


        val newHp = min(hpMax, currentHp + healAmount)

        return copy(currentHp = newHp, healsLeft = healsLeft - 1)
    }
}
