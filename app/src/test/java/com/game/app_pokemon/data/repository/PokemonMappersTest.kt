package com.game.app_pokemon.data.repository
import com.game.app_pokemon.data.remote.PokemonDetailDto
import com.game.app_pokemon.data.remote.SpriteDto
import com.game.app_pokemon.data.remote.StatDto
import com.game.app_pokemon.data.remote.StatInfoDto
import com.game.app_pokemon.data.remote.TypeDto
import com.game.app_pokemon.data.remote.TypeInfoDto
import junit.framework.TestCase.assertEquals
import org.junit.Test

class PokemonMappersTest {

    @Test
    fun toEntity_convertsStatsAndTypes() {
        // 1. PREPARAR: o DTO falso do Bulbasaur
        val dto = PokemonDetailDto(
            id = 1,
            name = "bulbasaur",
            sprites = SpriteDto(frontDefault = null),
            stats = listOf(
                StatDto(baseStat = 45, stat = StatInfoDto("hp")),
                StatDto(baseStat = 100, stat = StatInfoDto("attack")),
                StatDto(baseStat = 99, stat = StatInfoDto("special-attack")),
                StatDto(baseStat = 101, stat = StatInfoDto("defense")),
                StatDto(baseStat = 102, stat = StatInfoDto("special-defense")),
                StatDto(baseStat = 103, stat = StatInfoDto("speed"))


            ),
            types = listOf(
                TypeDto(TypeInfoDto("grass")),
                TypeDto(TypeInfoDto("poison"))
            )
        )

        // 2. EXECUTAR
        val entity = dto.toEntity()

        // 3. CONFERIR
        assertEquals("bulbasaur", entity.name)
        assertEquals(45, entity.hp)
        assertEquals(99, entity.specialAttack)
        assertEquals(103, entity.speed)
        assertEquals("grass,poison", entity.types)
        assertEquals(101, entity.defense)
        assertEquals(102, entity.specialDefense)
        assertEquals(100, entity.attack)

    }
}