package br.com.giovannicampos.woopevents.base.utils

import android.content.res.Resources
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.ParseException
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
    return try {
        val locale = Locale("pt", "BR")
        val formatter = SimpleDateFormat("dd", locale)
        formatter.format(Date(this))
    } catch (e: ParseException) {
        ""
    }
}

fun Long.timestampToShortMonth(): String {
    return try {
        val locale = Locale("pt", "BR")
        val formatter = SimpleDateFormat("MMM", locale)
        formatter.format(Date(this)).toUpperCase(locale)
    } catch (e: ParseException) {
        ""
    }
}

fun Long.timestampToYear(): String {
    return try {
        val locale = Locale("pt", "BR")
        val formatter = SimpleDateFormat("yyyy", locale)
        formatter.format(Date(this))
    } catch (e: ParseException) {
        ""
    }
}