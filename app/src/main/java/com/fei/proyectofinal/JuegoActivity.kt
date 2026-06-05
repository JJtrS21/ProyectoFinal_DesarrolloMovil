package com.fei.proyectofinal

import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.core.view.WindowCompat

class JuegoActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tvContador: TextView
    private lateinit var tvTemporizador: TextView
    private lateinit var tvBurbujaPregunta: TextView
    private lateinit var btnRespuesta1: Button
    private lateinit var btnRespuesta2: Button
    private lateinit var btnRespuesta3: Button
    private lateinit var btnRespuesta4: Button

    private lateinit var preguntas: List<Pregunta>
    private var indiceActual = 0
    private var aciertos = 0
    private var tiempoPorPregunta = 0
    private var cantidadTotal = 0
    private var tipoJuego = "Personalizado"
    private var operacionesStr = ""
    private var dificultadStr = "FACIL"
    private var perfilActual = "Sin perfil"
    private var timer: CountDownTimer? = null
    private var enRetroalimentacion = false

    private lateinit var tts: TextToSpeech
    private var ttsListo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_juego)
        ocultarBarras()

        tvContador = findViewById(R.id.tvContadorPreguntas)
        tvTemporizador = findViewById(R.id.tvTemporizador)
        tvBurbujaPregunta = findViewById(R.id.tvBurbujaPregunta)
        btnRespuesta1 = findViewById(R.id.btnRespuesta1)
        btnRespuesta2 = findViewById(R.id.btnRespuesta2)
        btnRespuesta3 = findViewById(R.id.btnRespuesta3)
        btnRespuesta4 = findViewById(R.id.btnRespuesta4)

        // Perfil y tipo de juego
        perfilActual = intent.getStringExtra("nombrePerfil") ?: "Sin perfil"
        tipoJuego = intent.getStringExtra("tipoJuego") ?: "Personalizado"

        // Inicializa el lector de voz
        tts = TextToSpeech(this, this)

        val operaciones = intent.getStringExtra("operaciones")?.split(",") ?: listOf("SUMA")
        val dificultad = intent.getStringExtra("dificultad") ?: "FACIL"
        val tiposPreguntas = intent.getStringExtra("tiposPreguntas")?.split(",") ?: listOf("OPERACIONES")

        tiempoPorPregunta = intent.getIntExtra("tiempo", 0)
        cantidadTotal = intent.getIntExtra("cantidad", 5)

        // Guarda estos datos como texto para registrarlos en SQLite
        operacionesStr = intent.getStringExtra("operaciones") ?: "SUMA"
        dificultadStr = intent.getStringExtra("dificultad") ?: "FACIL"

        preguntas = BancoPreguntas.filtrar(operaciones, dificultad, tiposPreguntas)

        if (preguntas.isEmpty()) {
            Toast.makeText(this, "No hay preguntas con esos filtros", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val base = preguntas.toList()

        while (preguntas.size < cantidadTotal) {
            preguntas = preguntas + base.random()
        }

        preguntas = preguntas.take(cantidadTotal)

        indiceActual = 0
        aciertos = 0

        mostrarPregunta()
    }

    private fun ocultarBarras() {
        supportActionBar?.hide()

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            WindowCompat.setDecorFitsSystemWindows(window, false)

            window.insetsController?.hide(
                WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars()
            )

            window.insetsController?.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }

    // ==================== TTS ====================

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale("es", "MX")
            ttsListo = true

            if (::preguntas.isInitialized && preguntas.isNotEmpty() && indiceActual < preguntas.size) {
                leerEnunciado(preguntas[indiceActual].enunciado)
            }
        }
    }

    private fun textoParaLeer(texto: String): String {
        return texto
            .replace("+", " más ")
            .replace("-", " menos ")
            .replace("×", " por ")
            .replace("÷", " entre ")
            .replace("=", " igual a ")
    }

    private fun leerEnunciado(texto: String) {
        if (::tts.isInitialized && ttsListo) {
            val textoCorregido = textoParaLeer(texto)

            tts.stop()
            tts.speak(textoCorregido, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    // ==================== MOSTRAR PREGUNTA ====================

    private fun mostrarPregunta() {
        if (indiceActual >= preguntas.size) {
            finalizarJuego()
            return
        }

        enRetroalimentacion = false

        val pregunta = preguntas[indiceActual]

        tvContador.text = "Pregunta ${indiceActual + 1} de $cantidadTotal"
        tvBurbujaPregunta.text = pregunta.enunciado

        // Lee automáticamente la pregunta
        leerEnunciado(pregunta.enunciado)

        // Al tocar la burbuja, repite el audio
        tvBurbujaPregunta.setOnClickListener {
            leerEnunciado(pregunta.enunciado)
        }

        val botones = listOf(btnRespuesta1, btnRespuesta2, btnRespuesta3, btnRespuesta4)

        for (i in botones.indices) {
            botones[i].text = pregunta.respuestas[i].toString()
            botones[i].isEnabled = true
            botones[i].visibility = Button.VISIBLE
            botones[i].setOnClickListener {
                verificarRespuesta(i, pregunta)
            }
        }

        if (tiempoPorPregunta > 0) {
            iniciarTemporizador()
        } else {
            tvTemporizador.text = "⏱ ∞"
        }
    }

    // ==================== VERIFICAR RESPUESTA ====================

    private fun verificarRespuesta(indiceSeleccionado: Int, pregunta: Pregunta) {
        if (enRetroalimentacion) return

        enRetroalimentacion = true
        timer?.cancel()

        val esCorrecta = indiceSeleccionado == pregunta.respuestaCorrecta

        if (esCorrecta) {
            aciertos++
            tvBurbujaPregunta.text = "✅ ¡Correcto! ${pregunta.retroalimentacion}"
            leerEnunciado("Correcto. ${pregunta.retroalimentacion}")
        } else {
            tvBurbujaPregunta.text = "❌ Incorrecto. ${pregunta.retroalimentacion}"
            leerEnunciado("Incorrecto. ${pregunta.retroalimentacion}")
        }

        btnRespuesta1.visibility = Button.INVISIBLE
        btnRespuesta2.visibility = Button.VISIBLE
        btnRespuesta3.visibility = Button.INVISIBLE
        btnRespuesta4.visibility = Button.INVISIBLE

        btnRespuesta2.text = "Siguiente →"
        btnRespuesta2.setOnClickListener {
            indiceActual++
            mostrarPregunta()
        }
    }

    // ==================== TEMPORIZADOR ====================

    private fun iniciarTemporizador() {
        timer?.cancel()

        val tiempoMilis = tiempoPorPregunta * 1000L
        tvTemporizador.text = "⏱ $tiempoPorPregunta"

        timer = object : CountDownTimer(tiempoMilis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tvTemporizador.text = "⏱ ${millisUntilFinished / 1000}"
            }

            override fun onFinish() {
                tvTemporizador.text = "⏱ 0"
                tiempoAgotado()
            }
        }.start()
    }

    private fun tiempoAgotado() {
        if (enRetroalimentacion) return

        enRetroalimentacion = true

        val pregunta = preguntas[indiceActual]

        tvBurbujaPregunta.text = "⏰ ¡Tiempo fuera! ${pregunta.retroalimentacion}"
        leerEnunciado("Tiempo fuera. ${pregunta.retroalimentacion}")

        btnRespuesta2.visibility = Button.INVISIBLE
        btnRespuesta3.visibility = Button.INVISIBLE
        btnRespuesta4.visibility = Button.INVISIBLE

        btnRespuesta1.text = "Siguiente →"
        btnRespuesta1.setOnClickListener {
            indiceActual++
            mostrarPregunta()
        }
    }

    // ==================== FINALIZAR ====================

    private fun finalizarJuego() {
        timer?.cancel()

        // Guarda la partida solo si hay un perfil válido
        if (perfilActual.isNotEmpty() && perfilActual != "Sin perfil") {
            val db = SQLiteHelper(this)

            db.insertarPartida(
                perfil = perfilActual,
                tipo = tipoJuego,
                operaciones = operacionesStr,
                dificultad = dificultadStr,
                tiempo = tiempoPorPregunta,
                aciertos = aciertos,
                total = cantidadTotal
            )
        }

        tvContador.text = "¡Juego terminado!"
        tvBurbujaPregunta.text = "🎉 Acertaste $aciertos de $cantidadTotal"
        tvTemporizador.text = ""

        leerEnunciado("Juego terminado. Acertaste $aciertos de $cantidadTotal")

        btnRespuesta1.visibility = Button.INVISIBLE
        btnRespuesta2.visibility = Button.VISIBLE
        btnRespuesta3.visibility = Button.INVISIBLE
        btnRespuesta4.visibility = Button.INVISIBLE

        btnRespuesta2.text = "Finalizar"
        btnRespuesta2.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        ocultarBarras()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if (hasFocus) {
            ocultarBarras()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        timer?.cancel()

        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
    }
}