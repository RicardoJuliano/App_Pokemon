package com.game.app_pokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.game.app_pokemon.ui.AppNavigation
import com.game.app_pokemon.ui.theme.App_PokemonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App_PokemonTheme{
                Surface{
                    AppNavigation()
                }
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun AppNavigationPreview() {
    App_PokemonTheme {
        Surface {
            AppNavigation()
        }
    }
}