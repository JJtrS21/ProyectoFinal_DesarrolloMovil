package com.fei.proyectofinal

import android.app.DatePickerDialog
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
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowCompat
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import androidx.appcompat.app.AlertDialog

class Padres : AppCompatActivity() {

    private lateinit var contenedorPerfiles: LinearLayout
    private lateinit var edtNombrePerfil: EditText
    private lateinit var imgAvatarFormulario: ImageView
    private lateinit var txtTituloFormulario: TextView

    private lateinit var btnGuardarPerfil: MaterialButton
    private lateinit var btnCancelarFormulario: MaterialButton
    private lateinit var btnEliminarPerfil: MaterialButton

    private lateinit var btnSubirPerfiles: MaterialButton
    private lateinit var btnBajarPerfiles: MaterialButton

    private lateinit var panelHistorial: LinearLayout
    private lateinit var txtTituloHistorial: TextView
    private lateinit var txtFechaSeleccionada: TextView
    private lateinit var tablaHistorial: TableLayout

    private var perfilHistorialActual = ""
    private var fechaHistorialActual = ""

    private var modoEditar = false
    private var perfilEditando = -1

    private var indiceInicioPerfiles = 0
    private val maxVisibles = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        ocultarBarras()

        setContentView(R.layout.activity_padres)

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
        txtFechaSeleccionada = findViewById(R.id.txtFechaSeleccionada)
        tablaHistorial = findViewById(R.id.tablaHistorial)

        val btnRegresar = findViewById<MaterialButton>(R.id.btnRegresar)

        btnRegresar.setOnClickListener {
            finish()
        }

        btnGuardarPerfil.setOnClickListener {
            guardarPerfil()
        }

        btnCancelarFormulario.setOnClickListener {
            if (modoEditar) {
                prepararNuevoPerfil()
            }
        }

        btnEliminarPerfil.setOnClickListener {
            confirmarEliminacionPerfil()
        }

        btnSubirPerfiles.setOnClickListener {
            if (indiceInicioPerfiles > 0) {
                indiceInicioPerfiles--
                cargarPerfiles()
            }
        }

        btnBajarPerfiles.setOnClickListener {
            val totalElementos = obtenerPerfilesGuardados().size + 1
            val maxInicio = maxOf(0, totalElementos - maxVisibles)

            if (indiceInicioPerfiles < maxInicio) {
                indiceInicioPerfiles++
                cargarPerfiles()
            }
        }

        // Abre un calendario al tocar la fecha del historial
        txtFechaSeleccionada.setOnClickListener {
            mostrarCalendarioFechas()
        }

        prepararNuevoPerfil()
        cargarPerfiles()
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

    private fun mostrarFormulario() {
        panelHistorial.visibility = View.GONE

        txtTituloFormulario.visibility = View.VISIBLE
        findViewById<View>(R.id.lblNombre).visibility = View.VISIBLE
        edtNombrePerfil.visibility = View.VISIBLE
        findViewById<View>(R.id.layoutBotonesFormulario).visibility = View.VISIBLE

        // La foto no se muestra en crear/editar perfil
        findViewById<View>(R.id.lblFoto).visibility = View.GONE
        imgAvatarFormulario.visibility = View.GONE
    }

    private fun ocultarFormulario() {
        txtTituloFormulario.visibility = View.GONE
        findViewById<View>(R.id.lblNombre).visibility = View.GONE
        edtNombrePerfil.visibility = View.GONE
        findViewById<View>(R.id.lblFoto).visibility = View.GONE
        imgAvatarFormulario.visibility = View.GONE
        findViewById<View>(R.id.layoutBotonesFormulario).visibility = View.GONE
    }

    private fun mostrarHistorialPerfil(nombre: String) {
        ocultarFormulario()

        panelHistorial.visibility = View.VISIBLE
        txtTituloHistorial.text = "Historial de juego de $nombre"

        perfilHistorialActual = nombre

        val db = SQLiteHelper(this)
        val fechas = db.obtenerFechasPorPerfil(nombre)

        if (fechas.isEmpty()) {
            fechaHistorialActual = obtenerFechaActual()
            txtFechaSeleccionada.text = "Sin partidas registradas"
            llenarTablaHistorial(mutableListOf())
        } else {
            fechaHistorialActual = fechas[0]
            txtFechaSeleccionada.text = fechaHistorialActual

            val partidas = SQLiteHelper(this)
                .obtenerPartidasPorPerfilYFecha(nombre, fechaHistorialActual)

            llenarTablaHistorial(partidas)
        }
    }

