# PROGRESSO — Desafio Pokémon Android

## Estado atual
- **Dia 3 fechado — app offline-first completo (dados e imagens), download em paralelo com limite e tela de erro com "Tentar de novo"; quiz de revisão dos Dias 1 a 3: 22/27**
- Progresso estimado do projeto: ~55%
- O app hoje: na 1ª abertura baixa os 151 Pokémon em paralelo (máx. 10 por vez), pré-carrega as 151 imagens em paralelo e salva no Room; nas aberturas seguintes lê tudo do banco e do cache, **sem internet**. Sem dados e sem internet: tela de erro com botão que tenta de novo (testado no PAX). Batalha e resultado ainda usam dados falsos.

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
- [x] Teste unitário do `toEntity` com valores distintos e os 6 atributos conferidos
- [x] Rolagem da lista em qualquer ponto da largura (`fillMaxWidth` no `LazyColumn`)
- [x] `PokemonRepository` offline-first (`count() == 0` baixa e salva, senão lê do banco)
- [x] `AppContainer` + `PokemonApp` (Application registrada no manifesto)
- [x] `AppViewModelProvider` (Factory) e `ListViewModel` recebendo o Repository
- [x] Lista funcionando offline no PAX (2ª abertura com Wi-Fi desligado)
- [x] `ImagePreloader` (Coil): pré-carrega as 151 imagens antes de salvar no banco; offline com imagens validado no PAX
- [x] Download dos detalhes em paralelo com `Semaphore(10)` + `coroutineScope` + `async/awaitAll`
- [x] Pré-carregamento de imagens em paralelo (`launch` + `Semaphore`)
- [x] Tela de erro com botão "Tentar novamente" (`retry()` põe `Loading` primeiro), testada no PAX
- [x] Quiz de revisão Dias 1 a 3 (27 perguntas): 22 acertos

## Próxima tarefa
1. **Dia 4:** motor de batalha em Kotlin puro (`domain`), com `Random` injetável e testes JUnit (fórmulas no CLAUDE.md).
2. Depois: fluxo das 3 batalhas ligado às telas (avisar quando chegar a 65%).
3. Re-testar o modo avião depois de uns 10 minutos (possível cabeçalho de cache curto do GitHub nas imagens).
4. Sugestão: colocar `.idea/` no `.gitignore`.

## Conceitos
**Dominados:** Kotlin básico, noções de frontend (JS/React), state hoisting, navegação, DTOs e Retrofit básico, estados com `sealed interface`, diferença entre `suspend` e `runBlocking`

**Dominados também:** papel do Repository (fonte única da verdade, esconde banco x rede), por que a Factory existe, Room guarda texto e Coil guarda imagem

**Dominados (quiz 22/27):** Repository, DI/AppContainer/Factory/`by lazy`, Semaphore, `async` x `launch`, `coroutineScope`, retry/Loading, Git `pull --rebase`

**A reforçar (erros do quiz):** recomposição (só reexecuta quem lê o estado), `suspend` = não bloqueia a thread (não é cache), `@Entity` = tabela (DTO = JSON), `OnConflictStrategy.REPLACE` = substitui a linha inteira, Coil `execute()` devolve `ErrorResult` sem lançar (por isso o `throw`); `launch` também propaga erro ao `coroutineScope` (não é `async` que traz o erro)

**Antigos a reforçar:** sintaxe do cabeçalho de classe (parâmetros do construtor x corpo), `by lazy`, `private` no container (expor o mínimo), `collectAsState`, coroutines (`viewModelScope`), por que testar com valores distintos, injeção de dependência

## Decisões tomadas
- `minSdk = 25`: o aparelho de teste (PAX A930) tem Android 7.1.1. O ícone adaptativo foi movido para `mipmap-anydpi-v26` para o app instalar nele.
- Sprite montado a partir do `id` (URL previsível do repositório de sprites da PokeAPI), evitando 151 chamadas extras só para as imagens.
- ViewModel guarda o estado (`_state` privado, `state` público): fluxo de dados em uma direção.
- Coil 3 com o artefato de rede (`coil-network-okhttp`), necessário para carregar imagens por `https`.
- Atributos de batalha (HP, ataque etc.) só vêm na chamada de detalhe: serão buscados uma vez e guardados no Room (Dia 3).
- Três modelos por pokémon: DTO (API), Entity (Room, 9 campos) e `Pokemon` (tela, 2 campos), ligados por conversores.
- `LazyColumn` com `fillMaxWidth()` (escolha do Ricardo, testada no aparelho): dá para rolar com o dedo em qualquer ponto da tela; nomes em maiúsculas na lista.
- Branch `docs/comentarios`: só comentários, revisada e mesclada por ele.
- Injeção de dependência manual: `AppContainer` na `Application` cria banco e Repository uma vez; `AppViewModelProvider` (Factory) entrega o Repository ao ViewModel.
- `count() == 0` mantido (em vez de `< 151`): download tudo-ou-nada, ciente do risco de banco incompleto.
- O `.idea_backup/` fica fora do Git (`.git/info/exclude`).
- Pré-carregar as imagens antes do `insertAll`: se a internet cair no meio, nada é salvo e o app tenta tudo de novo (tudo-ou-nada).

## Dificuldades e aprendizados
- `libs.versions.toml` e `build.gradle.kts` precisam ser commitados juntos.
- Versão do Android Studio precisa ser compatível com a do AGP do projeto.
- Alterações em duas máquinas: sempre `git pull` antes de começar e `git push` ao terminar.
- A pasta `.idea/` foi commitada sem querer; deveria estar no `.gitignore`.
- Antes de commitar: rodar o teste, ver `git status` e usar `git add` com caminhos específicos (nunca `-A` na raiz). Comando colado com `>` no PowerShell cria arquivos de lixo.
- Terminal: `git --no-pager` evita o paginador (`:`); sair dele com `q`.
