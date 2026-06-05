package com.fei.proyectofinal

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.google.android.material.button.MaterialButton

class Modos : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fuerza la pantalla en modo horizontal
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        // Oculta las barras del sistema
        ocultarBarras()

        // Carga el diseño de la pantalla de modos
        setContentView(R.layout.activity_modos)

        // Recibe los datos del perfil seleccionado desde la pantalla Perfiles
        val idPerfil = intent.getIntExtra("idPerfil", -1)
        val nombrePerfil = intent.getStringExtra("nombrePerfil") ?: "Perfil"
        val iconoPerfil = intent.getIntExtra("iconoPerfil", R.drawable.que_rayos)

        // Referencias a los elementos del XML
        val txtNombrePerfil = findViewById<TextView>(R.id.txtNombrePerfil)
        val imgPerfilSeleccionado = findViewById<ImageView>(R.id.imgPerfilSeleccionado)
        val btnRegresar = findViewById<MaterialButton>(R.id.btnRegresar)
        val btnNiveles = findViewById<MaterialButton>(R.id.btnNiveles)
        val btnPersonalizado = findViewById<MaterialButton>(R.id.btnPersonalizado)

        // Muestra el nombre y la imagen del perfil seleccionado
        txtNombrePerfil.text = nombrePerfil
        imgPerfilSeleccionado.setImageResource(iconoPerfil)

        // Regresa a la pantalla anterior
        btnRegresar.setOnClickListener {
            finish()
        }

        // Abre la pantalla de niveles y conserva los datos del perfil
        btnNiveles.setOnClickListener {
            val intent = Intent(this, NivelesActivity::class.java)
            intent.putExtra("idPerfil", idPerfil)
            intent.putExtra("nombrePerfil", nombrePerfil)
            intent.putExtra("iconoPerfil", iconoPerfil)
            startActivity(intent)
        }

        // Abre el formulario personalizado y conserva los datos del perfil
        btnPersonalizado.setOnClickListener {
            val intent = Intent(this, FormularioActivity::class.java)
            intent.putExtra("idPerfil", idPerfil)
            intent.putExtra("nombrePerfil", nombrePerfil)
            intent.putExtra("iconoPerfil", iconoPerfil)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        // Mantiene la pantalla completa al volver a la actividad
        ocultarBarras()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        // Vuelve a ocultar las barras si la pantalla recupera el foco
        if (hasFocus) {
            ocultarBarras()
        }
    }

    private fun ocultarBarras() {
        // Oculta la barra superior de la app si existe
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
}