    private fun prepararNuevoPerfil() {
        mostrarFormulario()

        modoEditar = false
        perfilEditando = -1

        txtTituloFormulario.text = "Crear perfil del niño"
        edtNombrePerfil.setText("")

        btnGuardarPerfil.visibility = View.VISIBLE
        btnCancelarFormulario.visibility = View.GONE
        btnEliminarPerfil.visibility = View.GONE

        btnGuardarPerfil.backgroundTintList =
            ColorStateList.valueOf(Color.parseColor("#6A4BB3"))
    }

    private fun prepararEditarPerfil(numeroPerfil: Int, nombre: String) {
        mostrarFormulario()

        modoEditar = true
        perfilEditando = numeroPerfil

        txtTituloFormulario.text = "Editar perfil del niño"
        edtNombrePerfil.setText(nombre)

        btnGuardarPerfil.visibility = View.VISIBLE
        btnCancelarFormulario.visibility = View.VISIBLE
        btnEliminarPerfil.visibility = View.VISIBLE

        btnCancelarFormulario.text = "Cancelar"
        btnCancelarFormulario.backgroundTintList =
            ColorStateList.valueOf(Color.parseColor("#6F687C"))

        btnGuardarPerfil.backgroundTintList =
            ColorStateList.valueOf(Color.parseColor("#6A4BB3"))

        btnEliminarPerfil.backgroundTintList =
            ColorStateList.valueOf(Color.parseColor("#9E2A2A"))
    }

    private fun existeNombreDuplicado(nombreNuevo: String): Boolean {
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
        val nombre = edtNombrePerfil.text.toString().trim()

        if (nombre.isEmpty()) {
            Toast.makeText(this, "Escribe un nombre para el perfil", Toast.LENGTH_SHORT).show()
            return
        }

        if (existeNombreDuplicado(nombre)) {
            Toast.makeText(this, "Ya existe un perfil con ese nombre", Toast.LENGTH_SHORT).show()
            return
        }

        val prefs = getSharedPreferences("perfiles", MODE_PRIVATE)
        val editor = prefs.edit()

        if (modoEditar && perfilEditando != -1) {
            editor.putString("perfil_$perfilEditando", nombre)
            editor.apply()

            Toast.makeText(this, "Perfil actualizado", Toast.LENGTH_SHORT).show()
        } else {
            val cantidadActual = prefs.getInt("cantidad_perfiles", 0)
            val nuevoNumero = cantidadActual + 1

            editor.putString("perfil_$nuevoNumero", nombre)
            editor.putInt("cantidad_perfiles", nuevoNumero)
            editor.apply()

            val totalElementos = nuevoNumero + 1
            indiceInicioPerfiles = maxOf(0, totalElementos - maxVisibles)

            Toast.makeText(this, "Perfil creado", Toast.LENGTH_SHORT).show()
        }

        cargarPerfiles()
        prepararNuevoPerfil()
    }

