package com.game.app_pokemon.domain

// Modelo simples que a tela de batalha desenha: nome, vida atual, vida máxima e a imagem.
// A tela não conhece o Combatant; quem converte é o AppNavigation.
data class Fighter(val name: String, val hp: Int, val maxHp: Int, val imageUrl: String)