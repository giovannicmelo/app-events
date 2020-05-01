package br.com.giovannicampos.core.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.net.ConnectivityManager
import java.util.Locale

fun Context.dpToPx(dp: Int): Float {
    return dp * this.resources.displayMetrics.density
}

fun Context.isConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null
}

fun Context.getAddresses(latitude: Double, longitude: Double): List<Address> {
    return Geocoder(
        this,
        Locale("pt", "BR")
    ).getFromLocation(latitude, longitude, 1)
}