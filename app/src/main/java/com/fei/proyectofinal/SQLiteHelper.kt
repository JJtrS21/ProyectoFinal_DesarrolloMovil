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
        // Crea la tabla donde se guardan las partidas realizadas
        db.execSQL(
            """
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
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Si cambia la versión de la base de datos, elimina la tabla y la vuelve a crear
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

        // Obtiene la fecha y hora actuales
        val fechaActual = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
            .format(java.util.Date())

        val horaActual = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
            .format(java.util.Date())

        // Inserta el registro de la partida en la base de datos
        db.execSQL(
            """
            INSERT INTO partidas 
            (perfil, tipo, operaciones, dificultad, tiempo, aciertos, total, fecha, hora)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """.trimIndent(),
            arrayOf(
                perfil,
                tipo,
                operaciones,
                dificultad,
                tiempo,
                aciertos,
                total,
                fechaActual,
                horaActual
            )
        )

        db.close()
    }

    fun obtenerPartidasPorPerfilYFecha(perfil: String, fecha: String): MutableList<Array<String>> {
        val db = readableDatabase
        val lista = mutableListOf<Array<String>>()

        val cursor = db.rawQuery(
            """
            SELECT hora, tipo, operaciones, dificultad, tiempo, aciertos, total
            FROM partidas
            WHERE perfil = ? AND fecha = ?
            ORDER BY hora DESC
            """.trimIndent(),
            arrayOf(perfil, fecha)
        )

        if (cursor.moveToFirst()) {
            do {
                val hora = cursor.getString(0)
                val tipo = cursor.getString(1)
                val operaciones = formatearOperaciones(cursor.getString(2))
                val dificultad = formatearDificultad(cursor.getString(3))
                val tiempo = cursor.getInt(4)
                val aciertos = cursor.getInt(5)
                val total = cursor.getInt(6)

                val tiempoTexto = if (tiempo == 0) {
                    "Sin límite"
                } else {
                    "$tiempo seg"
                }

                lista.add(
                    arrayOf(
                        hora,
                        tipo,
                        operaciones,
                        dificultad,
                        tiempoTexto,
                        "$aciertos/$total"
                    )
                )
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return lista
    }

    fun obtenerFechasPorPerfil(perfil: String): MutableList<String> {
        val db = readableDatabase
        val lista = mutableListOf<String>()

        val cursor = db.rawQuery(
            """
        SELECT DISTINCT fecha
        FROM partidas
        WHERE perfil = ?
        ORDER BY fecha DESC
        """.trimIndent(),
            arrayOf(perfil)
        )

        if (cursor.moveToFirst()) {
            do {
                lista.add(cursor.getString(0))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return lista
    }

    private fun formatearOperaciones(operaciones: String): String {
        return operaciones
            .replace("SUMA", "+")
            .replace("RESTA", "-")
            .replace("MULTIPLICACION", "×")
            .replace("DIVISION", "÷")
            .replace(",", ", ")
    }

    private fun formatearDificultad(dificultad: String): String {
        return when (dificultad.uppercase()) {
            "FACIL" -> "Fácil"
            "MEDIO" -> "Medio"
            "DIFICIL" -> "Difícil"
            else -> dificultad
        }
    }
}