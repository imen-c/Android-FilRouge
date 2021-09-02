package com.technipixl.filrouge.network.services

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

open class BaseServiceImpl: BaseService {

    override var baseUrl: String = ""

    fun isNetworkAvailable(context: Context) =
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
            getNetworkCapabilities(activeNetwork)?.run {
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            } ?: false
        }
}