package com.fei.proyectofinal

import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

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

    private var timer: CountDownTimer? = null
    private var enRetroalimentacion = false

    private lateinit var tts: TextToSpeech
    private var ttsListo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego)

        tvContador = findViewById(R.id.tvContadorPreguntas)
        tvTemporizador = findViewById(R.id.tvTemporizador)
        tvBurbujaPregunta = findViewById(R.id.tvBurbujaPregunta)
        btnRespuesta1 = findViewById(R.id.btnRespuesta1)
        btnRespuesta2 = findViewById(R.id.btnRespuesta2)
        btnRespuesta3 = findViewById(R.id.btnRespuesta3)
        btnRespuesta4 = findViewById(R.id.btnRespuesta4)

        tts = TextToSpeech(this, this)

        val operaciones = intent.getStringExtra("operaciones")?.split(",") ?: listOf("SUMA")
        val dificultad = intent.getStringExtra("dificultad") ?: "FACIL"
        val tiposPreguntas = intent.getStringExtra("tiposPreguntas")?.split(",") ?: listOf("OPERACIONES")
        tiempoPorPregunta = intent.getIntExtra("tiempo", 0)
        cantidadTotal = intent.getIntExtra("cantidad", 5)

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

        // Leer automáticamente
        leerEnunciado(pregunta.enunciado)

        // Tocar la burbuja repite el audio
        tvBurbujaPregunta.setOnClickListener {
            leerEnunciado(pregunta.enunciado)
        }

        val botones = listOf(btnRespuesta1, btnRespuesta2, btnRespuesta3, btnRespuesta4)
        for (i in botones.indices) {
            botones[i].text = pregunta.respuestas[i].toString()
            botones[i].isEnabled = true
            botones[i].visibility = Button.VISIBLE
            botones[i].setOnClickListener { verificarRespuesta(i, pregunta) }
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
            tvBurbujaPregunta.text = "✅ ${pregunta.retroalimentacion}"
        } else {
            tvBurbujaPregunta.text = "❌ Incorrecto. ${pregunta.retroalimentacion}"
        }

        leerEnunciado(pregunta.retroalimentacion)

        btnRespuesta2.visibility = Button.INVISIBLE
        btnRespuesta3.visibility = Button.INVISIBLE
        btnRespuesta4.visibility = Button.INVISIBLE
        btnRespuesta1.text = "Siguiente →"
        btnRespuesta1.setOnClickListener {
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
        tvContador.text = "¡Juego terminado!"
        tvBurbujaPregunta.text = "🎉 Acertaste $aciertos de $cantidadTotal"
        tvTemporizador.text = ""

        leerEnunciado("Juego terminado. Acertaste $aciertos de $cantidadTotal")

        btnRespuesta2.visibility = Button.INVISIBLE
        btnRespuesta3.visibility = Button.INVISIBLE
        btnRespuesta4.visibility = Button.INVISIBLE
        btnRespuesta1.text = "Finalizar"
        btnRespuesta1.setOnClickListener {
            // TODO: Cambiar por ResultadoActivity cuando esté lista
            finish()
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