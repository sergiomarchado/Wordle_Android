# 🧩 Wordle Multilingüe con Android

Este proyecto forma parte del **EjercicioEntregaExamen** de la asignatura **Programación Multimedia y Dispositivos Móviles (PMDM)** del ciclo **Desarrollo de Aplicaciones Multiplataforma (DAM)**.  
Se trata de una versión Android del popular juego **Wordle**, desarrollada con **Kotlin**, **ViewModel**, **ViewBinding**, **Navigation Component** y otras herramientas de Jetpack.

---

## 🎮 ¿En qué consiste el juego?

**Wordle** es un juego de adivinanzas donde debes descubrir una palabra secreta de 5 letras en un máximo de 5 intentos.  
Después de cada intento, las letras se colorean indicando su acierto:

- 🟩 **Verde**: letra correcta en posición correcta.  
- 🟧 **Naranja**: letra correcta en posición incorrecta.  
- ⬜ **Gris**: letra incorrecta.

---

## ✨ Características implementadas

- ✅ Animaciones visuales (fondo dinámico, validación, errores, logo).
- ✅ Interfaz adaptativa para **modo horizontal y vertical**.
- ✅ Conservación del estado tras rotación: letras, colores, intentos, habilitación.
- ✅ **Multilenguaje**: español 🇪🇸 e inglés 🇬🇧.
- ✅ **Selector de idioma persistente** con `SharedPreferences`.
- ✅ **Pistas contextuales** para cada palabra (según el idioma).
- ✅ Diálogos de victoria / derrota con opción a **reiniciar** o **salir**.
- ✅ Soporte para **ScrollView horizontal** en dispositivos pequeños.
- ✅ Arquitectura limpia: `ViewModel`, `Fragment`, `Navigation`.

---

## 🌍 Idiomas soportados

| Idioma | Palabras y Pistas |
|--------|-------------------|
| 🇪🇸 Español | Lista de palabras y pistas en español. |
| 🇬🇧 Inglés  | Lista alternativa y traducciones para cada palabra. |

El idioma afecta tanto a la **UI textual** como a las **palabras utilizadas en el juego** y sus **pistas**.

---

## 📁 Estructura del proyecto

com.example.ejercicioexamenwordle/
├── MainActivity.kt # Activity principal con NavHost
├── GameViewModel.kt # ViewModel con lógica del juego
├── LocaleHelper.kt # Clase auxiliar para idioma persistente
├── WelcomeFragment.kt # Fragmento de pantalla inicial
├── GameFragment.kt # Fragmento principal del juego
├── res/
│ ├── layout/ # Layouts en orientación vertical
│ ├── layout-land/ # Layouts horizontales adaptados
│ ├── values/ # Strings, colores, temas (ES)
│ ├── values-en/ # Strings y pistas en inglés
│ ├── anim/ # Archivos de animación XML
│ ├── drawable/ # Fondo animado, logos
│ ├── navigation/ # Navigation Graph (NavHost)
│ └── xml/ # Configuración de backups
└── AndroidManifest.xml # Declaración de actividad y configuración general

---

🎓 Información académica
Asignatura: Programación Multimedia y Dispositivos Móviles (PMDM)

Ciclo formativo: Desarrollo de Aplicaciones Multiplataforma (DAM)

Curso: 2024 – 2025

Autor: Sergio Marchado Ropero

Centro educativo: CES Juan Pablo II
