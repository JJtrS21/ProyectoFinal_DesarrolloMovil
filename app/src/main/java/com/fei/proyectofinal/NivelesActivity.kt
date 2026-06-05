package com.fei.proyectofinal

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.core.view.WindowCompat

class NivelesActivity : AppCompatActivity() {

    private var nivelCentral = 1
    private val totalNiveles = 10

    // Datos del perfil seleccionado
    private var idPerfil = -1
    private var nombrePerfil = "Sin perfil"
    private var iconoPerfil = R.drawable.que_rayos

    private lateinit var btnAnterior: Button
    private lateinit var btnSiguiente: Button
    private lateinit var btnRegresar: Button
    private lateinit var nivelIzquierdo: View
    private lateinit var nivelCentralView: View
    private lateinit var nivelDerecho: View
    private lateinit var ivNivelIzquierdo: ImageView
    private lateinit var ivNivelCentral: ImageView
    private lateinit var ivNivelDerecho: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fuerza la pantalla en modo horizontal
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        // Carga el diseño de niveles
        setContentView(R.layout.activity_niveles)

        // Oculta barras del sistema
        ocultarBarras()

        // Recibe el perfil seleccionado desde Modos
        idPerfil = intent.getIntExtra("idPerfil", -1)
        nombrePerfil = intent.getStringExtra("nombrePerfil") ?: "Sin perfil"
        iconoPerfil = intent.getIntExtra("iconoPerfil", R.drawable.que_rayos)

        // Referencias a los botones y vistas del carrusel
        btnAnterior = findViewById(R.id.btnAnterior)
        btnSiguiente = findViewById(R.id.btnSiguiente)
        btnRegresar = findViewById(R.id.btnRegresarNiveles)
        nivelIzquierdo = findViewById(R.id.btnNivelIzquierdo)
        nivelCentralView = findViewById(R.id.btnNivelCentral)
        nivelDerecho = findViewById(R.id.btnNivelDerecho)
        ivNivelIzquierdo = findViewById(R.id.ivNivelIzquierdo)
        ivNivelCentral = findViewById(R.id.ivNivelCentral)
        ivNivelDerecho = findViewById(R.id.ivNivelDerecho)

        // Muestra los niveles iniciales
        actualizarCarrusel()

        // Mueve el carrusel hacia el nivel anterior
        btnAnterior.setOnClickListener {
            if (nivelCentral > 1) {
                nivelCentral--
                actualizarCarrusel()
            }
        }

        // Mueve el carrusel hacia el siguiente nivel
        btnSiguiente.setOnClickListener {
            if (nivelCentral < totalNiveles) {
                nivelCentral++
                actualizarCarrusel()
            }
        }

        // Tocar el nivel izquierdo también retrocede
        nivelIzquierdo.setOnClickListener {
            if (nivelCentral > 1) {
                nivelCentral--
                actualizarCarrusel()
            }
        }

        // Tocar el nivel derecho también avanza
        nivelDerecho.setOnClickListener {
            if (nivelCentral < totalNiveles) {
                nivelCentral++
                actualizarCarrusel()
            }
        }

        // Inicia el juego con el nivel central seleccionado
        nivelCentralView.setOnClickListener {
            iniciarJuego(nivelCentral)
        }

