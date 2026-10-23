package com.game.app_pokemon.ui.battle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.game.app_pokemon.domain.Fighter
import com.game.app_pokemon.ui.theme.App_PokemonTheme

// Tela de batalha: só desenha o que recebe (stateless) e avisa os cliques por callback.
@Composable
fun BattleScreen(
    player: Fighter,
    opponent: Fighter,
    log: List<String>,
    physicalDamage: IntRange?,
    specialDamage: IntRange?,
    healsLeft: Int,
    onAttack: () -> Unit,
    onSpecialAttack: () -> Unit,
    onHeal: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        FighterCard(player)
        FighterCard(opponent)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onAttack, modifier = Modifier.weight(1f)) {
                Text("Atacar${rangeText(physicalDamage)}")
            }
            Button(onClick = onSpecialAttack, modifier = Modifier.weight(1f)) {
                Text("Especial${rangeText(specialDamage)}")
            }
            Button(onClick = onHeal, modifier = Modifier.weight(1f)) {
                Text("Curar ($healsLeft)")
            }
        }
        Text("Registro", style = MaterialTheme.typography.titleSmall)
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(log) { Text(it) }
        }
    }
}

// Texto da faixa de dano no botão (ex.: " (28–33)"); sem faixa (cura), não mostra nada.
private fun rangeText(range: IntRange?): String =
    if (range == null) "" else " (${range.first}–${range.last})"