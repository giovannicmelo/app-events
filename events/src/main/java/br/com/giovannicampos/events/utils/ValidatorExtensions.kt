package br.com.giovannicampos.events.utils

import androidx.core.util.PatternsCompat

fun String.isValidName(): Boolean {
    return this.isNotEmpty() && this.length >= 3
}

fun String.isValidEmail(): Boolean {
    return this.isNotEmpty() && PatternsCompat.EMAIL_ADDRESS.matcher(this).matches()
}