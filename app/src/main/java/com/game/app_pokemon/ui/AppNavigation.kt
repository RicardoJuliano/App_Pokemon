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
    // Controlador de navegação: guarda a pilha de telas e permite ir para outra (navigate) ou voltar.
    val navController = rememberNavController()
    // Um único GameViewModel para o app todo (vive na Activity): lista, batalha e resultado compartilham a mesma partida.
    val gameViewModel: GameViewModel = viewModel(factory = AppViewModelProvider.Factory)
    // collectAsState: toda vez que o estado muda, a tela é recomposta (parecido com useState no React).
    val gameState by gameViewModel.state.collectAsState()

    // Mapa de telas: cada composable("rota") associa um endereço (texto) a uma tela. O app abre na "list".
    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            ListRoute(onPokemonClick = { pokemon ->
                // Sorteia os adversários e começa a partida com o Pokémon escolhido.
                gameViewModel.startGame(pokemon)
                navController.navigate("battle")
            })
        }
        composable("battle") {
            // Cada estado do jogo vira uma tela diferente (when sobre a sealed interface, sem else).
            when (val current = gameState) {
                is GameState.Playing -> BattleScreen(
                    // Faixa de dano e curas restantes aparecem nos botões.
                    physicalDamage = gameViewModel.previewDamage(BattleAction.PhysicalAttack),
                    specialDamage = gameViewModel.previewDamage(BattleAction.SpecialAttack),
                    healsLeft = current.player.healsLeft,
                    // Fighter é o modelo simples que a tela usa; convertemos do Combatant.
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

                // LaunchedEffect(Unit) roda uma vez ao entrar nesta tela (parecido com useEffect com array vazio): vai para o resultado.
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