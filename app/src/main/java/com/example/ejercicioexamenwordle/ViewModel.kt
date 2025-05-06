package com.example.ejercicioexamenwordle

import androidx.lifecycle.ViewModel

/**
 * ViewModel que mantiene el estado del juego de Wordle durante la vida de los fragments.
 * Sirve como puente de datos entre WelcomeFragment y GameFragment.
 */
class GameViewModel : ViewModel() {

    // Nombre del jugador introducido en la pantalla de bienvenida
    var playerName: String = ""

    // Idioma seleccionado por el jugador ("es" o "en")
    var selectedLanguage: String = "es" // Español por defecto

    // Palabra secreta que el jugador debe adivinar
    var secretWord: String = ""

    // Número de intentos restantes que tiene el jugador
    var remainingAttempts: Int = 5

    // Lista opcional que almacena los intentos previos del jugador (no se usa actualmente)
    val usedAttempts = mutableListOf<String>()

    /**
     * Reinicia el estado del juego:
     * - Elige una nueva palabra secreta aleatoria de la lista proporcionada.
     * - Resetea los intentos restantes a 5.
     * - Limpia el historial de intentos usados.
     *
     * @param words Lista de palabras posibles (según el idioma)
     */
    fun resetGame(words: List<String>) {
        secretWord = words.random()
        remainingAttempts = 5
        usedAttempts.clear()
    }
}
