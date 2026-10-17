package com.game.app_pokemon.ui.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.game.app_pokemon.ui.theme.App_PokemonTheme


@Composable
fun ResultScreen(title: String, message: String, onPlayAgain: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(text = title, style = MaterialTheme.typography.headlineMedium)
        Text(text = message)
        Button(onClick = onPlayAgain) { Text("Jogar de novo") }


    }
}

@Preview(showBackground = true)
@Composable
fun VictoryPreview() {
    App_PokemonTheme {
        Surface {
            ResultScreen(
                title = "Vitória",
                message = "Obrigado por participar.",
                onPlayAgain = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameOverPreview() {
    App_PokemonTheme {
        Surface {
            ResultScreen(
                title = "Game over",
                message = "Obrigado por participar.",
                onPlayAgain = {}
            )
        }
    }
}