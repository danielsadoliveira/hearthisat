package br.com.hearthisat.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import br.com.hearthisat.R
import br.com.hearthisat.extensions.then
import br.com.hearthisat.utils.layout.BaseDialog
import kotlinx.android.synthetic.main.dialog_error.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class ErrorDialog(context: Context,
                  val title: String? = "Ops!",
                  val message: String? = "Ocorreu um erro ao executar esta ação!",
                  val action: () -> Unit = {}) : BaseDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_error)
        setup()
    }

    private fun setup() {
        setCanceledOnTouchOutside(false)
        ok_button.onClick {
            action.invoke()
            dismiss()
        }

        title_label.apply {
            visibility = !title.isNullOrEmpty() then View.VISIBLE ?: View.GONE
            text = !title.isNullOrEmpty() then title ?: ""
        }

        if (!message.isNullOrEmpty()) {
            message_label.text = message
        }
    }
}