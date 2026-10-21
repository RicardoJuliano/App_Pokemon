package com.game.app_pokemon.data.repository
import com.game.app_pokemon.data.remote.PokemonDetailDto
import com.game.app_pokemon.data.remote.SpriteDto
import com.game.app_pokemon.data.remote.StatDto
import com.game.app_pokemon.data.remote.StatInfoDto
import com.game.app_pokemon.data.remote.TypeDto
import com.game.app_pokemon.data.remote.TypeInfoDto
import junit.framework.TestCase.assertEquals
import org.junit.Test

// Teste unitário do conversor DTO -> Entity. Roda na JVM (pasta src/test), sem celular, internet ou banco.
class PokemonMappersTest {

    // Padrão de 3 passos: preparar, executar, conferir. Nos asserts, o valor esperado vem primeiro.
    @Test
    fun toEntity_convertsStatsAndTypes() {
        // 1. PREPARAR: o DTO falso do Bulbasaur
        val dto = PokemonDetailDto(
            id = 1,
            name = "bulbasaur",
            sprites = SpriteDto(frontDefault = null),
            // A API manda os atributos numa lista; o toEntity() precisa achar cada um pelo nome.
            // Valores diferentes em cada atributo: se o conversor trocasse dois campos, o teste perceberia.
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
        // A lista de tipos vira um texto separado por vírgula, que é como o Room vai guardar.
        assertEquals("grass,poison", entity.types)
        assertEquals(101, entity.defense)
        assertEquals(102, entity.specialDefense)
        assertEquals(100, entity.attack)

    }
}