package com.fei.proyectofinal

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.material.button.MaterialButton

class Inicio : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fuerza la pantalla en modo horizontal
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        // Carga el diseño de la pantalla de inicio
        setContentView(R.layout.activity_inicio)

        // Oculta barras del sistema para pantalla completa
        ocultarBarras()

        // Botón para entrar a la selección de perfiles
        val btnJugar = findViewById<MaterialButton>(R.id.btnJugar)

        btnJugar.setOnClickListener {
            val intent = Intent(this, Perfiles::class.java)
            startActivity(intent)
        }

        // Botón para entrar al menú de padres
        val btnPadres = findViewById<MaterialButton>(R.id.btnPadres)

        btnPadres.setOnClickListener {
            val intent = Intent(this, Padres::class.java)
            startActivity(intent)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        // Vuelve a ocultar las barras si la pantalla recupera el foco
        if (hasFocus) {
            ocultarBarras()
        }
    }

    private fun ocultarBarras() {
        // Permite que el contenido use toda la pantalla
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val controller = WindowInsetsControllerCompat(window, window.decorView)

        // Oculta barra superior y barra de navegación
        controller.hide(WindowInsetsCompat.Type.statusBars())
        controller.hide(WindowInsetsCompat.Type.navigationBars())

        // Permite mostrar las barras temporalmente al deslizar
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}