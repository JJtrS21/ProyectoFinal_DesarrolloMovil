package com.fei.proyectofinal

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) : SQLiteOpenHelper(
    context,
    "math_escape.db",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE partidas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                perfil TEXT,
                tipo TEXT,
                operaciones TEXT,
                dificultad TEXT,
                tiempo INTEGER,
                aciertos INTEGER,
                total INTEGER,
                fecha TEXT,
                hora TEXT
            )
        """)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS partidas")
        onCreate(db)
    }

    fun insertarPartida(
        perfil: String,
        tipo: String,
        operaciones: String,
        dificultad: String,
        tiempo: Int,
        aciertos: Int,
        total: Int
    ) {
        val db = writableDatabase

        // Fecha y hora actuales
        val fechaActual = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
            .format(java.util.Date())
        val horaActual = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
            .format(java.util.Date())

        db.execSQL("""
            INSERT INTO partidas (perfil, tipo, operaciones, dificultad, tiempo, aciertos, total, fecha, hora)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """, arrayOf(perfil, tipo, operaciones, dificultad, tiempo, aciertos, total, fechaActual, horaActual))

        db.close()
    }
}