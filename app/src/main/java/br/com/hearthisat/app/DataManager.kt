package br.com.hearthisat.app

import android.content.IntentFilter
import android.net.ConnectivityManager
import br.com.hearthisat.utils.general.*
import br.com.hearthisat.utils.sensors.NetworkStateReceiver
import com.danikula.videocache.HttpProxyCacheServer



object DataManager {
    enum class Key {
        TOKEN
    }

    //region ## GENERAL VARIABLES
    var networkStateReceiver : NetworkStateReceiver? = null

    private var _proxy: HttpProxyCacheServer? = null
    val proxy: HttpProxyCacheServer
        get() {
            if (_proxy == null) {
                _proxy = HttpProxyCacheServer(App.instance.getContext())
            }

            return _proxy!!
        }

    //endregion

    //region ## STORED VARIABLES ##
    var token: String
        get() = PreferencesUtils.getString(key = Key.TOKEN)
        set(value) = PreferencesUtils.putString(key = Key.TOKEN, value = value)
    //endregion

    //region ## GENERAL FUNCTIONS ##
    fun clearData() {
        token = ""
    }

    fun startNetworkReceiver() {
        networkStateReceiver = NetworkStateReceiver()
        App.instance.getContext().registerReceiver(networkStateReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }
    //endregion
}
