package com.fei.proyectofinal

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class Padres : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        ocultarBarras()

        setContentView(R.layout.activity_padres)

        val btnRegresar = findViewById<MaterialButton>(R.id.btnRegresar)

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

        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            window.insetsController?.hide(
                WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars()
            )

            window.insetsController?.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}