    private fun confirmarEliminacionPerfil() {
        // Verifica que realmente se esté editando un perfil
        if (!modoEditar || perfilEditando == -1) {
            Toast.makeText(this, "Selecciona un perfil para eliminar", Toast.LENGTH_SHORT).show()
            return
        }

        // Obtiene el nombre actual escrito en el campo
        val nombrePerfil = edtNombrePerfil.text.toString().trim()

        // Muestra un aviso antes de eliminar el perfil
        AlertDialog.Builder(this)
            .setTitle("Eliminar perfil")
            .setMessage(
                "¿Estás seguro de eliminar el perfil \"$nombrePerfil\"?\n\n" +
                        "Esta acción es permanente y no se podrá deshacer."
            )
            .setPositiveButton("Eliminar") { dialog, _ ->
                eliminarPerfil()
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun eliminarPerfil() {
        if (!modoEditar || perfilEditando == -1) {
            Toast.makeText(this, "Selecciona un perfil para eliminar", Toast.LENGTH_SHORT).show()
            return
        }

        val prefs = getSharedPreferences("perfiles", MODE_PRIVATE)
        val cantidad = prefs.getInt("cantidad_perfiles", 0)
        val editor = prefs.edit()

        for (i in perfilEditando until cantidad) {
            val siguienteNombre = prefs.getString("perfil_${i + 1}", null)
            editor.putString("perfil_$i", siguienteNombre)
        }

        editor.remove("perfil_$cantidad")
        editor.putInt("cantidad_perfiles", cantidad - 1)
        editor.apply()

        Toast.makeText(this, "Perfil eliminado", Toast.LENGTH_SHORT).show()

        cargarPerfiles()
        prepararNuevoPerfil()
    }

    private fun obtenerPerfilesGuardados(): MutableList<Pair<Int, String>> {
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
        contenedorPerfiles.removeAllViews()

        val perfiles = obtenerPerfilesGuardados()

        val totalElementos = perfiles.size + 1
        val maxInicio = maxOf(0, totalElementos - maxVisibles)

        if (indiceInicioPerfiles > maxInicio) {
            indiceInicioPerfiles = maxInicio
        }

        val ultimo = minOf(indiceInicioPerfiles + maxVisibles, totalElementos)

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

        botonPerfil.setOnClickListener {
            mostrarHistorialPerfil(nombre)
        }

        botonEditar.setOnClickListener {
            prepararEditarPerfil(numeroPerfil, nombre)
        }

        fila.addView(botonPerfil)
        fila.addView(botonEditar)

        return fila
    }

    private fun crearFilaAgregar(): LinearLayout {
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
        btnSubirPerfiles.visibility = View.VISIBLE
        btnBajarPerfiles.visibility = View.VISIBLE

        val puedeSubir = indiceInicioPerfiles > 0
        val puedeBajar = indiceInicioPerfiles < (totalElementos - maxVisibles)

        btnSubirPerfiles.isEnabled = puedeSubir
        btnBajarPerfiles.isEnabled = puedeBajar

        btnSubirPerfiles.alpha = if (puedeSubir) 1f else 0.55f
        btnBajarPerfiles.alpha = if (puedeBajar) 1f else 0.55f
    }

    private fun llenarTablaHistorial(partidas: MutableList<Array<String>>) {
        tablaHistorial.removeAllViews()

        agregarFilaTabla(
            arrayOf("Hora", "Tipo", "Operaciones", "Dificultad", "Tiempo", "Aciertos"),
            esEncabezado = true
        )

        if (partidas.isEmpty()) {
            agregarFilaTabla(
                arrayOf("Sin datos", "-", "-", "-", "-", "-"),
                esEncabezado = false
            )
            return
        }

        for (partida in partidas) {
            agregarFilaTabla(partida, esEncabezado = false)
        }
    }

    private fun agregarFilaTabla(datos: Array<String>, esEncabezado: Boolean) {
        val fila = TableRow(this).apply {
            layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
            )

            setBackgroundColor(
                if (esEncabezado) {
                    Color.parseColor("#CFCFCF")
                } else {
                    Color.parseColor("#55FFFFFF")
                }
            )
        }

        val pesos = floatArrayOf(
            0.75f, // Hora
            1.55f, // Tipo
            1.35f, // Operaciones
            1.15f, // Dificultad
            1.15f, // Tiempo
            1.0f   // Aciertos
        )

        for (i in datos.indices) {
            val texto = TextView(this).apply {
                text = datos[i]
                textSize = if (esEncabezado) 10f else 9.6f
                setTextColor(Color.BLACK)
                setPadding(dp(3), dp(4), dp(3), dp(4))
                gravity = Gravity.CENTER
                maxLines = 2

                if (esEncabezado) {
                    typeface = ResourcesCompat.getFont(this@Padres, R.font.fredoka_bold)
                }

                layoutParams = TableRow.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    pesos.getOrElse(i) { 1f }
                )
            }

            fila.addView(texto)
        }

        tablaHistorial.addView(fila)
    }

    private fun mostrarCalendarioFechas() {
        // Verifica que primero se haya seleccionado un perfil
        if (perfilHistorialActual.isEmpty()) {
            Toast.makeText(this, "Selecciona un perfil primero", Toast.LENGTH_SHORT).show()
            return
        }

        val calendario = Calendar.getInstance()

        val anio = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)

        // Abre el calendario para que el usuario elija una fecha
        val datePicker = DatePickerDialog(
            this,
            { _, anioSeleccionado, mesSeleccionado, diaSeleccionado ->

                // Convierte la fecha elegida al formato usado en SQLite: yyyy-MM-dd
                val fechaSeleccionada = String.format(
                    Locale.getDefault(),
                    "%04d-%02d-%02d",
                    anioSeleccionado,
                    mesSeleccionado + 1,
                    diaSeleccionado
                )

                fechaHistorialActual = fechaSeleccionada
                txtFechaSeleccionada.text = fechaSeleccionada

                // Busca las partidas del perfil en la fecha seleccionada
                val partidas = SQLiteHelper(this)
                    .obtenerPartidasPorPerfilYFecha(perfilHistorialActual, fechaHistorialActual)

                llenarTablaHistorial(partidas)
            },
            anio,
            mes,
            dia
        )

        datePicker.show()
    }

    private fun obtenerFechaActual(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    private fun obtenerPrimeraLetra(nombre: String): Char {
        val primera = nombre.trim().lowercase().firstOrNull() ?: return '#'

        return if (primera in 'a'..'z' || primera == 'ñ') {
            primera
        } else {
            '#'
        }
    }

    private fun obtenerIconoPerfil(letra: Char): Int {
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
        return (valor * resources.displayMetrics.density).toInt()
    }
}