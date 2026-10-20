package com.game.app_pokemon.ui

import com.game.app_pokemon.ui.battle.BattleScreen
import com.game.app_pokemon.ui.list.ListScreen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.game.app_pokemon.domain.Fighter
import com.game.app_pokemon.domain.Pokemon
import com.game.app_pokemon.ui.list.ListRoute
import com.game.app_pokemon.ui.result.ResultScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            ListRoute(onPokemonClick = { navController.navigate("battle") })

        }
        composable("battle") {
            BattleScreen(
                player = Fighter(
                    "Jogador", 100, 100),
                    opponent = Fighter(
                        "Oponente",
                        80,
                        100),
                        log = listOf("Log da batalha"),
                        onAttack = { navController.navigate("result") },
                        onSpecialAttack = {},
                        onHeal = {}
                    )
        }
        composable("result") {
            ResultScreen(
                title = "Resultado",
                message = "Você venceu as 3 batalhas!",
                onPlayAgain = { navController.popBackStack("list", inclusive = false) })
        }
    }
}