# Guía del repositorio

## Estructura y organización
MarketLab es una aplicación Android de un solo módulo, desarrollada con Kotlin y Jetpack Compose. El código está en `app/src/main/java/com/marketlab/`:

- `presentation/`: pantallas de catálogo, detalle y favoritos, ViewModels, estados de interfaz, navegación, componentes y tema.
- `domain/`: modelos de productos y contratos de repositorios.
- `data/`: servicios Retrofit, DTOs, entidades y DAOs de Room, mapeadores e implementaciones de repositorios.
- `di/`: proveedores de dependencias con Hilt.

Los recursos están en `app/src/main/res/`; las pruebas JVM, en `app/src/test/`; y las instrumentadas, en `app/src/androidTest/`. Las versiones de dependencias se centralizan en `gradle/libs.versions.toml`.

## Compilación, pruebas y desarrollo
Ejecuta desde la raíz con el wrapper de Gradle:

- `./gradlew :app:assembleDebug`: genera el APK de depuración.
- `./gradlew :app:installDebug`: instala en un dispositivo o emulador conectado. Abre MarketLab desde su icono o Android Studio.
- `./gradlew :app:testDebugUnitTest`: ejecuta las pruebas JVM.
- `./gradlew :app:connectedDebugAndroidTest`: ejecuta las pruebas instrumentadas en un dispositivo conectado.
- `./gradlew :app:lintDebug`: ejecuta Android Lint.

Configura el SDK desde Android Studio o `local.properties`. El proyecto usa SDK objetivo 37, admite API 24+ y declara Java 25 para el daemon de Gradle.

## Estilo y nombres
Usa cuatro espacios de sangría y el formateador Kotlin de Android Studio. Emplea `PascalCase` para tipos, archivos y funciones componibles; `camelCase` para otras funciones y propiedades; y `snake_case` para recursos. Conserva sufijos como `Screen`, `ViewModel`, `UiState`, `Repository` y `Entity`. No hay configuración específica de ktlint ni detekt.

Mantén los tipos de persistencia y red en `data/`. Expón modelos de dominio mediante contratos de repositorios e inyecta dependencias con Hilt.

## Pruebas
Se utiliza JUnit 4 para pruebas locales; AndroidJUnit4, Espresso y Compose UI para instrumentadas. Nombra las clases `<Subject>Test` y replica los paquetes del código fuente. Las pruebas actuales son ejemplos iniciales; no existe un umbral de cobertura configurado. Añade pruebas de comportamiento para cambios de lógica, especialmente mapeo, estados de carga/error y persistencia de favoritos.

## Commits y solicitudes de cambio
El historial utiliza principalmente descripciones en español con prefijos `feat:` y `refactor:`. Sigue ese patrón con commits concisos y enfocados. Cada PR debe explicar los cambios, enlazar incidencias pertinentes, indicar las validaciones realizadas e incluir capturas si modifica la interfaz. Evita cambios ajenos al objetivo y no incluyas secretos, rutas locales del SDK ni archivos generados.
