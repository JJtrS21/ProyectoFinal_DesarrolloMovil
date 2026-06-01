package com.fei.proyectofinal

import android.content.pm.ActivityInfo
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowCompat
import com.google.android.material.button.MaterialButton
import kotlin.jvm.java

class Perfiles : AppCompatActivity() {

    // Contenedor donde se agregan los botones de perfiles
    private lateinit var contenedorPerfiles: LinearLayout

    // Flechas para mover la lista de perfiles
    private lateinit var btnSubirPerfiles: MaterialButton
    private lateinit var btnBajarPerfiles: MaterialButton

    // Controla desde qué perfil empieza la lista visible
    private var indiceInicioPerfiles = 0

    // Cantidad máxima de perfiles visibles en pantalla
    private val maxVisibles = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fuerza la pantalla en modo horizontal
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        // Oculta barras del sistema
        ocultarBarras()

        // Carga el diseño XML de perfiles
        setContentView(R.layout.activity_perfiles)

        // Relaciona variables con elementos del XML
        contenedorPerfiles = findViewById(R.id.contenedorPerfiles)
        btnSubirPerfiles = findViewById(R.id.btnSubirPerfiles)
        btnBajarPerfiles = findViewById(R.id.btnBajarPerfiles)

        val btnRegresar = findViewById<MaterialButton>(R.id.btnRegresar)

        // Regresa a la pantalla anterior
        btnRegresar.setOnClickListener {
            finish()
        }

        // Mueve la lista de perfiles hacia arriba
        btnSubirPerfiles.setOnClickListener {
            if (indiceInicioPerfiles > 0) {
                indiceInicioPerfiles--
                cargarPerfiles()
            }
        }

        // Mueve la lista de perfiles hacia abajo
        btnBajarPerfiles.setOnClickListener {
            val totalPerfiles = obtenerPerfilesGuardados().size
            val maxInicio = maxOf(0, totalPerfiles - maxVisibles)

            if (indiceInicioPerfiles < maxInicio) {
                indiceInicioPerfiles++
                cargarPerfiles()
            }
        }

