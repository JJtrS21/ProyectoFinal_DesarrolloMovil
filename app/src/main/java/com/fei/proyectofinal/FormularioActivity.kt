package com.fei.proyectofinal

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.core.view.WindowCompat

class FormularioActivity : AppCompatActivity() {

    // Variables para guardar los datos del perfil seleccionado
    private var idPerfil = -1
    private var nombrePerfil = "Sin perfil"
    private var iconoPerfil = R.drawable.que_rayos

    // Variables para las selecciones del formulario
    private var dificultadSeleccionada = "FACIL"
    private var tiempoSeleccionado = 0 // 0 = sin límite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fuerza la pantalla en modo horizontal
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        // Carga el diseño del formulario personalizado
        setContentView(R.layout.activity_formulario)

        // Oculta barras del sistema
        ocultarBarras()

        // Recibe los datos del perfil seleccionado desde Modos
        idPerfil = intent.getIntExtra("idPerfil", -1)
        nombrePerfil = intent.getStringExtra("nombrePerfil") ?: "Sin perfil"
        iconoPerfil = intent.getIntExtra("iconoPerfil", R.drawable.que_rayos)

        configurarDificultad()
        configurarTiempo()
        configurarCantidad()
        configurarAyuda()
        configurarBotonRegresar()
        configurarBotonJugar()
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

    private fun configurarDificultad() {
        val btnFacil = findViewById<Button>(R.id.btnFacil)
        val btnMedio = findViewById<Button>(R.id.btnMedio)
        val btnDificil = findViewById<Button>(R.id.btnDificil)

        btnFacil.setOnClickListener {
            dificultadSeleccionada = "FACIL"

            btnFacil.backgroundTintList = getColorStateList(R.color.primary)
            btnFacil.setTextColor(getColor(R.color.on_primary))

            btnMedio.backgroundTintList = getColorStateList(R.color.primary_container)
            btnMedio.setTextColor(getColor(R.color.on_surface))

            btnDificil.backgroundTintList = getColorStateList(R.color.primary_container)
            btnDificil.setTextColor(getColor(R.color.on_surface))
        }

        btnMedio.setOnClickListener {
            dificultadSeleccionada = "MEDIO"

            btnMedio.backgroundTintList = getColorStateList(R.color.primary)
            btnMedio.setTextColor(getColor(R.color.on_primary))

            btnFacil.backgroundTintList = getColorStateList(R.color.primary_container)
            btnFacil.setTextColor(getColor(R.color.on_surface))

            btnDificil.backgroundTintList = getColorStateList(R.color.primary_container)
            btnDificil.setTextColor(getColor(R.color.on_surface))
        }

        btnDificil.setOnClickListener {
            dificultadSeleccionada = "DIFICIL"

            btnDificil.backgroundTintList = getColorStateList(R.color.primary)
            btnDificil.setTextColor(getColor(R.color.on_primary))

            btnFacil.backgroundTintList = getColorStateList(R.color.primary_container)
            btnFacil.setTextColor(getColor(R.color.on_surface))

            btnMedio.backgroundTintList = getColorStateList(R.color.primary_container)
            btnMedio.setTextColor(getColor(R.color.on_surface))
        }
    }

    private fun configurarTiempo() {
        val btnSinLimite = findViewById<Button>(R.id.btnSinLimite)
        val btn30seg = findViewById<Button>(R.id.btn30seg)
        val btn15seg = findViewById<Button>(R.id.btn15seg)

        btnSinLimite.setOnClickListener {
            tiempoSeleccionado = 0

            btnSinLimite.backgroundTintList = getColorStateList(R.color.primary)
            btnSinLimite.setTextColor(getColor(R.color.on_primary))

            btn30seg.backgroundTintList = getColorStateList(R.color.primary_container)
            btn30seg.setTextColor(getColor(R.color.on_surface))

            btn15seg.backgroundTintList = getColorStateList(R.color.primary_container)
            btn15seg.setTextColor(getColor(R.color.on_surface))
        }

        btn30seg.setOnClickListener {
            tiempoSeleccionado = 30

            btn30seg.backgroundTintList = getColorStateList(R.color.primary)
            btn30seg.setTextColor(getColor(R.color.on_primary))

            btnSinLimite.backgroundTintList = getColorStateList(R.color.primary_container)
            btnSinLimite.setTextColor(getColor(R.color.on_surface))

            btn15seg.backgroundTintList = getColorStateList(R.color.primary_container)
            btn15seg.setTextColor(getColor(R.color.on_surface))
        }

        btn15seg.setOnClickListener {
            tiempoSeleccionado = 15

            btn15seg.backgroundTintList = getColorStateList(R.color.primary)
            btn15seg.setTextColor(getColor(R.color.on_primary))

            btnSinLimite.backgroundTintList = getColorStateList(R.color.primary_container)
            btnSinLimite.setTextColor(getColor(R.color.on_surface))

            btn30seg.backgroundTintList = getColorStateList(R.color.primary_container)
            btn30seg.setTextColor(getColor(R.color.on_surface))
        }
    }

