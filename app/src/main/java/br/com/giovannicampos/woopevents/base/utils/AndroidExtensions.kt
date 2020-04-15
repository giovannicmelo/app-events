package br.com.giovannicampos.woopevents.base.utils

import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager

fun Int.toPx(): Float = (this * Resources.getSystem().displayMetrics.density)

fun Context.isConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null
}