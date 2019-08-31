package br.com.hearthisat.extensions

import android.util.Base64
import java.io.File

fun File.toBase64() : String {
    val base64 = Base64.encodeToString(this.readBytes(), Base64.NO_WRAP)
    return "data:image/jpeg;base64,$base64"
}