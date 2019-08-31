package br.com.hearthisat.utils.layout

import android.app.Dialog
import android.content.Context
import android.view.View
import br.com.hearthisat.R
import br.com.hearthisat.app.App
import br.com.hearthisat.extensions.caseNotNull
import br.com.hearthisat.extensions.isConnected
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.error

abstract class BaseDialog(context: Context) : Dialog(context, theme){
    val mContext = context

    companion object {
        const val theme: Int = R.style.DialogTheme
        val app: App = App.instance
    }
}
