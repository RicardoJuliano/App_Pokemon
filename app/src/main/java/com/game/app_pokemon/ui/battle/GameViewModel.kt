package com.game.app_pokemon.ui.battle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.game.app_pokemon.data.repository.PokemonRepository
import com.game.app_pokemon.domain.BattleAction
import com.game.app_pokemon.domain.BattleEngine
import com.game.app_pokemon.domain.BattleOutcome
import com.game.app_pokemon.domain.HEALS_PER_BATTLE
import com.game.app_pokemon.domain.OPPONENTS_COUNT
import com.game.app_pokemon.domain.Opponent
import com.game.app_pokemon.domain.OpponentGenerator
import com.game.app_pokemon.domain.Pokemon
import com.game.app_pokemon.domain.toCombatant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel(
    private val repository: PokemonRepository,
    private val engine: BattleEngine,
    private val opponentGenerator: OpponentGenerator
) : ViewModel() {

    fun previewDamage(action: BattleAction): IntRange? {
        val current = _state.value as? GameState.Playing ?: return null
        return engine.previewDamage(current.player, current.opponent.combatant, action)
    }

    private val _state = MutableStateFlow<GameState>(GameState.NotStarted)
    val state: StateFlow<GameState> = _state

    private var opponents: List<Opponent> = emptyList()

    fun startGame(playerPokemon: Pokemon) {
        viewModelScope.launch {
            val allPokemons = repository.getPokemons()
            opponents = opponentGenerator.generate(allPokemons, playerPokemon)
            _state.value = GameState.Playing(
                player = playerPokemon.toCombatant(),
                opponent = opponents[0],
                battleNumber = 1,
                log = listOf("Batalha 1 contra ${opponents[0].trainerName}!")
            )
        }
    }

    fun onAction(action: BattleAction) {
        val current = _state.value as? GameState.Playing ?: return
        val turn = engine.playTurn(current.player, current.opponent.combatant, action)
        val log = current.log + turn.log

        _state.value = when (turn.outcome) {
            BattleOutcome.ONGOING -> current.copy(
                player = turn.player,
                opponent = current.opponent.copy(combatant = turn.opponent),
                log = log
            )
            BattleOutcome.OPPONENT_WON -> GameState.Finished(won = false, log = log + "Game over!")
            BattleOutcome.PLAYER_WON -> {
                if (current.battleNumber == OPPONENTS_COUNT) {
                    GameState.Finished(won = true, log = log + "Você venceu as 3 batalhas!")
                } else {
                    val next = opponents[current.battleNumber]
                    GameState.Playing(
                        player = turn.player.copy(healsLeft = HEALS_PER_BATTLE),
                        opponent = next,
                        battleNumber = current.battleNumber + 1,
                        log = log + "Batalha ${current.battleNumber + 1} contra ${next.trainerName}!"
                    )
                }
            }
        }
    }

    fun reset() {
        _state.value = GameState.NotStarted
    }
}