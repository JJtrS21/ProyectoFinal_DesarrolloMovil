package com.fei.proyectofinal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class NivelesActivity : AppCompatActivity() {

    private var nivelCentral = 2
    private val totalNiveles = 10

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
        setContentView(R.layout.activity_niveles)

        btnAnterior = findViewById(R.id.btnAnterior)
        btnSiguiente = findViewById(R.id.btnSiguiente)
        btnRegresar = findViewById(R.id.btnRegresarNiveles)
        nivelIzquierdo = findViewById(R.id.btnNivelIzquierdo)
        nivelCentralView = findViewById(R.id.btnNivelCentral)
        nivelDerecho = findViewById(R.id.btnNivelDerecho)
        ivNivelIzquierdo = findViewById(R.id.ivNivelIzquierdo)
        ivNivelCentral = findViewById(R.id.ivNivelCentral)
        ivNivelDerecho = findViewById(R.id.ivNivelDerecho)

        actualizarCarrusel()

        btnAnterior.setOnClickListener {
            if (nivelCentral > 1) { nivelCentral--; actualizarCarrusel() }
        }

        btnSiguiente.setOnClickListener {
            if (nivelCentral < totalNiveles) { nivelCentral++; actualizarCarrusel() }
        }

        nivelIzquierdo.setOnClickListener {
            if (nivelCentral > 1) { nivelCentral--; actualizarCarrusel() }
        }

        nivelDerecho.setOnClickListener {
            if (nivelCentral < totalNiveles) { nivelCentral++; actualizarCarrusel() }
        }

        nivelCentralView.setOnClickListener {
            iniciarJuego(nivelCentral)
        }

        btnRegresar.setOnClickListener {
            finish()
        }
    }

    private fun actualizarCarrusel() {
        val izquierdo = nivelCentral - 1
        val derecho = nivelCentral + 1

        if (izquierdo >= 1) {
            nivelIzquierdo.visibility = View.VISIBLE
            ponerImagenONumero(ivNivelIzquierdo, izquierdo)
        } else {
            nivelIzquierdo.visibility = View.INVISIBLE
        }

        ponerImagenONumero(ivNivelCentral, nivelCentral)

        if (derecho <= totalNiveles) {
            nivelDerecho.visibility = View.VISIBLE
            ponerImagenONumero(ivNivelDerecho, derecho)
        } else {
            nivelDerecho.visibility = View.INVISIBLE
        }

        btnAnterior.isEnabled = nivelCentral > 1
        btnSiguiente.isEnabled = nivelCentral < totalNiveles
    }

    private fun ponerImagenONumero(imageView: ImageView, numero: Int) {
        val resId = resources.getIdentifier("a$numero", "drawable", packageName)
        if (resId != 0) {
            imageView.setImageResource(resId)
        } else {
            imageView.setImageResource(0)
        }
    }

    private fun iniciarJuego(nivel: Int) {
        val intent = Intent(this, JuegoActivity::class.java)
        when (nivel) {
            1 -> { intent.putExtra("operaciones", "SUMA"); intent.putExtra("dificultad", "FACIL"); intent.putExtra("tiempo", 0); intent.putExtra("tiposPreguntas", "OPERACIONES"); intent.putExtra("cantidad", 5) }
            2 -> { intent.putExtra("operaciones", "SUMA,RESTA"); intent.putExtra("dificultad", "FACIL"); intent.putExtra("tiempo", 0); intent.putExtra("tiposPreguntas", "OPERACIONES"); intent.putExtra("cantidad", 5) }
            3 -> { intent.putExtra("operaciones", "SUMA,RESTA"); intent.putExtra("dificultad", "FACIL"); intent.putExtra("tiempo", 30); intent.putExtra("tiposPreguntas", "OPERACIONES"); intent.putExtra("cantidad", 5) }
            4 -> { intent.putExtra("operaciones", "SUMA,RESTA,MULTIPLICACION"); intent.putExtra("dificultad", "FACIL"); intent.putExtra("tiempo", 30); intent.putExtra("tiposPreguntas", "OPERACIONES"); intent.putExtra("cantidad", 5) }
            5 -> { intent.putExtra("operaciones", "SUMA,RESTA,MULTIPLICACION,DIVISION"); intent.putExtra("dificultad", "FACIL"); intent.putExtra("tiempo", 30); intent.putExtra("tiposPreguntas", "OPERACIONES"); intent.putExtra("cantidad", 5) }
            6 -> { intent.putExtra("operaciones", "SUMA,RESTA,MULTIPLICACION,DIVISION"); intent.putExtra("dificultad", "MEDIO"); intent.putExtra("tiempo", 30); intent.putExtra("tiposPreguntas", "OPERACIONES"); intent.putExtra("cantidad", 7) }
            7 -> { intent.putExtra("operaciones", "SUMA,RESTA,MULTIPLICACION,DIVISION"); intent.putExtra("dificultad", "MEDIO"); intent.putExtra("tiempo", 30); intent.putExtra("tiposPreguntas", "OPERACIONES,PROBLEMAS"); intent.putExtra("cantidad", 7) }
            8 -> { intent.putExtra("operaciones", "SUMA,RESTA,MULTIPLICACION,DIVISION"); intent.putExtra("dificultad", "MEDIO"); intent.putExtra("tiempo", 15); intent.putExtra("tiposPreguntas", "OPERACIONES,PROBLEMAS"); intent.putExtra("cantidad", 7) }
            9 -> { intent.putExtra("operaciones", "SUMA,RESTA,MULTIPLICACION,DIVISION"); intent.putExtra("dificultad", "DIFICIL"); intent.putExtra("tiempo", 30); intent.putExtra("tiposPreguntas", "OPERACIONES,PROBLEMAS"); intent.putExtra("cantidad", 10) }
            10 -> { intent.putExtra("operaciones", "SUMA,RESTA,MULTIPLICACION,DIVISION"); intent.putExtra("dificultad", "DIFICIL"); intent.putExtra("tiempo", 15); intent.putExtra("tiposPreguntas", "OPERACIONES,PROBLEMAS"); intent.putExtra("cantidad", 10) }
        }
        startActivity(intent)
    }
}