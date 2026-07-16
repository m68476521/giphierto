# Giphierto — Agent Guide

## Build & test

```bash
./gradlew ktlintCheck        # Lint (CI only runs this)
./gradlew ktlintFormat       # Auto-fix formatting
./gradlew test               # All unit tests (mostly skeleton — no real tests exist)
./gradlew :app:test          # App module unit tests
```

CI (`.github/workflows/main_ci.yml`) triggers on push/PR to `master` — only runs `ktlintCheck`. No build or test step in CI.

## Architecture

- **Multi-module Clean Architecture**: `:app` → `:presentation` → `:domain`/`:data` + `:core:{networking,models,ui}`
- **DI**: Dagger Hilt (`@HiltAndroidApp`, `@AndroidEntryPoint`, `@HiltViewModel`). Hilt modules under `app/.../di/module/`.
- **Navigation**: Compose Navigation with type-safe `@Serializable` routes. `Screen` sealed class lives in `:core:models` (`com.morozco.core.model.Screen`). Routes: `Dashboard`, `Search(word)`, `Categories`, `SubCategories(subcategory)`, `Favorites`.
- **Presentation pattern**: Each feature has `*Presentation` interface (extends `*UIActions` + `*UIStateProvider`), `*UIState` data class, `*ViewModel(@HiltViewModel)`, and `*Screen(@Composable)`. Screen composable receives the Presentation interface via `hiltViewModel<>()`.
- **State**: `StateFlow<*UIState>` exposed from ViewModel, collected in Compose via `collectAsState()`.
- **Paging 3**: `PagingSource` implementations for trending, search, categories, subcategories. Used with `LazyVerticalStaggeredGrid` in categories.
- **Image loading**: Coil 3 (`AsyncImage` + `coil-gif`). Not Glide (README is stale).
- **Networking (active)**: Ktor via `MainAPIInterface` / `MainSDK2` in `:core:networking`. Custom `NetworkResult<T>` sealed class with `.toResult()`.
- **Networking (legacy/dead)**: Retrofit + RxJava code still exists but is `@Deprecated` (commented-out `GiphyService`, deprecated `MainAPI`, `GiphyApi`, `ApiHelper`). Don't touch.
- **Database**: Room with `AppDatabase`, `ImageDao`, `ImageEntity`. Uses `allowMainThreadQueries()`.

## Quirks & gotchas

- **README is stale** — trust the code, not the README.
- **Dual package naming**: `com.m68476521.*` (old — `:app` and `:core:networking`) vs `com.morozco.*` (new — everything else). New code goes in `com.morozco.*`.
- **Secrets in `gradle.properties`**: Keystore passwords and Giphy API key are hardcoded and committed. Watch for this — do not introduce new secrets.
- **Stale/deprecated code everywhere**: Many files are `@Deprecated`, fully commented out, or labeled "old way". Check for `@Deprecated` annotations before modifying.
- **No real tests exist** — only skeleton files with empty/commented-out test methods.
- **Product flavors**: `dev`, `prod`, `local` (dimension: "environment"). Default is `local`. API key injected via `BuildConfig.API_KEY`.
- **Java 17** for `:app` and `:core:ui`; **Java 11** for all other modules.
- **Debug logging**: `println("MKE...")` scattered throughout — personal debug markers, not production logging.
- **Compose BOM**: Material3 1.3.1. Kotlin 2.2.20 with built-in Compose compiler plugin.
- **`local.properties`** requires `sdk.dir` and `API_KEY` — it's gitignored, `API_KEY=exampleOfApiKey` is the template.
