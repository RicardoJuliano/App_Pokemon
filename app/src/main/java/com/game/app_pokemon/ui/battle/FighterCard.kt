package com.game.app_pokemon.ui.battle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.game.app_pokemon.domain.Fighter

@Composable
fun FighterCard(fighter: Fighter) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = fighter.imageUrl,
                contentDescription = fighter.name,
                modifier = Modifier.size(96.dp)
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = fighter.name.uppercase(), style = MaterialTheme.typography.titleMedium)
                LinearProgressIndicator(
                    progress = { fighter.hp.toFloat() / fighter.maxHp.toFloat() },
                    modifier = Modifier.fillMaxWidth()
                )
                Text(text = "${fighter.hp}/${fighter.maxHp}")
            }
        }
    }
}

