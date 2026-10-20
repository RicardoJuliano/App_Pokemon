package com.game.app_pokemon.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

// Entity = uma tabela do Room. Cada objeto desta classe é uma linha da tabela 'pokemons'.
// Guarda o catálogo dos 151 Pokémon no aparelho, para o app funcionar offline depois da primeira vez.
@Entity(tableName = "pokemons")
data class PokemonEntity(
    // Chave primária: identifica uma única linha. Usamos o id do Pokémon na PokeAPI (único para cada um).
    @PrimaryKey val id: Int,
    val name: String,
    // Atributos de batalha (vêm da chamada de detalhe da API): hp, attack, defense, specialAttack, specialDefense, speed.
    val hp: Int,
    val attack : Int,
    val defense : Int,
    val specialAttack : Int,
    val specialDefense: Int,
    val speed: Int,
    // Tipos guardados em um único texto separado por vírgula (ex.: "grass,poison"), para não precisar de conversor.
    val types: String
)