    private fun configurarCantidad() {
        val seekBar = findViewById<SeekBar>(R.id.seekBarCantidad)
        val tvValor = findViewById<TextView>(R.id.tvValorCantidad)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvValor.text = (progress + 1).toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun configurarAyuda() {
        val btnAyuda = findViewById<Button>(R.id.btnAyuda)

        btnAyuda.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Explicación de las opciones")
                .setMessage(
                    """
                    Elige las operaciones: suma, resta, multiplicación o división. Puedes combinar todas las que quieras.
                    
                    ¿Qué significan las dificultades?
                    Fácil – Sumas y restas hasta 10. Tablas del 1, 2, 3 y 10.
                    Medio – Sumas y restas hasta 20. Tablas del 4, 5 y 6.
                    Difícil – Sumas y restas hasta 100. Tablas del 7, 8 y 9.
                    
                    ¿Cómo funciona el tiempo por pregunta?
                    Sin límite – Aprendes sin apuros, sin presión de tiempo.
                    30 segundos – Tiempo justo para pensar y responder.
                    15 segundos – Solo si ya eres muy rápido. ¡Modo desafío!
                    
                    ¿Qué tipo de preguntas hay?
                    Operaciones – Operaciones directas como "3 + 5 = ?"
                    Problemas – Problemas con historia como "Pepito tenía 3 manzanas y regaló 2. ¿Cuántas le quedan?"
                    
                    💡 CONSEJO: Durante el juego, toca la burbuja de diálogo del zorrito para escuchar de nuevo la pregunta.
                    """.trimIndent()
                )
                .setPositiveButton("¡ENTENDIDO!") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }

    private fun configurarBotonRegresar() {
        val btnRegresar = findViewById<Button>(R.id.btnRegresar)

        btnRegresar.setOnClickListener {
            finish()
        }
    }

    private fun obtenerOperaciones(): String {
        val ops = mutableListOf<String>()

        if (findViewById<CheckBox>(R.id.cbSuma).isChecked) ops.add("SUMA")
        if (findViewById<CheckBox>(R.id.cbResta).isChecked) ops.add("RESTA")
        if (findViewById<CheckBox>(R.id.cbMultiplicacion).isChecked) ops.add("MULTIPLICACION")
        if (findViewById<CheckBox>(R.id.cbDivision).isChecked) ops.add("DIVISION")

        return ops.joinToString(",")
    }

    private fun obtenerTiposPreguntas(): String {
        val tipos = mutableListOf<String>()

        if (findViewById<CheckBox>(R.id.cbOperaciones).isChecked) tipos.add("OPERACIONES")
        if (findViewById<CheckBox>(R.id.cbProblemas).isChecked) tipos.add("PROBLEMAS")

        return tipos.joinToString(",")
    }

    private fun configurarBotonJugar() {
        val btnJugar = findViewById<Button>(R.id.btnJugar)

        btnJugar.setOnClickListener {
            val operaciones = obtenerOperaciones()
            val tiposPreguntas = obtenerTiposPreguntas()

            if (operaciones.isEmpty()) {
                Toast.makeText(this, "Selecciona al menos una operación", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (tiposPreguntas.isEmpty()) {
                Toast.makeText(this, "Selecciona al menos un tipo de pregunta", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intentJuego = Intent(this, JuegoActivity::class.java)

            intentJuego.putExtra("operaciones", operaciones)
            intentJuego.putExtra("dificultad", dificultadSeleccionada)
            intentJuego.putExtra("tiempo", tiempoSeleccionado)
            intentJuego.putExtra("cantidad", findViewById<SeekBar>(R.id.seekBarCantidad).progress + 1)
            intentJuego.putExtra("tiposPreguntas", tiposPreguntas)
            intentJuego.putExtra("tipoJuego", "Personalizado")

            // Mantiene los datos del perfil seleccionado
            intentJuego.putExtra("idPerfil", idPerfil)
            intentJuego.putExtra("nombrePerfil", nombrePerfil)
            intentJuego.putExtra("iconoPerfil", iconoPerfil)

            startActivity(intentJuego)
        }
    }
}