# Assignment 1 — Assisted code generation - MIP (Exercice 8)

Course: Desenvolvimento de Aplicações Móveis <br>
Student(s): Guilherme Graça / A5127 <br>
Date: 08/03/2026 <br>
Repository URL: https://github.com/GuilhermeGraca/CineMatch_Ex8_DAM_TP1/ <br>
---

## 1. Introduction
O objetivo deste trabalho é explorar a engenharia de software autónoma e o desenvolvimento assistido por IA através da criação de uma aplicação móvel robusta no Android Studio com Kotlin. A aplicação desenvolvida, **CineMatch**, visa resolver o problema da fadiga de decisão na escolha de filmes, proporcionando uma experiência de recomendação de filmes simplificada, visualmente apelativa e personalizada. Os seus objetivos principais incluem a consulta a uma API externa (OMDb) para a obtenção de dados de filmes, a apresentação de uma interface de utilizador especializada "Cinematic Dark" e a possibilidade de os utilizadores guardarem as suas recomendações favoritas numa Watchlist local persistente.

## 2. System Overview
O CineMatch é uma aplicação Android nativa de recomendação e acompanhamento de filmes.
**Principais Funcionalidades:**
- **Pesquisa e Descoberta**: Um ecrã inicial com uma Barra de Pesquisa (Search Bar) e Chips de escolha de género, o que permite aos utilizadores procurar filmes específicos ou navegar por categorias.
- **Recomendações**: Um ecrã de resultados que exibe os pósteres dos filmes num layout em grelha personalizado e um sistema de atualização baseado em paginação.
- **Detalhes do Filme**: Um ecrã de detalhes, com destaque para o póster, sinopse do filme e critérios de lançamento.
- **Watchlist e Classificação (Rating)**: Uma watchlist local persistente onde os utilizadores podem guardar os seus filmes favoritos e atribuir uma classificação pessoal de 0 a 5 estrelas.

## 3. Architecture and Design
A aplicação segue estritamente o padrão de arquitetura **MVVM (Model-View-ViewModel)**.
- **Estrutura de Pastas**: Separada em camadas `ui` (dividida por ecrãs: main, results, details, watchlist), `data` (api, db, model, repository) e `util`.
- **Padrões de Design**:
  - **Padrão Repository**: Centraliza a obtenção de dados e atua como uma fonte única de verdade (Single Source of Truth), decidindo se os dados devem ser obtidos da rede (Retrofit/OMDb) ou do armazenamento local (Room).
  - **StateFlow/Sealed Classes**: Usado para representar suavemente os estados da UI (`Loading`, `Success`, `Error`), garantindo que as Views apenas reajam às alterações de estado sem reter lógica de negócio.
- **Design de Interface (UI)**: A app traduz um design web moderno ("Stitch") para XML nativo, implementando um tema "Cinematic Dark". Recorre aos componentes do Material Design 3, evita layouts básicos e aproveita gradientes escuros profundos, botões arredondados, cartões de filmes sem bordas e micro-interações de `layoutAnimation`.

## 4. Implementation
- **Camada de Rede (Network)**: Suportada por **Retrofit** e **OkHttp** para comunicar de forma segura e eficiente com a API OMDb.
- **Camada de Persistência**: Implementada recorrendo à **Room Database**, armazenando objetos `MovieEntity` que guardam as classificações pessoais do utilizador localmente no dispositivo.
- **Operações Assíncronas**: Totalmente geridas por **Kotlin Coroutines**, assegurando que os pedidos à base de dados e à rede são executados fora da thread principal (Main Thread).
- **Carregamento de Imagens**: Utiliza a biblioteca **Coil** para obter imagens (pósteres) online de forma assíncrona, leve e rápida.
- **Navegação**: Utiliza o **Navigation Component** do AndroidX (Single-Activity Architecture) com SafeArgs para passar dados (como os IDs do IMDb) entre os Fragmentos de forma segura e sem falhas.

## 5. Testing and Validation
- **Estratégia de Teste**: Foco na verificação manual e na validação rigorosa da gestão de estado.
- **Cenários Validados**:
  - Respostas bem-sucedidas da API devidamente mapeadas nas RecyclerViews.
  - Estados Vazios/Erros (por exemplo, pesquisar um filme inexistente desencadeia um estado de erro "No results found" em vez de a aplicação falhar de forma abrupta).
  - Teste de persistência (guardar um filme no ecrã de Detalhes, voltar ao ecrã inicial e abrir a Watchlist para confirmar a atualização quase instantânea via Kotlin Flow).