        // Carga los perfiles guardados al abrir la pantalla
        cargarPerfiles()
    }

    override fun onResume() {
        super.onResume()

        // Mantiene pantalla completa y actualiza perfiles al volver
        ocultarBarras()
        cargarPerfiles()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        // Vuelve a ocultar barras si la pantalla recupera el foco
        if (hasFocus) {
            ocultarBarras()
        }
    }

    private fun ocultarBarras() {
        // Oculta la barra superior de la app
        supportActionBar?.hide()

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            // Permite que el contenido use toda la pantalla
            WindowCompat.setDecorFitsSystemWindows(window, false)

            // Oculta barra de estado y barra de navegación
            window.insetsController?.hide(
                WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars()
            )

            // Permite mostrar las barras temporalmente al deslizar
            window.insetsController?.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } else {
            // Compatibilidad para versiones antiguas de Android
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

    private fun obtenerPerfilesGuardados(): MutableList<Pair<Int, String>> {
        // Recupera los perfiles guardados en SharedPreferences
        val prefs = getSharedPreferences("perfiles", MODE_PRIVATE)
        val cantidad = prefs.getInt("cantidad_perfiles", 0)

        val lista = mutableListOf<Pair<Int, String>>()

        // Recorre los perfiles guardados y los agrega a una lista
        for (i in 1..cantidad) {
            val nombre = prefs.getString("perfil_$i", "Perfil $i") ?: "Perfil $i"
            lista.add(i to nombre)
        }

        return lista
    }

    private fun cargarPerfiles() {
        // Limpia el contenedor antes de dibujar los perfiles
        contenedorPerfiles.removeAllViews()

        val perfiles = obtenerPerfilesGuardados()

        // Si no hay perfiles, muestra un botón informativo
        if (perfiles.isEmpty()) {
            contenedorPerfiles.addView(crearBotonVacio())
            actualizarEstadoFlechas(0)
            return
        }

        val totalPerfiles = perfiles.size
        val maxInicio = maxOf(0, totalPerfiles - maxVisibles)

        // Evita que el índice se salga del rango permitido
        if (indiceInicioPerfiles > maxInicio) {
            indiceInicioPerfiles = maxInicio
        }

        val ultimo = minOf(indiceInicioPerfiles + maxVisibles, totalPerfiles)

        // Dibuja solo los perfiles visibles según las flechas
        for (posicion in indiceInicioPerfiles until ultimo) {
            val (numeroPerfil, nombre) = perfiles[posicion]
            contenedorPerfiles.addView(crearBotonPerfil(numeroPerfil, nombre))
        }

        actualizarEstadoFlechas(totalPerfiles)
    }

    private fun crearBotonPerfil(numeroPerfil: Int, nombre: String): MaterialButton {
        // Crea visualmente un botón para cada perfil guardado
        return MaterialButton(this).apply {
            text = nombre
            textSize = 15f
            typeface = ResourcesCompat.getFont(this@Perfiles, R.font.fredoka_bold)
            setTextColor(Color.WHITE)
            isAllCaps = false

            backgroundTintList = ColorStateList.valueOf(Color.parseColor("#6A4BB3"))
            strokeColor = ColorStateList.valueOf(Color.WHITE)
            strokeWidth = dp(3)
            cornerRadius = dp(14)

            minWidth = 0
            minHeight = 0
            minimumWidth = 0
            minimumHeight = 0

            setPadding(dp(8), 0, dp(8), 0)

            // Asigna icono según la primera letra del nombre
            iconPadding = dp(8)
            iconSize = dp(40)
            setIconResource(obtenerIconoPerfil(obtenerPrimeraLetra(nombre)))
            iconTint = null

            layoutParams = LinearLayout.LayoutParams(
                dp(210),
                dp(58)
            ).apply {
                bottomMargin = dp(10)
            }

            // Al seleccionar un perfil, abre la pantalla de modos
            setOnClickListener {
                val intent = android.content.Intent(this@Perfiles, Modos::class.java)
                intent.putExtra("idPerfil", numeroPerfil)
                intent.putExtra("nombrePerfil", nombre)
                intent.putExtra("iconoPerfil", obtenerIconoPerfil(obtenerPrimeraLetra(nombre)))
                startActivity(intent)
            }
        }
    }

    private fun crearBotonVacio(): MaterialButton {
        // Botón mostrado cuando todavía no existen perfiles
        return MaterialButton(this).apply {
            text = "Sin perfiles"
            textSize = 15f
            typeface = ResourcesCompat.getFont(this@Perfiles, R.font.fredoka_bold)
            setTextColor(Color.WHITE)
            isAllCaps = false

            backgroundTintList = ColorStateList.valueOf(Color.parseColor("#6A4BB3"))
            strokeColor = ColorStateList.valueOf(Color.WHITE)
            strokeWidth = dp(3)
            cornerRadius = dp(14)

            minWidth = 0
            minHeight = 0
            minimumWidth = 0
            minimumHeight = 0

            layoutParams = LinearLayout.LayoutParams(
                dp(210),
                dp(54)
            )
        }
    }

    private fun actualizarEstadoFlechas(totalPerfiles: Int) {
        // Mantiene visibles las flechas
        btnSubirPerfiles.visibility = View.VISIBLE
        btnBajarPerfiles.visibility = View.VISIBLE

        // Verifica si se puede mover hacia arriba o hacia abajo
        val puedeSubir = indiceInicioPerfiles > 0
        val puedeBajar = indiceInicioPerfiles < (totalPerfiles - maxVisibles)

        btnSubirPerfiles.isEnabled = puedeSubir
        btnBajarPerfiles.isEnabled = puedeBajar

        // Baja la opacidad si la flecha no se puede usar
        btnSubirPerfiles.alpha = if (puedeSubir) 1f else 0.55f
        btnBajarPerfiles.alpha = if (puedeBajar) 1f else 0.55f
    }

    private fun obtenerPrimeraLetra(nombre: String): Char {
        // Obtiene la primera letra válida del nombre
        val primera = nombre.trim().lowercase().firstOrNull() ?: return '#'

        return if (primera in 'a'..'z' || primera == 'ñ') {
            primera
        } else {
            '#'
        }
    }

    private fun obtenerIconoPerfil(letra: Char): Int {
        // Regresa el icono correspondiente a la primera letra
        return when (letra) {
            'a' -> R.drawable.perfil_a
            'b' -> R.drawable.perfil_b
            'c' -> R.drawable.perfil_c
            'd' -> R.drawable.perfil_d
            'e' -> R.drawable.perfil_e
            'f' -> R.drawable.perfil_f
            'g' -> R.drawable.perfil_g
            'h' -> R.drawable.perfil_h
            'i' -> R.drawable.perfil_i
            'j' -> R.drawable.perfil_j
            'k' -> R.drawable.perfil_k
            'l' -> R.drawable.perfil_l
            'm' -> R.drawable.perfil_m
            'n' -> R.drawable.perfil_n
            'ñ' -> R.drawable.perfil_enie
            'o' -> R.drawable.perfil_o
            'p' -> R.drawable.perfil_p
            'q' -> R.drawable.perfil_q
            'r' -> R.drawable.perfil_r
            's' -> R.drawable.perfil_s
            't' -> R.drawable.perfil_t
            'u' -> R.drawable.perfil_u
            'v' -> R.drawable.perfil_v
            'w' -> R.drawable.perfil_w
            'x' -> R.drawable.perfil_x
            'y' -> R.drawable.perfil_y
            'z' -> R.drawable.perfil_z
            else -> R.drawable.que_rayos
        }
    }

    private fun dp(valor: Int): Int {
        // Convierte valores dp a pixeles según la densidad del dispositivo
        return (valor * resources.displayMetrics.density).toInt()
    }
}