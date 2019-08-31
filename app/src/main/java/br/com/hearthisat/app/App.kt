package br.com.hearthisat.app

import android.app.Application
import android.content.Context
import android.graphics.Point
import android.util.DisplayMetrics
import br.com.hearthisat.extensions.ContextAware
import br.com.hearthisat.service.config.APIEnvironment
import org.jetbrains.anko.windowManager
import java.text.SimpleDateFormat
import java.util.*

class App: Application(), ContextAware {
    var environment : APIEnvironment = APIEnvironment.production
    var today: Date = Date()
    var dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)

    companion object {
        private var _instance: App? = null
        val instance: App
            get() = _instance ?: throw IllegalStateException("App not created")

        val displayMetrics : DisplayMetrics
            get() {
                val metrics = DisplayMetrics()
                instance.getContext().windowManager.defaultDisplay.getMetrics(metrics)
                return metrics
            }

        val screenSize : Point
            get() {
                val size = Point()
                instance.getContext().windowManager.defaultDisplay.getSize(size)
                return size
            }
    }

    override fun getContext(): Context = applicationContext

    override fun onCreate() {
        super.onCreate()
        _instance = this
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"))
    }
}