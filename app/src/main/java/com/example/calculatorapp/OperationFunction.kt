package com.example.calculatorapp

fun add(num1: String, num2: String): Double {
    val a = num1.toDouble()
    val b = num2.toDouble()
    return a + b
}

fun subtract(num1: String, num2: String): Double {
    val a = num1.toDouble()
    val b = num2.toDouble()
    return a - b
}

fun multiply(num1: String, num2: String): Double {
    val a = num1.toDouble()
    val b = num2.toDouble()
    return a * b
}

fun divide(num1: String, num2: String): Double {
    val a = num1.toDouble()
    val b = num2.toDouble()
    return if (b == 0.0) {
        Double.NaN
    } else {
        a / b
    }
}