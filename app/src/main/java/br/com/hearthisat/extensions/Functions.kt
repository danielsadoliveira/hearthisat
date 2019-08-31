package br.com.hearthisat.extensions

import android.content.Context
import android.net.ConnectivityManager
import br.com.hearthisat.app.App

fun isConnected(): Boolean {
    val connMgr = App.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    val networkInfo = connMgr?.activeNetworkInfo

    return networkInfo != null && networkInfo.isConnected
}