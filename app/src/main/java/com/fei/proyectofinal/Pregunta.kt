package com.fei.proyectofinal

data class Pregunta(
    val enunciado: String,
    val respuestas: List<Int>,
    val respuestaCorrecta: Int,
    val retroalimentacion: String,
    val esNarrativa: Boolean,
    val operacion: String,
    val dificultad: String
)