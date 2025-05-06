package com.example.ejercicioexamenwordle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.ejercicioexamenwordle.databinding.ActivityMainBinding

/**
 * Clase principal de la aplicación.
 * Esta actividad sirve como contenedor para los fragments gestionados por Navigation Component.
 * Se encarga de inicializar el idioma guardado y de vincular la vista principal con su binding.
 */
class MainActivity : AppCompatActivity() {

    // Enlace con la vista a través de ViewBinding para acceder de forma segura a los elementos del layout.
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Cargamos el idioma preferido del usuario ANTES de inflar el layout, para que se apliquen los textos correctamente.
        LocaleHelper.loadLocale(this)

        // Llamada obligatoria al metodo onCreate del padre (AppCompatActivity)
        super.onCreate(savedInstanceState)

        // Inflamos el layout usando ViewBinding y lo asociamos como contenido de la actividad.
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    /**
     * Metodo llamado para permitir la navegación hacia atrás entre fragments.
     */
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
