# PROGRESSO — Desafio Pokémon Android

## Estado atual
- **Dia 3 em andamento — Room e conversores prontos; falta Repository, pré-carregamento de imagens e teste offline**
- Progresso estimado do projeto: ~39%
- O app hoje: lista os 151 Pokémon da PokeAPI com imagem, estados de carregamento e erro, no aparelho físico (PAX A930, Android 7.1.1). Ainda depende de internet a cada abertura (o Room existe, mas ainda não é usado). Batalha e resultado usam dados falsos.

## Tarefas concluídas
**Dia 1**
- [x] Projeto Compose criado e repositório Git no GitHub
- [x] Navegação entre telas (lista, batalha, resultado) com dados falsos
- [x] Telas com state hoisting (stateless + callbacks)

**Dia 2**
- [x] Dependências de rede e permissão INTERNET
- [x] DTOs, `PokeApiService` (Retrofit) e `PokeApiClient`
- [x] Testes JUnit contra a API real (lista de 151 e detalhe do id 1)
- [x] `ListViewModel` com `StateFlow` e estados `Loading`/`Success`/`Error`
- [x] `ListRoute` ligando o ViewModel à `ListScreen`
- [x] Sprites na lista com Coil
- [x] App rodando no aparelho físico

**Dia 3**
- [x] Dependências do Room e do KSP
- [x] `PokemonEntity`, `PokemonDao` e `PokemonDatabase`
- [x] Conversores `toEntity` (DTO para Entity) e `toDomain` (Entity para Pokemon)
- [x] Teste unitário do `toEntity` (no pacote correto)
- [x] Rolagem da lista em qualquer ponto da largura (`fillMaxWidth` no `LazyColumn`)

## Próxima tarefa
1. Melhorar o teste do `toEntity`: seis valores **diferentes** nos atributos, conferir os seis, tipo real (`poison`), import `org.junit.Assert.assertEquals`. Provar que o teste protege trocando `attack` por `defense` de propósito (deve falhar) e desfazendo depois.
2. `PokemonRepository` (offline-first: `count() == 0` baixa e salva com concorrência limitada, senão lê do Room).
3. `AppContainer` (DI manual), ligar o `ListViewModel` ao Repository.
4. Pré-carregar as 151 imagens e testar em modo avião no PAX.
5. Perguntas de entrevista da fase 3.

## Conceitos
**Dominados:** Kotlin básico, noções de frontend (JS/React), state hoisting, navegação, DTOs e Retrofit básico, estados com `sealed interface`, diferença entre `suspend` e `runBlocking`

**A reforçar:** papéis de `ListViewModel` / `ListRoute` / `ListScreen`, `collectAsState`, coroutines (`viewModelScope`), diferença Entity x modelo de domínio (cada camada tem o seu modelo), por que testar com valores distintos, Repository, injeção de dependência

## Decisões tomadas
- `minSdk = 25`: o aparelho de teste (PAX A930) tem Android 7.1.1. O ícone adaptativo foi movido para `mipmap-anydpi-v26` para o app instalar nele.
- Sprite montado a partir do `id` (URL previsível do repositório de sprites da PokeAPI), evitando 151 chamadas extras só para as imagens.
- ViewModel guarda o estado (`_state` privado, `state` público): fluxo de dados em uma direção.
- Coil 3 com o artefato de rede (`coil-network-okhttp`), necessário para carregar imagens por `https`.
- Atributos de batalha (HP, ataque etc.) só vêm na chamada de detalhe: serão buscados uma vez e guardados no Room (Dia 3).
- Três modelos por pokémon: DTO (API), Entity (Room, 9 campos) e `Pokemon` (tela, 2 campos), ligados por conversores.
- `LazyColumn` com `fillMaxWidth()` (escolha do Ricardo, testada no aparelho): dá para rolar com o dedo em qualquer ponto da tela; nomes em maiúsculas na lista.
- Branch `docs/comentarios`: só comentários, revisada e mesclada por ele.

## Dificuldades e aprendizados
- `libs.versions.toml` e `build.gradle.kts` precisam ser commitados juntos.
- Versão do Android Studio precisa ser compatível com a do AGP do projeto.
- Alterações em duas máquinas: sempre `git pull` antes de começar e `git push` ao terminar.
- A pasta `.idea/` foi commitada sem querer; deveria estar no `.gitignore`.
- Antes de commitar: rodar o teste, ver `git status` e usar `git add` com caminhos específicos (nunca `-A` na raiz). Comando colado com `>` no PowerShell cria arquivos de lixo.
- Terminal: `git --no-pager` evita o paginador (`:`); sair dele com `q`.