- **Limitações Conhecidas**: A API OMDb restringe as pesquisas estritamente pelo título (parâmetro `s`), o que torna a pesquisa genérica por géneros menos eficaz. Resolvemos esta limitação implementando um campo de texto livre de "fallback" (pesquisa livre) e gerindo múltiplos updates com uma pseudo-paginação aleatória ("Refresh").

## 6. Usage Instructions
1. Clonar o repositório e abri-lo no Android Studio.
2. Na raiz do projeto, localizar ou criar o ficheiro `local.properties`.
3. Adicionar uma chave de API válida do OMDb da seguinte forma: `OMDB_API_KEY="a_tua_chave_api_aqui"`.
4. Sincronizar os ficheiros de build do Gradle (assegurar as configurações do plugin KSP).
5. Fazer o Build ('assembleDebug') e executar a aplicação num Emulador Android ou Dispositivo Físico compatível.

---
# Autonomous Software Engineering Sections - only for [AC OK, AI OK] sections
## 7. Prompting Strategy
O projeto foi totalmente construído com a ajuda do agente autónomo Google Antigravity. Inicialmente, recorremos ao **Planning Mode** (Modo de Planeamento) para impor o design rigoroso da arquitetura e apresentar um plano de implementação formal antes da geração de qualquer linha de código.
- **Evolução dos Prompts**: Começámos com pedidos amplos a nível de arquitetura ("Cria um MVP com um Ecrã Principal de Questionário"). Assim que a base foi solidificada, os prompts tornaram-se altamente focados e cirúrgicos na melhoria da experiência de utilizador ("Remove cirurgicamente a funcionalidade 'Surprise Me' devido a limites da API sem afetar o código da Watchlist").

## 8. Autonomous Agent Workflow
O Antigravity operou num fluxo de trabalho end-to-end contínuo e interativo:
1. **Planeamento**: Criou um artefacto chamado `implementation_plan.md` que detalhava as restrições da arquitetura MVVM, bibliotecas base (Coil, Retrofit, Room) e as bases de design.
2. **Programação (Coding)**: O agente gerou de forma autónoma as interfaces XML, ViewModels em Kotlin, Fragmentos e DAOs do Room.
3. **Depuração (Debugging)**: Quando iam ocorrendo erros típicos do ciclo de compilação (por exemplo, `attr/colorBackground not found`), o agente teve a capacidade de ler a consola da compilação diretamente no terminal, isolou assim a falta da declaração do prefixo `android:` e implementando a correção nos ficheiros com um 'diff'.
4. **Documentação**: Este agente geriu continuamente uma lista ativa de tarefas (`task.md`) e um ficheiro descritivo iterativo descrevendo o passo a passo do desenvolvimento (`walkthrough.md`) nas 5 fases totais do projeto.

## 9. Verification of AI-Generated Artifacts
A validação das saídas da Inteligência Artificial foi executada continuamente via testes empíricos no Emulador do Android e revisões regulares da lógica gerada nos ViewModels/XML. Adicionalmente, o agente utilizava proativamente comandos de compilação no terminal background do sistema local (`./gradlew assembleDebug`), permitindo certificar tecnicamente a estrutura gerada em Kotlin sem interação manual, garantindo resoluções de eventuais falhados do ciclo Gradle durante novas *features*.

## 10. Human vs AI Contribution
- **Contribuição da IA**: O agente revelou-se central na agilização estrutural massiva "boilerplate" (boilerplate de classes MVVM de Android e dependências), arquitetura rápida, gestão nativa de base de dados SQLite (via Room) com `coroutines`, bem como ao mapear visualmente as diretrizes do material gráfico da Web (UI mockups) sobre estruturas nativas Android nativas com otimizações em código final XML Layout.
- **Contribuição Humana**: Desempenhei o papel de gestor de produto associado a um Quality Analyst. Exerci controlo central a nível pragmático nos testes locais em device (UI verification), na curadoria das linhas de desenvolvimento baseadas nas capacidades gráficas do App (como decidir abortar as calls restritas OMDb e pedir que fossem anulados na app a favor da pesquisa manual), e a restrição ao API_KEY na branch. 

## 11. Ethical and Responsible Use
Durante as etapas de codificação, o agente e a External API entraram em conflito: a IA conseguia ligar logicamente a API para entregar sugestões, no entanto os resultados produzidos pela falta de atributos específicos base da Movie Database (onde procurar estritamente por palavras e não categorias) não revelava lógica e consistência qualitativa ao utilizador ("UX Fraca"). Interrompi intencionalmente a AI e ordenei arquiteturalmente o corte dessas conexões, o que dá um valor pragmático às soluções base da interface de pesquisa em detrimento de uma mecânica de "Recomendações" base disfuncional e baseada na AI.
Adicionalmente, nenhuma credencial de rede privada de testes/consumo externo subiu a qualquer registo permanente da thread/commit da codebase gerada pelos Agentes, limitando acessos partilhados indevidos na web.

