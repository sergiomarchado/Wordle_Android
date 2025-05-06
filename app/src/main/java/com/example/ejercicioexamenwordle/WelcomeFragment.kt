package com.example.ejercicioexamenwordle

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.example.ejercicioexamenwordle.databinding.FragmentWelcomeBinding
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

/**
 * Fragmento inicial donde el usuario introduce su nombre y selecciona el idioma.
 * También contiene animaciones visuales y lógica para cambiar el idioma dinámicamente.
 */
class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    // ViewModel compartido entre fragments
    private val gameViewModel: GameViewModel by activityViewModels()

    // Variable que almacena el idioma actualmente seleccionado
    private var idiomaSeleccionadoAnterior: String = "en"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Animación de fondo
        iniciarAnimacionFondo()

        // Animación del logo
        animarLogo()

        // Marcar el idioma guardado previamente
        marcarIdiomaActual()

        // Cambio de idioma mediante RadioButtons
        binding.radioGroupLanguage.setOnCheckedChangeListener { _, checkedId ->
            val nuevoIdioma = when (checkedId) {
                R.id.rbEnglish -> "en"
                R.id.rbSpanish -> "es"
                else -> return@setOnCheckedChangeListener
            }

            Log.d("WordleIdioma", "Seleccionado: $nuevoIdioma | Anterior: $idiomaSeleccionadoAnterior")

            // Solo se aplica el cambio si realmente ha cambiado el idioma
            if (nuevoIdioma != idiomaSeleccionadoAnterior) {
                idiomaSeleccionadoAnterior = nuevoIdioma
                LocaleHelper.setLocale(requireContext(), nuevoIdioma)
                requireActivity().recreate() // Reinicia para aplicar el idioma
            } else {
                Log.d("WordleIdioma", "Idioma ya activo, no se cambia")
            }
        }

        // Botón para comenzar el juego
        binding.btnStartGame.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()

            if (username.isEmpty()) {
                // Mostrar mensaje si no se ha introducido el nombre
                Snackbar.make(binding.root, R.string.snackbar_empty_name, Snackbar.LENGTH_SHORT).show()
            } else {
                // Guardamos los datos en el ViewModel
                gameViewModel.playerName = username
                gameViewModel.selectedLanguage =
                    if (binding.rbEnglish.isChecked) "en" else "es"

                // Navegamos al fragment del juego
                findNavController().navigate(R.id.action_WelcomeFragment_to_GameFragment)
            }
        }
    }

    /**
     * Inicia la animación del fondo del layout si es de tipo AnimationDrawable.
     */
    private fun iniciarAnimacionFondo() {
        val layout = binding.welcomeLayout
        val animationDrawable = layout.background as? AnimationDrawable
        animationDrawable?.setEnterFadeDuration(1500)
        animationDrawable?.setExitFadeDuration(1500)
        animationDrawable?.start()
    }

    /**
     * Aplica una animación de rebote al logo de Wordle.
     */
    private fun animarLogo() {
        val bounce = AnimationUtils.loadAnimation(requireContext(), R.anim.bounce)
        binding.imgGameLogo.startAnimation(bounce)
    }

    /**
     * Marca el RadioButton correspondiente al idioma guardado previamente.
     * También actualiza la variable de seguimiento del idioma.
     */
    private fun marcarIdiomaActual() {
        val actual = LocaleHelper.getSavedLanguage(requireContext())
        idiomaSeleccionadoAnterior = actual
        Log.d("WordleIdioma", "Idioma guardado: $actual")

        when (actual) {
            "en" -> binding.rbEnglish.isChecked = true
            "es" -> binding.rbSpanish.isChecked = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
