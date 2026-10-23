package com.game.app_pokemon.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.game.app_pokemon.domain.BattleAction
import com.game.app_pokemon.domain.Fighter
import com.game.app_pokemon.ui.battle.BattleScreen
import com.game.app_pokemon.ui.battle.GameState
import com.game.app_pokemon.ui.battle.GameViewModel
import com.game.app_pokemon.ui.list.ListRoute
import com.game.app_pokemon.ui.result.ResultScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val gameViewModel: GameViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val gameState by gameViewModel.state.collectAsState()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            ListRoute(onPokemonClick = { pokemon ->
                gameViewModel.startGame(pokemon)
                navController.navigate("battle")
            })
        }
        composable("battle") {
            when (val current = gameState) {
                is GameState.Playing -> BattleScreen(
                    player = Fighter(
                        current.player.name,
                        current.player.currentHp,
                        current.player.hpMax,
                        current.player.imageUrl
                    ),
                    opponent = Fighter(
                        current.opponent.combatant.name,
                        current.opponent.combatant.currentHp,
                        current.opponent.combatant.hpMax,
                        current.opponent.combatant.imageUrl
                    ),
                    log = current.log,
                    onAttack = { gameViewModel.onAction(BattleAction.PhysicalAttack) },
                    onSpecialAttack = { gameViewModel.onAction(BattleAction.SpecialAttack) },
                    onHeal = { gameViewModel.onAction(BattleAction.Heal) }
                )

                is GameState.Finished -> LaunchedEffect(Unit) { navController.navigate("result") }
                GameState.NotStarted -> Text("Carregando...")
            }
        }
        composable("result") {
            val finished = gameState as? GameState.Finished
            val won = finished?.won == true
            ResultScreen(
                title = if (won) "Vitória" else "Game over",
                message = if (won) "Você venceu as 3 batalhas!" else "Você perdeu a batalha.",
                onPlayAgain = {
                    gameViewModel.reset()
                    navController.popBackStack("list", inclusive = false)
                }
            )
        }
    }
}