        // Regresa a la pantalla anterior
        btnRegresar.setOnClickListener {
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

    private fun actualizarCarrusel() {
        val izquierdo = nivelCentral - 1
        val derecho = nivelCentral + 1

        // Muestra nivel izquierdo si existe
        if (izquierdo >= 1) {
            nivelIzquierdo.visibility = View.VISIBLE
            ponerImagenONumero(ivNivelIzquierdo, izquierdo)
        } else {
            nivelIzquierdo.visibility = View.INVISIBLE
        }

        // Muestra nivel central
        ponerImagenONumero(ivNivelCentral, nivelCentral)

        // Muestra nivel derecho si existe
        if (derecho <= totalNiveles) {
            nivelDerecho.visibility = View.VISIBLE
            ponerImagenONumero(ivNivelDerecho, derecho)
        } else {
            nivelDerecho.visibility = View.INVISIBLE
        }

        // Activa o desactiva flechas según el nivel actual
        btnAnterior.isEnabled = nivelCentral > 1
        btnSiguiente.isEnabled = nivelCentral < totalNiveles
    }

    private fun ponerImagenONumero(imageView: ImageView, numero: Int) {
        // Busca una imagen llamada a1, a2, a3, etc.
        val resId = resources.getIdentifier("a$numero", "drawable", packageName)

        if (resId != 0) {
            imageView.setImageResource(resId)
        } else {
            imageView.setImageResource(0)
        }
    }

    private fun iniciarJuego(nivel: Int) {
        val intentJuego = Intent(this, JuegoActivity::class.java)

        // Configuración de cada nivel
        when (nivel) {
            1 -> {
                intentJuego.putExtra("operaciones", "SUMA")
                intentJuego.putExtra("dificultad", "FACIL")
                intentJuego.putExtra("tiempo", 0)
                intentJuego.putExtra("tiposPreguntas", "OPERACIONES")
                intentJuego.putExtra("cantidad", 5)
            }

            2 -> {
                intentJuego.putExtra("operaciones", "SUMA,RESTA")
                intentJuego.putExtra("dificultad", "FACIL")
                intentJuego.putExtra("tiempo", 0)
                intentJuego.putExtra("tiposPreguntas", "OPERACIONES")
                intentJuego.putExtra("cantidad", 5)
            }

            3 -> {
                intentJuego.putExtra("operaciones", "SUMA,RESTA")
                intentJuego.putExtra("dificultad", "FACIL")
                intentJuego.putExtra("tiempo", 30)
                intentJuego.putExtra("tiposPreguntas", "OPERACIONES")
                intentJuego.putExtra("cantidad", 5)
            }

            4 -> {
                intentJuego.putExtra("operaciones", "SUMA,RESTA,MULTIPLICACION")
                intentJuego.putExtra("dificultad", "FACIL")
                intentJuego.putExtra("tiempo", 30)
                intentJuego.putExtra("tiposPreguntas", "OPERACIONES")
                intentJuego.putExtra("cantidad", 5)
            }

            5 -> {
                intentJuego.putExtra("operaciones", "SUMA,RESTA,MULTIPLICACION,DIVISION")
                intentJuego.putExtra("dificultad", "FACIL")
                intentJuego.putExtra("tiempo", 30)
                intentJuego.putExtra("tiposPreguntas", "OPERACIONES")
                intentJuego.putExtra("cantidad", 5)
            }

            6 -> {
                intentJuego.putExtra("operaciones", "SUMA,RESTA,MULTIPLICACION,DIVISION")
                intentJuego.putExtra("dificultad", "MEDIO")
                intentJuego.putExtra("tiempo", 30)
                intentJuego.putExtra("tiposPreguntas", "OPERACIONES")
                intentJuego.putExtra("cantidad", 7)
            }

            7 -> {
                intentJuego.putExtra("operaciones", "SUMA,RESTA,MULTIPLICACION,DIVISION")
                intentJuego.putExtra("dificultad", "MEDIO")
                intentJuego.putExtra("tiempo", 30)
                intentJuego.putExtra("tiposPreguntas", "OPERACIONES,PROBLEMAS")
                intentJuego.putExtra("cantidad", 7)
            }

            8 -> {
                intentJuego.putExtra("operaciones", "SUMA,RESTA,MULTIPLICACION,DIVISION")
                intentJuego.putExtra("dificultad", "MEDIO")
                intentJuego.putExtra("tiempo", 15)
                intentJuego.putExtra("tiposPreguntas", "OPERACIONES,PROBLEMAS")
                intentJuego.putExtra("cantidad", 7)
            }

            9 -> {
                intentJuego.putExtra("operaciones", "SUMA,RESTA,MULTIPLICACION,DIVISION")
                intentJuego.putExtra("dificultad", "DIFICIL")
                intentJuego.putExtra("tiempo", 30)
                intentJuego.putExtra("tiposPreguntas", "OPERACIONES,PROBLEMAS")
                intentJuego.putExtra("cantidad", 10)
            }

            10 -> {
                intentJuego.putExtra("operaciones", "SUMA,RESTA,MULTIPLICACION,DIVISION")
                intentJuego.putExtra("dificultad", "DIFICIL")
                intentJuego.putExtra("tiempo", 15)
                intentJuego.putExtra("tiposPreguntas", "OPERACIONES,PROBLEMAS")
                intentJuego.putExtra("cantidad", 10)
            }
        }

        // Datos del tipo de juego
        intentJuego.putExtra("tipoJuego", "Nivel $nivel")

        // Mantiene los datos del perfil seleccionado
        intentJuego.putExtra("idPerfil", idPerfil)
        intentJuego.putExtra("nombrePerfil", nombrePerfil)
        intentJuego.putExtra("iconoPerfil", iconoPerfil)

        startActivity(intentJuego)
    }
}