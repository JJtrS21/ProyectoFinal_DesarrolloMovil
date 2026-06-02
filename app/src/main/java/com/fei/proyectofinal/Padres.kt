package com.fei.proyectofinal

import android.content.pm.ActivityInfo
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowCompat
import com.google.android.material.button.MaterialButton

class Padres : AppCompatActivity() {

    // Elementos principales de la pantalla
    private lateinit var contenedorPerfiles: LinearLayout
    private lateinit var edtNombrePerfil: EditText
    private lateinit var imgAvatarFormulario: ImageView
    private lateinit var txtTituloFormulario: TextView

    // Botones del formulario
    private lateinit var btnGuardarPerfil: MaterialButton
    private lateinit var btnCancelarFormulario: MaterialButton
    private lateinit var btnEliminarPerfil: MaterialButton

    // Botones para mover la lista de perfiles
    private lateinit var btnSubirPerfiles: MaterialButton
    private lateinit var btnBajarPerfiles: MaterialButton

    // Panel de historial del perfil
    private lateinit var panelHistorial: LinearLayout
    private lateinit var txtTituloHistorial: TextView

    // Variables para controlar si se está creando o editando un perfil
    private var modoEditar = false
    private var perfilEditando = -1

    // Control de los perfiles visibles con flechas
    private var indiceInicioPerfiles = 0
    private val maxVisibles = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fuerza la pantalla en modo horizontal
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        // Oculta barras del sistema
        ocultarBarras()

        // Carga el diseño XML
        setContentView(R.layout.activity_padres)

        // Relaciona variables con elementos del XML
        contenedorPerfiles = findViewById(R.id.contenedorPerfiles)
        edtNombrePerfil = findViewById(R.id.edtNombrePerfil)
        imgAvatarFormulario = findViewById(R.id.imgAvatarFormulario)
        txtTituloFormulario = findViewById(R.id.txtTituloFormulario)
        btnGuardarPerfil = findViewById(R.id.btnGuardarPerfil)
        btnCancelarFormulario = findViewById(R.id.btnCancelarFormulario)
        btnEliminarPerfil = findViewById(R.id.btnEliminarPerfil)
        btnSubirPerfiles = findViewById(R.id.btnSubirPerfiles)
        btnBajarPerfiles = findViewById(R.id.btnBajarPerfiles)
        panelHistorial = findViewById(R.id.panelHistorial)
        txtTituloHistorial = findViewById(R.id.txtTituloHistorial)

        val btnRegresar = findViewById<MaterialButton>(R.id.btnRegresar)

        // Regresa a la pantalla anterior
        btnRegresar.setOnClickListener {
            finish()
        }

        // Guarda un perfil nuevo o editado
        btnGuardarPerfil.setOnClickListener {
            guardarPerfil()
        }

        // Limpia el formulario para crear un nuevo perfil
        btnCancelarFormulario.setOnClickListener {
            prepararNuevoPerfil()
        }

        // Elimina el perfil seleccionado
        btnEliminarPerfil.setOnClickListener {
            eliminarPerfil()
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
            val totalElementos = obtenerPerfilesGuardados().size + 1
            val maxInicio = maxOf(0, totalElementos - maxVisibles)

            if (indiceInicioPerfiles < maxInicio) {
                indiceInicioPerfiles++
                cargarPerfiles()
            }
        }

