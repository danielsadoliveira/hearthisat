package br.com.hearthisat.utils.layout

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import br.com.hearthisat.dialog.LoadingDialog
import br.com.hearthisat.enums.ContentType
import br.com.hearthisat.extensions.ContextAware
import br.com.hearthisat.extensions.caseNotNull
import br.com.hearthisat.extensions.caseNull

abstract class BaseFragment : Fragment(), ContextAware {
    var initialized = false

    var loadingDialog : LoadingDialog? = null

    override fun onStart() {
        super.onStart()
        if (!initialized){
            setup()
            initialized = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        initialized = false
    }

    open fun setup() { }

    fun getContent(type: ContentType) {
        (activity as BaseActivity).getContent(type = type)
    }

    fun openKeyboard(editText : EditText) {
        (activity as BaseActivity).openKeyboard(editText = editText)
    }

    fun closeKeyboard() {
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity!!.window?.currentFocus?.windowToken, 0)
    }

    fun clearCache() {
        (activity as BaseActivity).clearCache()
    }

    fun startLoading(){
        loadingDialog.caseNull {
            loadingDialog = LoadingDialog(activity!!)
        }

        loadingDialog!!.show()
    }

    fun stopLoading(){
        loadingDialog.caseNotNull {
            loadingDialog!!.dismiss()
        }
    }
}