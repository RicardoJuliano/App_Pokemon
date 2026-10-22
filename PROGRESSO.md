# PROGRESSO — Desafio Pokémon Android

## Estado atual
- **Dia 3 em andamento — Room + Repository ligados à lista; falta pré-carregar as imagens e limitar a concorrência do download**
- Progresso estimado do projeto: ~46%
- O app hoje: na 1ª abertura baixa os 151 Pokémon (152 chamadas, em fila, demora), salva no Room e mostra a lista; nas aberturas seguintes lê do banco e a lista aparece **sem internet** (testado no PAX com Wi-Fi desligado). As **imagens** ainda dependem do cache do Coil (só aparecem as já vistas). Batalha e resultado ainda usam dados falsos.

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

## Próxima tarefa
1. Pré-carregar as 151 imagens (Coil `ImageLoader` + `ImageRequest`) logo após o primeiro download, para o modo avião funcionar de verdade. Testar no PAX.
2. Limitar a concorrência do download (coroutines em paralelo com limite, ex.: `Semaphore`), hoje são 152 chamadas em fila.
3. Tela de erro com botão "Tentar de novo" (sem dados e sem internet).
4. Perguntas de entrevista da fase 3.

## Conceitos
**Dominados:** Kotlin básico, noções de frontend (JS/React), state hoisting, navegação, DTOs e Retrofit básico, estados com `sealed interface`, diferença entre `suspend` e `runBlocking`

**Dominados também:** papel do Repository (fonte única da verdade, esconde banco x rede), por que a Factory existe, Room guarda texto e Coil guarda imagem

**A reforçar:** sintaxe do cabeçalho de classe (parâmetros do construtor x corpo), `by lazy`, `private` no container (expor o mínimo), `collectAsState`, coroutines (`viewModelScope`), por que testar com valores distintos, injeção de dependência

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

## Dificuldades e aprendizados
- `libs.versions.toml` e `build.gradle.kts` precisam ser commitados juntos.
- Versão do Android Studio precisa ser compatível com a do AGP do projeto.
- Alterações em duas máquinas: sempre `git pull` antes de começar e `git push` ao terminar.
- A pasta `.idea/` foi commitada sem querer; deveria estar no `.gitignore`.
- Antes de commitar: rodar o teste, ver `git status` e usar `git add` com caminhos específicos (nunca `-A` na raiz). Comando colado com `>` no PowerShell cria arquivos de lixo.
- Terminal: `git --no-pager` evita o paginador (`:`); sair dele com `q`.
