package com.example.ejercicioexamenwordle

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import java.util.*
import androidx.core.content.edit

/**
 * Objeto auxiliar para gestionar el idioma (locale) de la aplicación.
 * Permite guardar el idioma seleccionado por el usuario y aplicarlo correctamente
 * en cada arranque de la aplicación.
 */
object LocaleHelper {

    // Nombre del archivo de preferencias compartidas donde se guarda el idioma
    private const val PREF_NAME = "settings"

    // Clave usada para guardar el idioma en SharedPreferences
    private const val KEY_LANGUAGE = "selected_language"

    /**
     * Aplica un nuevo idioma a la app y lo guarda en preferencias.
     *
     * @param context Contexto de la aplicación o actividad
     * @param language Código del idioma (por ejemplo "es" o "en")
     */
    fun setLocale(context: Context, language: String) {
        // Guardamos la elección en las preferencias
        saveLanguagePreference(context, language)

        // Configuramos la nueva locale
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)

        // Actualizamos la configuración del contexto
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    /**
     * Carga el idioma previamente guardado (si existe) y lo aplica.
     * Esta función se llama al iniciar la aplicación (por ejemplo, desde MainActivity).
     *
     * @param context Contexto actual
     */
    fun loadLocale(context: Context) {
        val language = getSavedLanguage(context)
        setLocale(context, language)
    }

    /**
     * Obtiene el idioma guardado en las preferencias. Por defecto devuelve inglés ("en").
     *
     * @param context Contexto actual
     * @return Código del idioma guardado
     */
    fun getSavedLanguage(context: Context): String {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_LANGUAGE, "en") ?: "en"
    }

    /**
     * Guarda el idioma seleccionado en las preferencias compartidas.
     *
     * @param context Contexto actual
     * @param language Código del idioma
     */
    private fun saveLanguagePreference(context: Context, language: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit {
            putString(KEY_LANGUAGE, language)
        }
    }
}
