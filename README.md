# Giphierto

Giphierto is a native Android application for searching and browsing GIFs using the [Giphy API](https://developers.giphy.com/). The project is built with a modular, clean-architecture approach and follows the MVVM pattern.

## Features

- Search GIFs in real time via the Giphy API
- Smooth GIF loading and rendering with Glide
- Reactive UI updates powered by RxJava
- Modular, layered codebase (`app`, `core`, `data`, `domain`, `presentation`)

## Tech Stack & Architecture

| Layer | Details |
|---|---|
| Language | Kotlin |
| Architecture | MVVM + Clean Architecture (multi-module) |
| UI | AndroidX, Jetpack Compose |
| DI | Dagger Hilt |
| Networking | Retrofit |
| Async/Reactive | RxJava |
| Image Loading | Glide |
| Navigation | AndroidX Navigation (Safe Args) |
| Serialization | Kotlinx Serialization |
| Code Quality | ktlint |
| Testing | JUnit (unit tests) |
| CI | GitHub Actions |

## Project Structure

```
giphierto/
├── app/              # Application entry point, DI wiring, app-level config
├── core/             # Shared utilities and cross-cutting concerns
├── data/             # Repository implementations, remote/local data sources
├── domain/           # Use cases and business models (platform-agnostic)
├── presentation/     # UI layer — screens, ViewModels, Compose components
└── gradle/           # Gradle version catalogs and wrapper config
```

This separation keeps business logic independent of the Android framework and UI, making the codebase easier to test, maintain, and extend.

## Getting Started

### Prerequisites

- Android Studio (latest stable release recommended)
- JDK 17+
- A [Giphy API key](https://developers.giphy.com/)

### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/m68476521/giphierto.git
   cd giphierto
   ```

2. Add your Giphy API key to `local.properties`:
   ```properties
   GIPHY_API_KEY=your_api_key_here
   ```

3. Open the project in Android Studio and let Gradle sync.

4. Build and run on an emulator or physical device:
   ```bash
   ./gradlew installDebug
   ```

### Running Tests

```bash
./gradlew test
```

### Lint

```bash
./gradlew ktlintCheck
```

## Contributing

Contributions are welcome! This project is tagged for **Hacktoberfest**

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/my-feature`)
3. Commit your changes (`git commit -m "Add my feature"`)
4. Push to your branch (`git push origin feature/my-feature`)
5. Open a Pull Request

Please make sure your code passes `ktlintCheck` and existing unit tests before submitting a PR.

## License

No license has been specified for this project yet. Consider adding one (e.g., MIT, Apache 2.0) to clarify how others may use your code.

## Acknowledgments

- [Giphy API](https://developers.giphy.com/) for GIF data
- [Glide](https://github.com/bumptech/glide) for image loading