        // Estado inicial de la pantalla
        prepararNuevoPerfil()
        cargarPerfiles()
    }

    override fun onResume() {
        super.onResume()

        // Mantiene la pantalla completa al volver a la actividad
        ocultarBarras()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        // Vuelve a ocultar barras cuando la pantalla recupera el foco
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

            // Oculta barra de estado y navegación
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

    private fun mostrarFormulario() {
        // Oculta historial y muestra formulario
        panelHistorial.visibility = View.GONE

        txtTituloFormulario.visibility = View.VISIBLE
        findViewById<View>(R.id.lblNombre).visibility = View.VISIBLE
        edtNombrePerfil.visibility = View.VISIBLE
        findViewById<View>(R.id.lblFoto).visibility = View.VISIBLE
        imgAvatarFormulario.visibility = View.VISIBLE
        findViewById<View>(R.id.layoutBotonesFormulario).visibility = View.VISIBLE
    }

    private fun ocultarFormulario() {
        // Oculta los elementos del formulario
        txtTituloFormulario.visibility = View.GONE
        findViewById<View>(R.id.lblNombre).visibility = View.GONE
        edtNombrePerfil.visibility = View.GONE
        findViewById<View>(R.id.lblFoto).visibility = View.GONE
        imgAvatarFormulario.visibility = View.GONE
        findViewById<View>(R.id.layoutBotonesFormulario).visibility = View.GONE
    }

    private fun mostrarHistorialPerfil(nombre: String) {
        // Muestra el historial visual del perfil seleccionado
        ocultarFormulario()

        panelHistorial.visibility = View.VISIBLE
        txtTituloHistorial.text = "Historial de juego de $nombre"
    }

    private fun prepararNuevoPerfil() {
        // Prepara el formulario para crear un perfil nuevo
        mostrarFormulario()

        modoEditar = false
        perfilEditando = -1

        txtTituloFormulario.text = "Crear Perfil"
        edtNombrePerfil.setText("")
        imgAvatarFormulario.setImageResource(R.drawable.perfil_a)
        btnEliminarPerfil.visibility = View.GONE
    }

    private fun prepararEditarPerfil(numeroPerfil: Int, nombre: String) {
        // Prepara el formulario para editar un perfil existente
        mostrarFormulario()

        modoEditar = true
        perfilEditando = numeroPerfil

        txtTituloFormulario.text = "Editar Perfil"
        edtNombrePerfil.setText(nombre)
        imgAvatarFormulario.setImageResource(obtenerIconoPerfil(obtenerPrimeraLetra(nombre)))
        btnEliminarPerfil.visibility = View.VISIBLE
    }

    private fun existeNombreDuplicado(nombreNuevo: String): Boolean {
        // Verifica que no exista otro perfil con el mismo nombre
        val prefs = getSharedPreferences("perfiles", MODE_PRIVATE)
        val cantidad = prefs.getInt("cantidad_perfiles", 0)

        for (i in 1..cantidad) {
            val nombreGuardado = prefs.getString("perfil_$i", "") ?: ""

            val esElMismoPerfilQueEstoyEditando = modoEditar && i == perfilEditando

            if (!esElMismoPerfilQueEstoyEditando &&
                nombreGuardado.trim().equals(nombreNuevo.trim(), ignoreCase = true)
            ) {
                return true
            }
        }

        return false
    }

    private fun guardarPerfil() {
        // Obtiene el nombre escrito por el usuario
        val nombre = edtNombrePerfil.text.toString().trim()

        // Valida que el nombre no esté vacío
        if (nombre.isEmpty()) {
            Toast.makeText(this, "Escribe un nombre para el perfil", Toast.LENGTH_SHORT).show()
            return
        }

        // Valida que el nombre no esté duplicado
        if (existeNombreDuplicado(nombre)) {
            Toast.makeText(this, "Ya existe un perfil con ese nombre", Toast.LENGTH_SHORT).show()
            return
        }

        // SharedPreferences guarda los perfiles de forma local
        val prefs = getSharedPreferences("perfiles", MODE_PRIVATE)
        val editor = prefs.edit()

        if (modoEditar && perfilEditando != -1) {
            // Actualiza un perfil existente
            editor.putString("perfil_$perfilEditando", nombre)
            editor.apply()

            Toast.makeText(this, "Perfil actualizado", Toast.LENGTH_SHORT).show()
        } else {
            // Crea un nuevo perfil
            val cantidadActual = prefs.getInt("cantidad_perfiles", 0)
            val nuevoNumero = cantidadActual + 1

            editor.putString("perfil_$nuevoNumero", nombre)
            editor.putInt("cantidad_perfiles", nuevoNumero)
            editor.apply()

            // Mueve la lista para mostrar el nuevo perfil y el botón +
            val totalElementos = nuevoNumero + 1
            indiceInicioPerfiles = maxOf(0, totalElementos - maxVisibles)

            Toast.makeText(this, "Perfil creado", Toast.LENGTH_SHORT).show()
        }

        // Actualiza la lista visual
        cargarPerfiles()
        prepararNuevoPerfil()
    }

    private fun eliminarPerfil() {
        // Verifica que haya un perfil seleccionado para editar
        if (!modoEditar || perfilEditando == -1) {
            Toast.makeText(this, "Selecciona un perfil para eliminar", Toast.LENGTH_SHORT).show()
            return
        }

        val prefs = getSharedPreferences("perfiles", MODE_PRIVATE)
        val cantidad = prefs.getInt("cantidad_perfiles", 0)
        val editor = prefs.edit()

        // Reacomoda los perfiles para no dejar espacios vacíos
        for (i in perfilEditando until cantidad) {
            val siguienteNombre = prefs.getString("perfil_${i + 1}", null)
            editor.putString("perfil_$i", siguienteNombre)
        }

        // Elimina el último registro duplicado
        editor.remove("perfil_$cantidad")
        editor.putInt("cantidad_perfiles", cantidad - 1)
        editor.apply()

        Toast.makeText(this, "Perfil eliminado", Toast.LENGTH_SHORT).show()

        // Actualiza la lista y limpia el formulario
        cargarPerfiles()
        prepararNuevoPerfil()
    }

    private fun obtenerPerfilesGuardados(): MutableList<Pair<Int, String>> {
        // Recupera los perfiles guardados en SharedPreferences
        val prefs = getSharedPreferences("perfiles", MODE_PRIVATE)
        val cantidad = prefs.getInt("cantidad_perfiles", 0)
        val lista = mutableListOf<Pair<Int, String>>()

        for (i in 1..cantidad) {
            val nombre = prefs.getString("perfil_$i", "Perfil $i") ?: "Perfil $i"
            lista.add(i to nombre)
        }

        return lista
    }

    private fun cargarPerfiles() {
        // Limpia la lista visual antes de volver a dibujarla
        contenedorPerfiles.removeAllViews()

        val perfiles = obtenerPerfilesGuardados()

        // Se suma 1 porque el botón + también cuenta como elemento
        val totalElementos = perfiles.size + 1
        val maxInicio = maxOf(0, totalElementos - maxVisibles)

        if (indiceInicioPerfiles > maxInicio) {
            indiceInicioPerfiles = maxInicio
        }

        val ultimo = minOf(indiceInicioPerfiles + maxVisibles, totalElementos)

        // Dibuja perfiles visibles o el botón +
        for (posicion in indiceInicioPerfiles until ultimo) {
            if (posicion < perfiles.size) {
                val (numeroPerfil, nombre) = perfiles[posicion]
                contenedorPerfiles.addView(crearFilaPerfil(numeroPerfil, nombre))
            } else {
                contenedorPerfiles.addView(crearFilaAgregar())
            }
        }

        actualizarEstadoFlechas(totalElementos)
    }

    private fun crearFilaPerfil(numeroPerfil: Int, nombre: String): LinearLayout {
        // Crea una fila con botón de perfil y botón de editar
        val fila = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL

            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = dp(6)
            }
        }

        // Botón principal del perfil
        val botonPerfil = MaterialButton(this).apply {
            text = nombre
            textSize = 15f
            typeface = ResourcesCompat.getFont(this@Padres, R.font.fredoka_bold)
            setTextColor(Color.WHITE)
            isAllCaps = false

            backgroundTintList = ColorStateList.valueOf(Color.parseColor("#6A4BB3"))
            strokeColor = ColorStateList.valueOf(Color.WHITE)
            strokeWidth = dp(3)
            cornerRadius = dp(16)

            minWidth = 0
            minHeight = 0
            minimumWidth = 0
            minimumHeight = 0

            setPadding(dp(8), 0, dp(8), 0)

            iconPadding = dp(8)
            iconSize = dp(36)
            setIconResource(obtenerIconoPerfil(obtenerPrimeraLetra(nombre)))
            iconTint = null

            layoutParams = LinearLayout.LayoutParams(
                dp(190),
                dp(48)
            )
        }

        // Botón de engrane para editar el perfil
        val botonEditar = MaterialButton(this).apply {
            text = ""
            contentDescription = "Editar perfil"

            backgroundTintList = ColorStateList.valueOf(Color.parseColor("#6A4BB3"))
            strokeColor = ColorStateList.valueOf(Color.WHITE)
            strokeWidth = dp(3)
            cornerRadius = dp(24)

            minWidth = 0
            minHeight = 0
            minimumWidth = 0
            minimumHeight = 0

            iconPadding = 0
            iconSize = dp(30)
            iconGravity = MaterialButton.ICON_GRAVITY_TEXT_TOP
            setIconResource(R.drawable.baseline_settings_24)
            setIconTintResource(android.R.color.white)

            gravity = Gravity.CENTER
            textAlignment = View.TEXT_ALIGNMENT_CENTER
            setPadding(0, 0, 0, 0)

            layoutParams = LinearLayout.LayoutParams(
                dp(48),
                dp(48)
            ).apply {
                leftMargin = dp(8)
            }
        }

        // Al tocar el perfil se muestra su historial
        botonPerfil.setOnClickListener {
            mostrarHistorialPerfil(nombre)
        }

        // Al tocar el engrane se edita el perfil
        botonEditar.setOnClickListener {
            prepararEditarPerfil(numeroPerfil, nombre)
        }

        fila.addView(botonPerfil)
        fila.addView(botonEditar)

        return fila
    }

    private fun crearFilaAgregar(): LinearLayout {
        // Crea la fila del botón para agregar perfil
        val fila = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER

            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = dp(6)
            }
        }

        // Botón +
        val botonAgregar = MaterialButton(this).apply {
            text = "+"
            textSize = 26f
            typeface = ResourcesCompat.getFont(this@Padres, R.font.fredoka_bold)
            setTextColor(Color.WHITE)
            isAllCaps = false
            gravity = Gravity.CENTER
            textAlignment = View.TEXT_ALIGNMENT_CENTER
            includeFontPadding = false

            backgroundTintList = ColorStateList.valueOf(Color.parseColor("#6A4BB3"))
            strokeColor = ColorStateList.valueOf(Color.WHITE)
            strokeWidth = dp(3)
            cornerRadius = dp(16)

            minWidth = 0
            minHeight = 0
            minimumWidth = 0
            minimumHeight = 0

            setPadding(0, 0, 0, dp(3))

            layoutParams = LinearLayout.LayoutParams(
                dp(48),
                dp(48)
            )
        }

        botonAgregar.setOnClickListener {
            prepararNuevoPerfil()
        }

        fila.addView(botonAgregar)

        return fila
    }

    private fun actualizarEstadoFlechas(totalElementos: Int) {
        // Mantiene visibles las flechas y solo las desactiva cuando no se pueden usar
        btnSubirPerfiles.visibility = View.VISIBLE
        btnBajarPerfiles.visibility = View.VISIBLE

        val puedeSubir = indiceInicioPerfiles > 0
        val puedeBajar = indiceInicioPerfiles < (totalElementos - maxVisibles)

        btnSubirPerfiles.isEnabled = puedeSubir
        btnBajarPerfiles.isEnabled = puedeBajar

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
        // Regresa el icono correspondiente a la primera letra del perfil
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