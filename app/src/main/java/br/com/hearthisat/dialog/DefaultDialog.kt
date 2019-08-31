package br.com.hearthisat.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import br.com.hearthisat.R
import br.com.hearthisat.utils.layout.BaseDialog
import kotlinx.android.synthetic.main.dialog_default.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class DefaultDialog(context: Context,
                    private val title: String = "Alerta!",
                    private val text: String,
                    private val okAction: (() -> Unit)? = null,
                    private val yesAction: (() -> Unit)? = null,
                    private val noAction: (() -> Unit)? = null) : BaseDialog(context){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_default)
        setup()
    }

    private fun setup() {
        setCanceledOnTouchOutside(false)

        title_label.text = title
        message_label.text = text

        if (yesAction != null && noAction != null){
            ok_button.visibility = View.GONE
            yes_no_layout.visibility = View.VISIBLE

            yes_button.onClick {
                yesAction.invoke()
                dismiss()
            }

            no_button.onClick {
                noAction.invoke()
                dismiss()
            }
        } else {
            ok_button.visibility = View.VISIBLE
            yes_no_layout.visibility = View.GONE

            ok_button.onClick {
                okAction?.invoke()
                dismiss()
            }
        }
    }
}