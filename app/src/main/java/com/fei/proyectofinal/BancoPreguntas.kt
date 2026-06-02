package com.fei.proyectofinal

object BancoPreguntas {

    private val todas = listOf(
        // ==================== SUMA FÁCIL (resultados hasta 10) ====================
        Pregunta("¿Cuánto es 3 + 5?", listOf(6, 8, 7, 9), 1, "3 + 5 = 8", false, "SUMA", "FACIL"),
        Pregunta("¿Cuánto es 7 + 2?", listOf(8, 9, 10, 7), 1, "7 + 2 = 9", false, "SUMA", "FACIL"),
        Pregunta("¿Cuánto es 4 + 6?", listOf(10, 9, 11, 8), 0, "4 + 6 = 10", false, "SUMA", "FACIL"),
        Pregunta("¿Cuánto es 1 + 4?", listOf(5, 4, 6, 3), 0, "1 + 4 = 5", false, "SUMA", "FACIL"),
        Pregunta("¿Cuánto es 2 + 8?", listOf(10, 9, 11, 8), 0, "2 + 8 = 10", false, "SUMA", "FACIL"),
        Pregunta("¿Cuánto es 6 + 3?", listOf(9, 8, 10, 7), 0, "6 + 3 = 9", false, "SUMA", "FACIL"),
        Pregunta("¿Cuánto es 0 + 7?", listOf(7, 6, 8, 5), 0, "0 + 7 = 7", false, "SUMA", "FACIL"),
        Pregunta("¿Cuánto es 5 + 5?", listOf(10, 9, 11, 8), 0, "5 + 5 = 10", false, "SUMA", "FACIL"),

        // ==================== RESTA FÁCIL (números hasta 10) ====================
        Pregunta("¿Cuánto es 9 - 4?", listOf(5, 6, 4, 3), 0, "9 - 4 = 5", false, "RESTA", "FACIL"),
        Pregunta("¿Cuánto es 8 - 3?", listOf(5, 4, 6, 3), 0, "8 - 3 = 5", false, "RESTA", "FACIL"),
        Pregunta("¿Cuánto es 10 - 7?", listOf(3, 2, 4, 1), 0, "10 - 7 = 3", false, "RESTA", "FACIL"),
        Pregunta("¿Cuánto es 6 - 2?", listOf(4, 3, 5, 2), 0, "6 - 2 = 4", false, "RESTA", "FACIL"),
        Pregunta("¿Cuánto es 7 - 5?", listOf(2, 3, 1, 4), 0, "7 - 5 = 2", false, "RESTA", "FACIL"),
        Pregunta("¿Cuánto es 9 - 1?", listOf(8, 7, 9, 6), 0, "9 - 1 = 8", false, "RESTA", "FACIL"),
        Pregunta("¿Cuánto es 5 - 0?", listOf(5, 4, 6, 3), 0, "5 - 0 = 5", false, "RESTA", "FACIL"),
        Pregunta("¿Cuánto es 10 - 5?", listOf(5, 4, 6, 3), 0, "10 - 5 = 5", false, "RESTA", "FACIL"),

        // ==================== MULTIPLICACIÓN FÁCIL (tablas 1, 2, 3, 10) ====================
        Pregunta("¿Cuánto es 2 × 3?", listOf(6, 5, 7, 4), 0, "2 × 3 = 6", false, "MULTIPLICACION", "FACIL"),
        Pregunta("¿Cuánto es 3 × 3?", listOf(9, 8, 10, 6), 0, "3 × 3 = 9", false, "MULTIPLICACION", "FACIL"),
        Pregunta("¿Cuánto es 1 × 5?", listOf(5, 4, 6, 3), 0, "1 × 5 = 5", false, "MULTIPLICACION", "FACIL"),
        Pregunta("¿Cuánto es 1 × 9?", listOf(9, 8, 10, 7), 0, "1 × 9 = 9", false, "MULTIPLICACION", "FACIL"),
        Pregunta("¿Cuánto es 2 × 4?", listOf(8, 7, 9, 6), 0, "2 × 4 = 8", false, "MULTIPLICACION", "FACIL"),
        Pregunta("¿Cuánto es 2 × 5?", listOf(10, 9, 11, 8), 0, "2 × 5 = 10", false, "MULTIPLICACION", "FACIL"),
        Pregunta("¿Cuánto es 3 × 2?", listOf(6, 5, 7, 4), 0, "3 × 2 = 6", false, "MULTIPLICACION", "FACIL"),
        Pregunta("¿Cuánto es 3 × 4?", listOf(12, 10, 14, 11), 0, "3 × 4 = 12", false, "MULTIPLICACION", "FACIL"),
        Pregunta("¿Cuánto es 10 × 1?", listOf(10, 9, 11, 8), 0, "10 × 1 = 10", false, "MULTIPLICACION", "FACIL"),
        Pregunta("¿Cuánto es 10 × 3?", listOf(30, 28, 32, 29), 0, "10 × 3 = 30", false, "MULTIPLICACION", "FACIL"),
        Pregunta("¿Cuánto es 10 × 7?", listOf(70, 68, 72, 69), 0, "10 × 7 = 70", false, "MULTIPLICACION", "FACIL"),
        Pregunta("¿Cuánto es 10 × 9?", listOf(90, 88, 92, 89), 0, "10 × 9 = 90", false, "MULTIPLICACION", "FACIL"),

        // ==================== DIVISIÓN FÁCIL (tablas 1, 2, 3, 10) ====================
        Pregunta("¿Cuánto es 6 ÷ 2?", listOf(3, 2, 4, 1), 0, "6 ÷ 2 = 3", false, "DIVISION", "FACIL"),
        Pregunta("¿Cuánto es 10 ÷ 2?", listOf(5, 4, 6, 3), 0, "10 ÷ 2 = 5", false, "DIVISION", "FACIL"),
        Pregunta("¿Cuánto es 9 ÷ 3?", listOf(3, 2, 4, 1), 0, "9 ÷ 3 = 3", false, "DIVISION", "FACIL"),
        Pregunta("¿Cuánto es 4 ÷ 2?", listOf(2, 1, 3, 4), 0, "4 ÷ 2 = 2", false, "DIVISION", "FACIL"),
        Pregunta("¿Cuánto es 8 ÷ 2?", listOf(4, 3, 5, 2), 0, "8 ÷ 2 = 4", false, "DIVISION", "FACIL"),
        Pregunta("¿Cuánto es 3 ÷ 1?", listOf(3, 2, 4, 1), 0, "3 ÷ 1 = 3", false, "DIVISION", "FACIL"),
        Pregunta("¿Cuánto es 6 ÷ 3?", listOf(2, 1, 3, 4), 0, "6 ÷ 3 = 2", false, "DIVISION", "FACIL"),
        Pregunta("¿Cuánto es 20 ÷ 10?", listOf(2, 1, 3, 4), 0, "20 ÷ 10 = 2", false, "DIVISION", "FACIL"),
        Pregunta("¿Cuánto es 50 ÷ 10?", listOf(5, 4, 6, 3), 0, "50 ÷ 10 = 5", false, "DIVISION", "FACIL"),
        Pregunta("¿Cuánto es 100 ÷ 10?", listOf(10, 9, 11, 8), 0, "100 ÷ 10 = 10", false, "DIVISION", "FACIL"),

        // ==================== SUMA MEDIO (resultados hasta 20) ====================
        Pregunta("¿Cuánto es 15 + 4?", listOf(19, 18, 20, 17), 0, "15 + 4 = 19", false, "SUMA", "MEDIO"),
        Pregunta("¿Cuánto es 12 + 8?", listOf(20, 19, 21, 18), 0, "12 + 8 = 20", false, "SUMA", "MEDIO"),
        Pregunta("¿Cuánto es 11 + 3?", listOf(14, 13, 15, 12), 0, "11 + 3 = 14", false, "SUMA", "MEDIO"),
        Pregunta("¿Cuánto es 7 + 9?", listOf(16, 15, 17, 14), 0, "7 + 9 = 16", false, "SUMA", "MEDIO"),
        Pregunta("¿Cuánto es 13 + 6?", listOf(19, 18, 20, 17), 0, "13 + 6 = 19", false, "SUMA", "MEDIO"),
        Pregunta("¿Cuánto es 10 + 10?", listOf(20, 19, 21, 18), 0, "10 + 10 = 20", false, "SUMA", "MEDIO"),
        Pregunta("¿Cuánto es 16 + 3?", listOf(19, 18, 20, 17), 0, "16 + 3 = 19", false, "SUMA", "MEDIO"),
        Pregunta("¿Cuánto es 8 + 11?", listOf(19, 18, 20, 17), 0, "8 + 11 = 19", false, "SUMA", "MEDIO"),

        // ==================== RESTA MEDIO (números hasta 20) ====================
        Pregunta("¿Cuánto es 18 - 9?", listOf(9, 8, 10, 7), 0, "18 - 9 = 9", false, "RESTA", "MEDIO"),
        Pregunta("¿Cuánto es 20 - 13?", listOf(7, 6, 8, 5), 0, "20 - 13 = 7", false, "RESTA", "MEDIO"),
        Pregunta("¿Cuánto es 15 - 8?", listOf(7, 6, 8, 5), 0, "15 - 8 = 7", false, "RESTA", "MEDIO"),
        Pregunta("¿Cuánto es 19 - 11?", listOf(8, 7, 9, 6), 0, "19 - 11 = 8", false, "RESTA", "MEDIO"),
        Pregunta("¿Cuánto es 17 - 5?", listOf(12, 11, 13, 10), 0, "17 - 5 = 12", false, "RESTA", "MEDIO"),
        Pregunta("¿Cuánto es 14 - 6?", listOf(8, 7, 9, 6), 0, "14 - 6 = 8", false, "RESTA", "MEDIO"),
        Pregunta("¿Cuánto es 20 - 4?", listOf(16, 15, 17, 14), 0, "20 - 4 = 16", false, "RESTA", "MEDIO"),
        Pregunta("¿Cuánto es 13 - 7?", listOf(6, 5, 7, 4), 0, "13 - 7 = 6", false, "RESTA", "MEDIO"),

        // ==================== MULTIPLICACIÓN MEDIO (tablas 4, 5, 6) ====================
        Pregunta("¿Cuánto es 4 × 6?", listOf(24, 22, 26, 20), 0, "4 × 6 = 24", false, "MULTIPLICACION", "MEDIO"),
        Pregunta("¿Cuánto es 5 × 5?", listOf(25, 20, 30, 15), 0, "5 × 5 = 25", false, "MULTIPLICACION", "MEDIO"),
        Pregunta("¿Cuánto es 4 × 3?", listOf(12, 10, 14, 11), 0, "4 × 3 = 12", false, "MULTIPLICACION", "MEDIO"),
        Pregunta("¿Cuánto es 4 × 7?", listOf(28, 26, 30, 24), 0, "4 × 7 = 28", false, "MULTIPLICACION", "MEDIO"),
        Pregunta("¿Cuánto es 4 × 9?", listOf(36, 34, 38, 32), 0, "4 × 9 = 36", false, "MULTIPLICACION", "MEDIO"),
        Pregunta("¿Cuánto es 5 × 3?", listOf(15, 13, 17, 12), 0, "5 × 3 = 15", false, "MULTIPLICACION", "MEDIO"),
        Pregunta("¿Cuánto es 5 × 6?", listOf(30, 28, 32, 25), 0, "5 × 6 = 30", false, "MULTIPLICACION", "MEDIO"),
        Pregunta("¿Cuánto es 5 × 8?", listOf(40, 38, 42, 35), 0, "5 × 8 = 40", false, "MULTIPLICACION", "MEDIO"),
        Pregunta("¿Cuánto es 6 × 2?", listOf(12, 10, 14, 8), 0, "6 × 2 = 12", false, "MULTIPLICACION", "MEDIO"),
        Pregunta("¿Cuánto es 6 × 4?", listOf(24, 22, 26, 20), 0, "6 × 4 = 24", false, "MULTIPLICACION", "MEDIO"),
        Pregunta("¿Cuánto es 6 × 7?", listOf(42, 40, 44, 38), 0, "6 × 7 = 42", false, "MULTIPLICACION", "MEDIO"),
        Pregunta("¿Cuánto es 6 × 9?", listOf(54, 52, 56, 50), 0, "6 × 9 = 54", false, "MULTIPLICACION", "MEDIO"),

        // ==================== DIVISIÓN MEDIO (tablas 4, 5, 6) ====================
        Pregunta("¿Cuánto es 24 ÷ 4?", listOf(6, 5, 7, 4), 0, "24 ÷ 4 = 6", false, "DIVISION", "MEDIO"),
        Pregunta("¿Cuánto es 30 ÷ 5?", listOf(6, 5, 7, 4), 0, "30 ÷ 5 = 6", false, "DIVISION", "MEDIO"),
        Pregunta("¿Cuánto es 20 ÷ 4?", listOf(5, 4, 6, 3), 0, "20 ÷ 4 = 5", false, "DIVISION", "MEDIO"),
        Pregunta("¿Cuánto es 36 ÷ 6?", listOf(6, 5, 7, 4), 0, "36 ÷ 6 = 6", false, "DIVISION", "MEDIO"),
        Pregunta("¿Cuánto es 16 ÷ 4?", listOf(4, 3, 5, 2), 0, "16 ÷ 4 = 4", false, "DIVISION", "MEDIO"),
        Pregunta("¿Cuánto es 25 ÷ 5?", listOf(5, 4, 6, 3), 0, "25 ÷ 5 = 5", false, "DIVISION", "MEDIO"),
        Pregunta("¿Cuánto es 42 ÷ 6?", listOf(7, 6, 8, 5), 0, "42 ÷ 6 = 7", false, "DIVISION", "MEDIO"),
        Pregunta("¿Cuánto es 12 ÷ 4?", listOf(3, 2, 4, 1), 0, "12 ÷ 4 = 3", false, "DIVISION", "MEDIO"),

        // ==================== SUMA DIFÍCIL (resultados hasta 100) ====================
        Pregunta("¿Cuánto es 45 + 32?", listOf(77, 75, 79, 73), 0, "45 + 32 = 77", false, "SUMA", "DIFICIL"),
        Pregunta("¿Cuánto es 68 + 7?", listOf(75, 73, 77, 71), 0, "68 + 7 = 75", false, "SUMA", "DIFICIL"),
        Pregunta("¿Cuánto es 29 + 43?", listOf(72, 70, 74, 68), 0, "29 + 43 = 72", false, "SUMA", "DIFICIL"),
        Pregunta("¿Cuánto es 56 + 28?", listOf(84, 82, 86, 80), 0, "56 + 28 = 84", false, "SUMA", "DIFICIL"),
        Pregunta("¿Cuánto es 33 + 59?", listOf(92, 90, 94, 88), 0, "33 + 59 = 92", false, "SUMA", "DIFICIL"),
        Pregunta("¿Cuánto es 71 + 19?", listOf(90, 88, 92, 86), 0, "71 + 19 = 90", false, "SUMA", "DIFICIL"),
        Pregunta("¿Cuánto es 44 + 44?", listOf(88, 86, 90, 84), 0, "44 + 44 = 88", false, "SUMA", "DIFICIL"),
        Pregunta("¿Cuánto es 50 + 37?", listOf(87, 85, 89, 83), 0, "50 + 37 = 87", false, "SUMA", "DIFICIL"),

        // ==================== RESTA DIFÍCIL (números hasta 100) ====================
        Pregunta("¿Cuánto es 83 - 27?", listOf(56, 54, 58, 52), 0, "83 - 27 = 56", false, "RESTA", "DIFICIL"),
        Pregunta("¿Cuánto es 50 - 15?", listOf(35, 33, 37, 31), 0, "50 - 15 = 35", false, "RESTA", "DIFICIL"),
        Pregunta("¿Cuánto es 91 - 46?", listOf(45, 43, 47, 41), 0, "91 - 46 = 45", false, "RESTA", "DIFICIL"),
        Pregunta("¿Cuánto es 100 - 38?", listOf(62, 60, 64, 58), 0, "100 - 38 = 62", false, "RESTA", "DIFICIL"),
        Pregunta("¿Cuánto es 67 - 29?", listOf(38, 36, 40, 34), 0, "67 - 29 = 38", false, "RESTA", "DIFICIL"),
        Pregunta("¿Cuánto es 74 - 18?", listOf(56, 54, 58, 52), 0, "74 - 18 = 56", false, "RESTA", "DIFICIL"),
        Pregunta("¿Cuánto es 88 - 53?", listOf(35, 33, 37, 31), 0, "88 - 53 = 35", false, "RESTA", "DIFICIL"),
        Pregunta("¿Cuánto es 95 - 67?", listOf(28, 26, 30, 24), 0, "95 - 67 = 28", false, "RESTA", "DIFICIL"),

        // ==================== MULTIPLICACIÓN DIFÍCIL (tablas 7, 8, 9) ====================
        Pregunta("¿Cuánto es 7 × 8?", listOf(56, 54, 58, 52), 0, "7 × 8 = 56", false, "MULTIPLICACION", "DIFICIL"),
        Pregunta("¿Cuánto es 9 × 9?", listOf(81, 79, 83, 77), 0, "9 × 9 = 81", false, "MULTIPLICACION", "DIFICIL"),
        Pregunta("¿Cuánto es 7 × 3?", listOf(21, 19, 23, 17), 0, "7 × 3 = 21", false, "MULTIPLICACION", "DIFICIL"),
        Pregunta("¿Cuánto es 7 × 5?", listOf(35, 33, 37, 30), 0, "7 × 5 = 35", false, "MULTIPLICACION", "DIFICIL"),
        Pregunta("¿Cuánto es 7 × 6?", listOf(42, 40, 44, 38), 0, "7 × 6 = 42", false, "MULTIPLICACION", "DIFICIL"),
        Pregunta("¿Cuánto es 7 × 9?", listOf(63, 61, 65, 59), 0, "7 × 9 = 63", false, "MULTIPLICACION", "DIFICIL"),
        Pregunta("¿Cuánto es 8 × 4?", listOf(32, 30, 34, 28), 0, "8 × 4 = 32", false, "MULTIPLICACION", "DIFICIL"),
        Pregunta("¿Cuánto es 8 × 6?", listOf(48, 46, 50, 44), 0, "8 × 6 = 48", false, "MULTIPLICACION", "DIFICIL"),
        Pregunta("¿Cuánto es 8 × 7?", listOf(56, 54, 58, 52), 0, "8 × 7 = 56", false, "MULTIPLICACION", "DIFICIL"),
        Pregunta("¿Cuánto es 8 × 9?", listOf(72, 70, 74, 68), 0, "8 × 9 = 72", false, "MULTIPLICACION", "DIFICIL"),
        Pregunta("¿Cuánto es 9 × 2?", listOf(18, 16, 20, 14), 0, "9 × 2 = 18", false, "MULTIPLICACION", "DIFICIL"),
        Pregunta("¿Cuánto es 9 × 5?", listOf(45, 43, 47, 40), 0, "9 × 5 = 45", false, "MULTIPLICACION", "DIFICIL"),
        Pregunta("¿Cuánto es 9 × 7?", listOf(63, 61, 65, 59), 0, "9 × 7 = 63", false, "MULTIPLICACION", "DIFICIL"),
        Pregunta("¿Cuánto es 9 × 8?", listOf(72, 70, 74, 68), 0, "9 × 8 = 72", false, "MULTIPLICACION", "DIFICIL"),

        // ==================== DIVISIÓN DIFÍCIL (tablas 7, 8, 9) ====================
        Pregunta("¿Cuánto es 63 ÷ 7?", listOf(9, 8, 10, 7), 0, "63 ÷ 7 = 9", false, "DIVISION", "DIFICIL"),
        Pregunta("¿Cuánto es 72 ÷ 8?", listOf(9, 8, 10, 7), 0, "72 ÷ 8 = 9", false, "DIVISION", "DIFICIL"),
        Pregunta("¿Cuánto es 81 ÷ 9?", listOf(9, 8, 10, 7), 0, "81 ÷ 9 = 9", false, "DIVISION", "DIFICIL"),
        Pregunta("¿Cuánto es 28 ÷ 7?", listOf(4, 3, 5, 2), 0, "28 ÷ 7 = 4", false, "DIVISION", "DIFICIL"),
        Pregunta("¿Cuánto es 56 ÷ 8?", listOf(7, 6, 8, 5), 0, "56 ÷ 8 = 7", false, "DIVISION", "DIFICIL"),
        Pregunta("¿Cuánto es 49 ÷ 7?", listOf(7, 6, 8, 5), 0, "49 ÷ 7 = 7", false, "DIVISION", "DIFICIL"),
        Pregunta("¿Cuánto es 32 ÷ 8?", listOf(4, 3, 5, 2), 0, "32 ÷ 8 = 4", false, "DIVISION", "DIFICIL"),
        Pregunta("¿Cuánto es 54 ÷ 9?", listOf(6, 5, 7, 4), 0, "54 ÷ 9 = 6", false, "DIVISION", "DIFICIL"),

        // ==================== PROBLEMAS NARRATIVOS ====================
        // SUMA FÁCIL
        Pregunta("El zorrito tenía 3 bellotas y encontró 2 más. ¿Cuántas tiene ahora?", listOf(5, 6, 4, 3), 0, "3 + 2 = 5 bellotas", true, "SUMA", "FACIL"),
        Pregunta("En el árbol hay 4 pajaritos y llegan 3 más. ¿Cuántos hay en total?", listOf(7, 6, 8, 5), 0, "4 + 3 = 7 pajaritos", true, "SUMA", "FACIL"),
        Pregunta("La ardilla tiene 5 nueces y encuentra 4 más. ¿Cuántas nueces tiene ahora?", listOf(9, 8, 10, 7), 0, "5 + 4 = 9 nueces", true, "SUMA", "FACIL"),
        Pregunta("María tiene 6 flores y recoge 2 más. ¿Cuántas flores tiene?", listOf(8, 7, 9, 6), 0, "6 + 2 = 8 flores", true, "SUMA", "FACIL"),

        // RESTA FÁCIL
        Pregunta("Pepito tenía 8 manzanas y regaló 3. ¿Cuántas le quedan?", listOf(5, 6, 4, 7), 0, "8 - 3 = 5 manzanas", true, "RESTA", "FACIL"),
        Pregunta("El osito tenía 7 panqueques y se comió 4. ¿Cuántos le quedan?", listOf(3, 2, 4, 5), 0, "7 - 4 = 3 panqueques", true, "RESTA", "FACIL"),
        Pregunta("En el estanque había 9 patitos y se fueron 5. ¿Cuántos quedan?", listOf(4, 3, 5, 2), 0, "9 - 5 = 4 patitos", true, "RESTA", "FACIL"),
        Pregunta("Carlos tenía 10 bolitas y perdió 6. ¿Cuántas le quedan?", listOf(4, 3, 5, 2), 0, "10 - 6 = 4 bolitas", true, "RESTA", "FACIL"),

        // MULTIPLICACIÓN FÁCIL
        Pregunta("Hay 3 mesas con 4 sillas cada una. ¿Cuántas sillas hay en total?", listOf(12, 10, 14, 8), 0, "3 × 4 = 12 sillas", true, "MULTIPLICACION", "FACIL"),
        Pregunta("La conejita tiene 5 jaulas con 2 conejos cada una. ¿Cuántos conejos tiene?", listOf(10, 8, 12, 6), 0, "5 × 2 = 10 conejos", true, "MULTIPLICACION", "FACIL"),
        Pregunta("Cada mochila tiene 3 libros y hay 5 mochilas. ¿Cuántos libros hay?", listOf(15, 12, 18, 10), 0, "3 × 5 = 15 libros", true, "MULTIPLICACION", "FACIL"),

        // DIVISIÓN FÁCIL
        Pregunta("Tienes 10 galletas y las repartes entre 2 amigos. ¿Cuántas tocan?", listOf(5, 4, 6, 3), 0, "10 ÷ 2 = 5 galletas", true, "DIVISION", "FACIL"),
        Pregunta("Hay 9 fresas para 3 niños. ¿Cuántas fresas come cada uno?", listOf(3, 2, 4, 1), 0, "9 ÷ 3 = 3 fresas", true, "DIVISION", "FACIL"),
        Pregunta("Mamá reparte 6 dulces entre sus 3 hijos. ¿Cuántos dulces toca cada uno?", listOf(2, 1, 3, 4), 0, "6 ÷ 3 = 2 dulces", true, "DIVISION", "FACIL"),

        // SUMA MEDIO
        Pregunta("La ardilla guardó 12 bellotas y encontró 7 más. ¿Cuántas tiene ahora?", listOf(19, 18, 20, 17), 0, "12 + 7 = 19 bellotas", true, "SUMA", "MEDIO"),
        Pregunta("En el campamento hay 15 niños y llegan 4 más. ¿Cuántos niños hay en total?", listOf(19, 18, 20, 17), 0, "15 + 4 = 19 niños", true, "SUMA", "MEDIO"),
        Pregunta("El zorrito recogió 11 flores en la mañana y 8 en la tarde. ¿Cuántas flores recogió?", listOf(19, 18, 20, 17), 0, "11 + 8 = 19 flores", true, "SUMA", "MEDIO"),

        // RESTA MEDIO
        Pregunta("El conejo tenía 15 zanahorias y se comió 6. ¿Cuántas le quedan?", listOf(9, 8, 10, 7), 0, "15 - 6 = 9 zanahorias", true, "RESTA", "MEDIO"),
        Pregunta("En la biblioteca había 18 libros y prestaron 9. ¿Cuántos libros quedan?", listOf(9, 8, 10, 7), 0, "18 - 9 = 9 libros", true, "RESTA", "MEDIO"),
        Pregunta("Pedro tenía 17 láminas y regaló 8. ¿Cuántas láminas le quedan?", listOf(9, 8, 10, 7), 0, "17 - 8 = 9 láminas", true, "RESTA", "MEDIO"),

        // MULTIPLICACIÓN MEDIO
        Pregunta("Hay 4 cajas con 5 lápices. ¿Cuántos lápices hay en total?", listOf(20, 18, 22, 16), 0, "4 × 5 = 20 lápices", true, "MULTIPLICACION", "MEDIO"),
        Pregunta("Cada bolsa tiene 6 manzanas y hay 5 bolsas. ¿Cuántas manzanas hay?", listOf(30, 28, 32, 25), 0, "5 × 6 = 30 manzanas", true, "MULTIPLICACION", "MEDIO"),
        Pregunta("La osa hornea 4 galletas por bandeja y hace 6 bandejas. ¿Cuántas galletas horneó?", listOf(24, 22, 26, 20), 0, "4 × 6 = 24 galletas", true, "MULTIPLICACION", "MEDIO"),

        // DIVISIÓN MEDIO
        Pregunta("Tienes 20 galletas y las repartes entre 4 amigos. ¿Cuántas tocan?", listOf(5, 4, 6, 3), 0, "20 ÷ 4 = 5 galletas", true, "DIVISION", "MEDIO"),
        Pregunta("La maestra reparte 30 hojas entre 5 niños. ¿Cuántas hojas toca cada uno?", listOf(6, 5, 7, 4), 0, "30 ÷ 5 = 6 hojas", true, "DIVISION", "MEDIO"),
        Pregunta("Hay 24 caramelos para 6 niños. ¿Cuántos caramelos come cada uno?", listOf(4, 3, 5, 2), 0, "24 ÷ 6 = 4 caramelos", true, "DIVISION", "MEDIO"),

        // SUMA DIFÍCIL
        Pregunta("En el bosque hay 45 árboles y se plantan 32 más. ¿Cuántos hay ahora?", listOf(77, 75, 79, 73), 0, "45 + 32 = 77 árboles", true, "SUMA", "DIFICIL"),
        Pregunta("La exploradora recorrió 56 kilómetros el lunes y 28 el martes. ¿Cuántos recorrió en total?", listOf(84, 82, 86, 80), 0, "56 + 28 = 84 kilómetros", true, "SUMA", "DIFICIL"),
        Pregunta("En la feria vendieron 67 globos rojos y 29 azules. ¿Cuántos globos vendieron?", listOf(96, 94, 98, 92), 0, "67 + 29 = 96 globos", true, "SUMA", "DIFICIL"),

        // RESTA DIFÍCIL
        Pregunta("El guardabosques tenía 83 semillas y plantó 27. ¿Cuántas le quedan?", listOf(56, 54, 58, 52), 0, "83 - 27 = 56 semillas", true, "RESTA", "DIFICIL"),
        Pregunta("En el campamento había 100 leños y usaron 38. ¿Cuántos leños quedan?", listOf(62, 60, 64, 58), 0, "100 - 38 = 62 leños", true, "RESTA", "DIFICIL"),
        Pregunta("La ardilla tenía 91 bellotas y se comió 46. ¿Cuántas le quedan?", listOf(45, 43, 47, 41), 0, "91 - 46 = 45 bellotas", true, "RESTA", "DIFICIL"),

        // MULTIPLICACIÓN DIFÍCIL
        Pregunta("El zorrito recoge 7 flores por día durante 8 días. ¿Cuántas flores recogió?", listOf(56, 54, 58, 52), 0, "7 × 8 = 56 flores", true, "MULTIPLICACION", "DIFICIL"),
        Pregunta("Cada árbol tiene 9 manzanas y hay 7 árboles. ¿Cuántas manzanas hay en total?", listOf(63, 61, 65, 59), 0, "9 × 7 = 63 manzanas", true, "MULTIPLICACION", "DIFICIL"),
        Pregunta("Hay 8 equipos con 9 niños cada uno. ¿Cuántos niños hay en total?", listOf(72, 70, 74, 68), 0, "8 × 9 = 72 niños", true, "MULTIPLICACION", "DIFICIL"),

        // DIVISIÓN DIFÍCIL
        Pregunta("Tienes 63 semillas y las pones en 7 macetas. ¿Cuántas semillas por maceta?", listOf(9, 8, 10, 7), 0, "63 ÷ 7 = 9 semillas", true, "DIVISION", "DIFICIL"),
        Pregunta("La maestra reparte 72 lápices entre 8 mesas. ¿Cuántos lápices por mesa?", listOf(9, 8, 10, 7), 0, "72 ÷ 8 = 9 lápices", true, "DIVISION", "DIFICIL"),
        Pregunta("Hay 81 páginas para leer en 9 días. ¿Cuántas páginas por día?", listOf(9, 8, 10, 7), 0, "81 ÷ 9 = 9 páginas", true, "DIVISION", "DIFICIL")
    )

    fun filtrar(operaciones: List<String>, dificultad: String, tiposPreguntas: List<String>): List<Pregunta> {
        return todas
            .filter { it.operacion in operaciones }
            .filter { it.dificultad == dificultad }
            .filter { if (it.esNarrativa) "PROBLEMAS" in tiposPreguntas else "OPERACIONES" in tiposPreguntas }
            .shuffled()
    }
}