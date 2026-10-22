# PROGRESSO — Desafio Pokémon Android

## Estado atual
- **Dia 4 em andamento — motor de batalha escrito (sem testes por decisão dele); falta ligar ao jogo (adversários, estado da partida, telas)**
- Progresso estimado do projeto: ~65%
- O app hoje: lista dos 151 offline com imagens, tela de erro com retry. No `domain`: `Combatant`, `DamageCalculator`, `BattleAction` e `BattleEngine` (ações, IA do adversário, turno completo). Batalha e resultado na tela ainda usam dados falsos (motor ainda não está ligado às telas).

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

**Dia 4**
- [x] `Combatant` imutável (`takeDamage`, `heal`, `hpMax`, `isFainted`)
- [x] `DamageCalculator` com `Random` injetável e `toDouble()` antes da divisão
- [x] `BattleAction` (`sealed interface`)
- [x] `BattleEngine`: `performAction`, IA do adversário (cura abaixo de 30%) e `playTurn` (ordem por `speed`, `TurnResult`, `BattleOutcome`)
- [ ] Testes do motor (adiados por decisão dele; voltar se sobrar tempo)

## Próxima tarefa
1. Ligar o motor ao jogo: dar os atributos ao `Pokemon` da lista e converter para `Combatant`.
2. Gerar os 3 adversários (nomes inventados, Pokémon sorteados sem repetir o do jogador).
3. ViewModel da partida (batalha atual, nº da batalha, log) e telas de batalha, vitória e Game Over com dados reais.
4. Decidir e documentar no README se o HP do jogador é restaurado entre batalhas.
5. Pendentes menores: `require(amount >= 0)` no `takeDamage`; `defense = 0`; re-testar modo avião após ~10 min; `.idea/` no `.gitignore`.

## Conceitos
**Dominados:** Kotlin básico, noções de frontend (JS/React), state hoisting, navegação, DTOs e Retrofit básico, estados com `sealed interface`, diferença entre `suspend` e `runBlocking`

**Dominados também:** papel do Repository (fonte única da verdade, esconde banco x rede), por que a Factory existe, Room guarda texto e Coil guarda imagem

**Dominados (quiz 22/27):** Repository, DI/AppContainer/Factory/`by lazy`, Semaphore, `async` x `launch`, `coroutineScope`, retry/Loading, Git `pull --rebase`

**A reforçar (erros do quiz):** recomposição (só reexecuta quem lê o estado), `suspend` = não bloqueia a thread (não é cache), `@Entity` = tabela (DTO = JSON), `OnConflictStrategy.REPLACE` = substitui a linha inteira, Coil `execute()` devolve `ErrorResult` sem lançar (por isso o `throw`); `launch` também propaga erro ao `coroutineScope` (não é `async` que traz o erro)

**Dia 4 — a reforçar:** `Int / Int` corta a parte decimal (usar `toDouble()` antes); campo errado em `copy` (`hp` x `currentHp`); copiar exemplo sem adaptar (valores e nomes); `when` com `=` devolve valor, com `{}` precisa de `return`; classes e objetos em PascalCase

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

- Poder dos ataques = 35 (constante `ATTACK_POWER`): com `hpMax` ~120 e razão ataque/defesa ~1, a batalha dura ~3 a 5 golpes e o dano supera a cura (24).
- Cura recupera 20% do `hpMax`; a IA do adversário só cura abaixo de 30% do `hpMax` e se tiver curas (não sorteia a cura).
- Empate de `speed`: o jogador age primeiro.
- Sem curas: `heal()` devolve o próprio lutador (situação normal); o log diz que não há curas.
- Testes do motor adiados por decisão dele (prioridade: fluxo completo).
- Estilo de condução direto registrado no `CLAUDE.md` e nas preferências (lógica completa, sem etapas só para compilar).

## Dificuldades e aprendizados
- `libs.versions.toml` e `build.gradle.kts` precisam ser commitados juntos.
- Versão do Android Studio precisa ser compatível com a do AGP do projeto.
- Alterações em duas máquinas: sempre `git pull` antes de começar e `git push` ao terminar.
- A pasta `.idea/` foi commitada sem querer; deveria estar no `.gitignore`.
- Antes de commitar: rodar o teste, ver `git status` e usar `git add` com caminhos específicos (nunca `-A` na raiz). Comando colado com `>` no PowerShell cria arquivos de lixo.
- Terminal: `git --no-pager` evita o paginador (`:`); sair dele com `q`.
