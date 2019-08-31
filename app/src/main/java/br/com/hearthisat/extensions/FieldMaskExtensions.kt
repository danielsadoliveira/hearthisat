package br.com.hearthisat.extensions

import android.text.InputType
import android.text.method.DigitsKeyListener
import android.widget.EditText
import com.redmadrobot.inputmask.MaskedTextChangedListener

/**
 * Masks consist of blocks of symbols, which may include:
 * . [] — a block for valueable symbols written by usuario.
 *
 * Square brackets block may contain any number of special symbols:
 * . 0 — mandatory digit. For instance, [000] mask will allow usuario to enter three numbers: 123.
 * . 9 — optional digit . For instance, [00099] mask will allow usuario to enter from three to five numbers.
 * . А — mandatory letter. [AAA] mask will allow usuario to enter three letters: abc.
 * . а — optional letter. [АААааа] mask will allow to enter from three to six letters.
 * . _ — mandatory symbol (digit or letter).
 * . - — optional symbol (digit or letter).
 * . … — ellipsis. Allows to enter endless count of symbols.
 *
 * Created by danielsadoliveira
 */

object MaskFormats {
    const val CPF = "[000].[000].[000]-[00]"
    const val RG = "[0000000000]"
    const val FONE = "([00]) [0000]-[0000]"
    const val CEP = "[00000]-[000]"
    const val DATE = "[00]/[00]/[0000]"
    const val TIME = "[00]:[00]"

    const val HEIGHT = "[0].[00]m"
    const val WEIGHT = "[009].[0]kg"
    const val LIQUID = "[09].[0]l"
    const val MONEY = "[999][.][999][.][999][.][999][,][99]"
}

/**
 * Extension to apply mask on a EditText.
 *
 * @param format - MaskFormat to be applied.
 * @param withHint - Boolean to show/hide Hint.
 *
 *  Check if the InputType is Numeric to apply a workaround with DigitsKey, avoiding Exception.
 */

fun EditText.mask(format: String) {
    val listener = MaskedTextChangedListener(format, this, null)

    if(this.inputType == InputType.TYPE_CLASS_NUMBER) {
        this.keyListener = DigitsKeyListener.getInstance("0123456789 -,.+()[]{}:lmkg")
    }

    this.addTextChangedListener(listener)
    this.onFocusChangeListener = listener
}

fun EditText.mask(format: String, affineFormats: List<String> = listOf()) {
    val listener = MaskedTextChangedListener(format, affineFormats,  this, null)

    if(this.inputType == InputType.TYPE_CLASS_NUMBER ) {
        this.keyListener = DigitsKeyListener.getInstance("0123456789 -.+()[]{}:lmkg")
    }

    this.addTextChangedListener(listener)
    this.onFocusChangeListener = listener
}