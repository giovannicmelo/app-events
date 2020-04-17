package br.com.giovannicampos.appevents.base.utils

import android.content.Context
import android.net.ConnectivityManager

fun Context.dpToPx(dp: Int): Float {
    return dp * this.resources.displayMetrics.density
}

fun Context.isConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null
}