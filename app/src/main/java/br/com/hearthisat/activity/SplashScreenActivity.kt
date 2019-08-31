package br.com.hearthisat.activity

import android.os.Bundle
import br.com.hearthisat.R
import br.com.hearthisat.app.DataManager
import br.com.hearthisat.utils.layout.BaseActivity
import org.jetbrains.anko.startActivity
import java.util.*
import kotlin.concurrent.schedule

class SplashScreenActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Timer().schedule(1000) {
            DataManager.startNetworkReceiver()

            startActivity<MainActivity>()
        }
    }
}

