package com.fei.proyectofinal

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.material.button.MaterialButton

class Perfiles : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        setContentView(R.layout.activity_perfiles)

        ocultarBarras()

        val btnRegresar = findViewById<MaterialButton>(R.id.btnRegresar)

        btnRegresar.setOnClickListener {
            finish()
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if (hasFocus) {
            ocultarBarras()
        }
    }

    private fun ocultarBarras() {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val controller = WindowInsetsControllerCompat(window, window.decorView)

        controller.hide(WindowInsetsCompat.Type.statusBars())
        controller.hide(WindowInsetsCompat.Type.navigationBars())

        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}