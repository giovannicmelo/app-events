package br.com.giovannicampos.core.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import java.util.Locale

fun Context.dpToPx(dp: Int): Float {
    return dp * this.resources.displayMetrics.density
}

fun Context.isConnected(): Boolean {
    var result = false
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    cm?.run {
        cm.getNetworkCapabilities(cm.activeNetwork)?.run {
            result = when {
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
    }
    return result
}

fun Context.getAddresses(latitude: Double, longitude: Double): List<Address> {
    return Geocoder(
        this,
        Locale("pt", "BR")
    ).getFromLocation(latitude, longitude, 1)
}