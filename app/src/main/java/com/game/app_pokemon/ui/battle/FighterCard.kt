package com.game.app_pokemon.ui.battle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.game.app_pokemon.domain.Fighter
import com.game.app_pokemon.ui.theme.App_PokemonTheme

@Composable
fun FighterCard(fighter: Fighter) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = fighter.name, style = MaterialTheme.typography.titleMedium)
            LinearProgressIndicator(
                progress = { fighter.hp.toFloat()/ fighter.maxHp.toFloat()},
                modifier = Modifier.fillMaxWidth()
            )
            Text(text = "${fighter.hp}/${fighter.maxHp}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FighterCardPreview() {
    App_PokemonTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FighterCard(Fighter("Fighter A", 80, 100))
                FighterCard(Fighter("Fighter B", 25, 100))
            }
        }
    }
}