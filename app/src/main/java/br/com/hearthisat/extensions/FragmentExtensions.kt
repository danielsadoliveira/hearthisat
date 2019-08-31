package br.com.hearthisat.extensions

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import java.lang.reflect.Type
import java.io.IOException


fun <T: Fragment> T.newInstance(extras: Map<String, Any>? = null) = this.apply {
    this.arguments = Bundle().apply {
        extras.caseNotNull { ex ->
            ex.keys.forEach { key ->
                when (val value = ex[key]) {
                    is Boolean -> putBoolean(key, value)
                    is Char -> putChar(key, value)
                    is String -> putString(key, value)
                    is Number -> putLong(key, value.toLong())
                }
            }
        }
    }
}

fun <T> Fragment.restoreObject(key: String, type: Class<T>) : T?{
    arguments.caseNotNull {
        val json = it.getString(key)
        json.caseNull { return null }

        return json!!.objectFromJson(type)
    }

    return null
}

/**
 * @type = TypeToken<List<T>>(){}.type
 * */
fun <T> Fragment.restoreList(key: String, data: Intent? = null, type: Type): List<T>? {
    val json = if (data != null) data.getStringExtra(key)
    else arguments?.getString(key)

    json.caseNull { return null }

    return json!!.listFromJson(type)
}

fun Fragment.restoreString(key: String) : String? {
    arguments.caseNotNull {
        return it.getString(key)
    }
    return null
}

fun Fragment.restoreBoolean(key: String) : Boolean? {
    arguments.caseNotNull {
        return it.getBoolean(key)
    }
    return null
}

fun Fragment.open(url: String) {
    val parsable = if (!url.startsWith("http://") && !url.startsWith("https://")) {
        "http://$url"
    } else {
        url
    }

    open(Uri.parse(parsable))
}

fun Fragment.open(url: Uri){
    startActivity(Intent(Intent.ACTION_VIEW, url))
}

fun Fragment.share(url: String){
    val shareable = if (!url.startsWith("http://") && !url.startsWith("https://")) {
        "http://$url"
    } else {
        url
    }

    val i = Intent(Intent.ACTION_SEND)
    i.type = "text/plain"
    i.putExtra(Intent.EXTRA_SUBJECT, "Compartilhar link")
    i.putExtra(Intent.EXTRA_TEXT, shareable)

    startActivity(Intent.createChooser(i, "Compartilhar link"))
}

fun Fragment.loadJsonFromAsset(fileName: String) : String? {
    val json: String?

    try {
        val inputStream = activity!!.assets.open(fileName)
        val buffer = ByteArray(inputStream.available())
        inputStream.read(buffer)
        inputStream.close()

        json = String(buffer, Charsets.UTF_8)
    } catch (ex: IOException) {
        ex.printStackTrace()
        return null
    }

    return json
}