package br.com.giovannicampos.core.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Double.toCurrency(formatPattern: String): String {
    val decimalFormat = DecimalFormat(
        formatPattern,
        DecimalFormatSymbols(Locale("pt", "BR"))
    )

    return decimalFormat.format(this)
}

fun Long.timestampToDayOfMonth(): String {
    val locale = Locale("pt", "BR")
    val formatter = SimpleDateFormat("dd", locale)
    return formatter.format(Date(this))
}

fun Long.timestampToShortMonth(): String {
    val locale = Locale("pt", "BR")
    val formatter = SimpleDateFormat("MMM", locale)
    return formatter.format(Date(this)).uppercase(locale)
}

fun Long.timestampToYear(): String {
    val locale = Locale("pt", "BR")
    val formatter = SimpleDateFormat("yyyy", locale)
    return formatter.format(Date(this))
}

fun Long.timestampToDateFull(): String {
    val locale = Locale("pt", "BR")
    val formatter = SimpleDateFormat("dd 'de' MMMM 'de' yyyy", locale)
    return formatter.format(Date(this)).lowercase(locale)
}