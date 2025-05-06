package com.example.ejercicioexamenwordle

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ejercicioexamenwordle.databinding.FragmentGameBinding
import java.util.*

/**
 * Fragmento principal del juego de Wordle.
 * Muestra la interfaz de juego, gestiona los intentos del jugador
 * y actualiza el estado del juego.
 */
class GameFragment : Fragment() {

    // ViewBinding para acceder a las vistas de forma segura
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    // ViewModel compartido entre fragments para mantener datos persistentes
    private val gameViewModel: GameViewModel by activityViewModels()

    // Índice de fila actual (de 0 a 4)
    private var currentRowIndex = 0

    // Palabra secreta como array de caracteres
    private lateinit var secretWord: CharArray

    // Bandera que indica si la partida ha finalizado
    private var juegoTerminado = false

    // Listas de palabras por idioma
    private val wordListEs = listOf("PERRO", "CASAS", "RATON", "FUEGO", "PLAZA", "FLOTA", "TORRE", "PULPO", "MOVER", "SALIR")
    private val wordListEn = listOf("PLANT", "HOUSE", "WATER", "FLOOR", "CHAIR", "FRUIT", "GHOST", "MOUSE", "LIGHT", "BRAIN")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Configura el estado inicial del fragmento al crearse la vista.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iniciarAnimacionFondo()

        // Mostrar el saludo con el nombre del jugador
        binding.tvPlayerName.text = getString(R.string.saludo_jugador, gameViewModel.playerName)

        // Seleccionar lista de palabras según el idioma
        val idioma = Locale.getDefault().language
        val selectedList = if (idioma == "es") wordListEs else wordListEn

        // Si no hay palabra secreta aún, iniciar una nueva
        if (gameViewModel.secretWord.isEmpty()) {
            gameViewModel.resetGame(selectedList)
        }

        secretWord = gameViewModel.secretWord.toCharArray()

        mostrarPista()
        actualizarIntentosRestantes()

        // Botones
        binding.btnValidate.setOnClickListener { validarIntento() }
        binding.btnRestart.setOnClickListener { reiniciarJuego() }
    }

    /**
     * Inicia la animación de fondo del layout principal.
     */
    private fun iniciarAnimacionFondo() {
        val layout = binding.gameLayout
        val animDrawable = layout.background as? AnimationDrawable
        animDrawable?.setEnterFadeDuration(1500)
        animDrawable?.setExitFadeDuration(1500)
        animDrawable?.start()
    }

    /**
     * Muestra una pista basada en la palabra secreta y el idioma actual.
     */
    private fun mostrarPista() {
        val wordKey = gameViewModel.secretWord.lowercase(Locale.ROOT)
        val hintResId = resources.getIdentifier("hint_$wordKey", "string", requireContext().packageName)
        if (hintResId != 0) {
            val pista = getString(hintResId)
            binding.tvHint.text = getString(R.string.pista_formato, pista)
        } else {
            binding.tvHint.text = ""
        }
    }

    /**
     * Valida el intento del jugador y actualiza colores y estado del juego.
     */
    private fun validarIntento() {
        if (juegoTerminado || currentRowIndex >= 5) return

        val rowId = resources.getIdentifier("row${currentRowIndex + 1}", "id", requireContext().packageName)
        val rowLayout = view?.findViewById<LinearLayout>(rowId) ?: return

        val letrasUsuario = mutableListOf<Char>()
        val letraBoxes = mutableListOf<EditText>()

        // Extraer letras ingresadas
        for (i in 0 until rowLayout.childCount) {
            val editText = rowLayout.getChildAt(i) as EditText
            val letra = editText.text.toString().uppercase()
            if (letra.length != 1) {
                showToast(R.string.error_letras_incompletas)
                return
            }
            letrasUsuario.add(letra[0])
            letraBoxes.add(editText)
        }

        // Comparar letra por letra
        for (i in 0..4) {
            val letra = letrasUsuario[i]
            val editBox = letraBoxes[i]
            when {
                letra == secretWord[i] -> editBox.setBackgroundColor(Color.GREEN)
                letra in secretWord -> editBox.setBackgroundColor(Color.rgb(255, 165, 0)) // Naranja
                else -> editBox.setBackgroundColor(Color.LTGRAY)
            }

            // Animación y bloqueo del campo
            val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.scale_up)
            editBox.startAnimation(anim)
            editBox.isEnabled = false
        }

        // Comprobar si ha ganado
        if (letrasUsuario.joinToString("") == secretWord.joinToString("")) {
            juegoTerminado = true
            mostrarDialogoFinal(
                getString(R.string.congratulations_title),
                getString(R.string.congratulations_message, gameViewModel.playerName)
            )
            return
        }

        // No acertó, avanzar intento
        gameViewModel.remainingAttempts--
        currentRowIndex++
        actualizarIntentosRestantes()

        // Comprobar si perdió
        if (gameViewModel.remainingAttempts == 0) {
            val shake = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
            rowLayout.startAnimation(shake)
            juegoTerminado = true
            mostrarDialogoFinal(
                getString(R.string.game_over_title),
                getString(R.string.game_over_message)
            )
        }
    }

    /**
     * Actualiza el contador de intentos restantes en pantalla.
     */
    private fun actualizarIntentosRestantes() {
        binding.tvRemainingAttempts.text = getString(R.string.intentos_restantes, gameViewModel.remainingAttempts)
    }

    /**
     * Reinicia el juego desde cero, limpiando inputs y generando nueva palabra.
     */
    private fun reiniciarJuego() {
        val idioma = Locale.getDefault().language
        val selectedList = if (idioma == "es") wordListEs else wordListEn

        gameViewModel.resetGame(selectedList)
        secretWord = gameViewModel.secretWord.toCharArray()
        juegoTerminado = false
        currentRowIndex = 0

        // Limpiar campos de texto
        for (i in 1..5) {
            val rowId = resources.getIdentifier("row$i", "id", requireContext().packageName)
            val rowLayout = view?.findViewById<LinearLayout>(rowId) ?: continue
            for (j in 0 until rowLayout.childCount) {
                val editText = rowLayout.getChildAt(j) as EditText
                editText.setText("")
                editText.setBackgroundResource(android.R.drawable.editbox_background)
                editText.isEnabled = true
            }
        }

        mostrarPista()
        actualizarIntentosRestantes()

        // Animación de botones
        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.scale_up)
        binding.btnValidate.startAnimation(anim)
        binding.btnRestart.startAnimation(anim)
    }

    /**
     * Muestra un diálogo cuando el jugador gana o pierde.
     */
    private fun mostrarDialogoFinal(titulo: String, mensaje: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(titulo)
            .setMessage(mensaje)
            .setCancelable(false)
            .setPositiveButton(R.string.button_restart) { _, _ -> reiniciarJuego() }
            .setNegativeButton(R.string.exit_app) { _, _ -> requireActivity().finish() }
            .show()
    }

    /**
     * Muestra un mensaje Toast corto.
     */
    private fun showToast(resId: Int) {
        Toast.makeText(requireContext(), getString(resId), Toast.LENGTH_SHORT).show()
    }

    /**
     * Limpia la referencia del binding cuando se destruye la vista.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