---
# Development Process
## 12. Version Control and Commit History
A fragmentação e rastreamento local registou 5 fases modulares principais: Planeamento & Setup, Módulos Data/Persistence Room Base, Refreshing API UX, UI Styling e "Polishing Final" (gradientes/animatics). O ciclo comprovou uma evolução tangível entre o protótipo Material MVP (básico default) para o estilo complexo (Cinematic Design Custom). Tudo num raciocínio de passos sequenciais geridos com pequenas alterações aos commit tree. 

## 13. Difficulties and Lessons Learned
- **Desafio Metodológico (Design):** Tentei que a IA traduzisse um código de design complexo da web (HTML/Tailwind Next.js chamado 'Stitch') diretamente para a app. Contudo, a IA não conseguiu transpor o código nativamente de forma perfeita para o ecossistema Android XML views. Como solução, optei por apenas fornecer à IA as ideias gerais e restrições visuais do design base (ex: cantos arredondados, 'Cinematic Dark theme', ausência de bordas, sobreposições de gradiente), permitindo-lhe implementar livremente usando ferramentas básicas e eficientes (`ShapeAppearanceModel`, `<shape>`), em vez de forçar um mapeamento exato linha a linha.
- **Desafio Metodológico (Integração de APIs/Backend):** Inicialmente, a intenção era utilizar a API do **TMDB** (The Movie Database) para construir a aplicação devido à sua robustez. No entanto, enfrentei dificuldades intransponíveis em gerar um código/chave da API funcional para o meu utilizador no TMDB. Como alternativa de recurso, adotámos a API **OMDb**.
- **Lição Aprendida (Limitação de API):** A escolha forçada do OMDb revelou-se problemática, uma vez que a API gratuita está severamente limitada a pesquisas que exigem correspondência direta de palavras no título do filme (pesquisa `s=`), o que falha na entrega de recomendações lógicas baseadas puramente num género selecionado e sem opções avançadas de pesquisa. Este obstáculo exigiu a restruturação parcial da aplicação, incluindo a remoção de funcionalidades planeadas geradas pela IA e a implementação pragmática de uma barra de pesquisa manual (fallback). 
- **Resolução de Complexidade KAPT/KSP**: O projeto levantou um conflito inicial onde as anotações da base de dados (Room Android) originaram erros nas dependências modernas, forçando a ferramenta e o operador a migrarem toda a compilação Gradle de "KAPT" para as bibliotecas atuais "KSP". 

## 14. Future Improvements
- **Transição Exclusiva para Jetpack Compose**: Re-arquiteturar esta camada (Views tradicionais e DataBindings pesados via Adapter/RecyclerView) para UI Composed Declarativa moderna Jetpack Compose (garantindo coerência a uma lógica UI inteiramente passiva/reativa a `StateFlow`).
- **TMDB vs OMDb Backend Integration**: Alterar o DataService/Repositório e integrar uma plataforma Movie Backend densamente interligada ao ecossistema (The Movie Data Base) habilitando os "Chips genres" a passarem ID únicos no lugar da keyword fraca de "pesquisa em titulo" em que agora opera a app.  
- **Suporte Caching Offline First**: Armazenar instâncias da Homepage/Search cache por longos períodos no próprio Base de Dados Room/DataStore nativamente a fim de assegurar interactividade rápida da mesma sem qualquer internet connection ativa.

---
## 15. Divulgação do Uso de IA (Obrigatório)
- **Ferramenta IA**: Google Antigravity.
  - **Contexto de Aplicação**: Recorreu-se como um auxílio à agilidade e setup estrutural da infraestrutura, geração padronizada de Kotlin Base ViewModels via Flow States, Room DAO SQL queries e como parceiro de exploração (Debug compiler errors de Android).
- **Ferramenta de Design com AI Stitch** Foi usada para obter inspiração de design para a app
- **GEMINI 3** para apoio da redação do ficheiro READ.md
- Reconheço e assumo integral responsabilidade sobre todas e quaisquer modificações providenciadas nos ambientes contidos, com as mesmas sendo geridas, autorizadas individualmente no planeamento base/debug final a fim de garantir integridade do conteúdo final do Código aqui exportado à avaliação desta Submissão Académica Móvel.
