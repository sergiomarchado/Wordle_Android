Wordle Multilingüe - EjercicioEntregaExamen (PMDM)

Este proyecto es el resultado del EjercicioEntregaExamen de la asignatura Programación Multimedia y Dispositivos Móviles (PMDM) del ciclo Desarrollo de Aplicaciones Multiplataforma (DAM).Se trata de una versión Android del popular juego Wordle, desarrollada con Kotlin y Android Jetpack.

🧠 ¿En qué consiste el juego?

Wordle es un juego de adivinanzas en el que debes acertar una palabra secreta de 5 letras en un máximo de 5 intentos.Después de cada intento, cada letra recibe un color que indica:

🟩 Verde: letra y posición correctas.

🟧 Naranja: letra en la palabra pero posición incorrecta.

⬜ Gris: la letra no está en la palabra.

✨ Características implementadas

✅ Animaciones visuales en fondo, logo, validación y errores.

✅ Interfaz adaptable a orientación horizontal/vertical.

✅ Conservación del estado al rotar pantalla: letras, colores, botones deshabilitados, intentos.

✅ Soporte multilingüe: Español 🇪🇸 e Inglés 🇬🇧.

✅ Selector de idioma persistente con SharedPreferences.

✅ Pistas únicas para cada palabra según idioma (como ayuda contextual).

✅ Diálogos de victoria/derrota con opciones de reiniciar o salir.

✅ ScrollView en horizontal para pantallas pequeñas, por compatibilidad.

✅ Diseño modular y limpio usando ViewModel, ViewBinding, Fragments y Navigation.

🌍 Idiomas soportados

🇪🇸 Español

🇬🇧 Inglés

Las palabras secretas y sus pistas están adaptadas al idioma seleccionado, de modo que el idioma también afecta al conjunto de palabras del juego.

📂 Estructura del proyecto

ᴿ com.example.ejercicioexamenwordle
 ├── MainActivity.kt              // Activity principal con NavHost
 ├── GameViewModel.kt            // ViewModel con lógica del juego
 ├── LocaleHelper.kt             // Utilidad para gestión de idiomas
 ├── WelcomeFragment.kt          // Fragmento de bienvenida
 ├── GameFragment.kt             // Fragmento principal del juego
 ├── res/
 │ ├── layout/                   // Layouts verticales
 │ ├── layout-land/              // Layouts horizontales adaptados
 │ ├── values/                   // Strings, temas, colores
 │ ├── values-en/                // Traducción al inglés
 │ ├── anim/                     // Animaciones XML (bounce, shake...)
 │ ├── drawable/                 // Fondo animado y logo
 │ ├── navigation/               // Navigation Graph (NavHost)
 │ └── xml/                      // Configuración de backup y datos
 └── AndroidManifest.xml         // Declaración del tema y actividad

⚙️ Requisitos y ejecución

Clona el repositorio:

git clone https://github.com/tu_usuario/EjercicioExamenWordle.git

Abre el proyecto en Android Studio.

Ejecuta el proyecto en un emulador o dispositivo físico con Android.

🎓 Información académica

Asignatura: Programación Multimedia y Dispositivos Móviles (PMDM)

Ciclo Formativo: Desarrollo de Aplicaciones Multiplataforma (DAM)

Curso: 2024–2025

Autor: Sergio Marchado Ropero

Centro educativo: CES Juan Pablo II
