package br.com.hearthisat.dialog

import android.content.Context
import android.os.Bundle
import android.view.Window
import br.com.hearthisat.R
import br.com.hearthisat.utils.layout.BaseDialog

class LoadingDialog(context: Context) : BaseDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_loading)
        setCanceledOnTouchOutside(false)
    }
}