package br.com.hearthisat.extensions

import android.app.Activity
import android.content.Intent
import android.net.Uri
import java.io.IOException
import java.lang.reflect.Type
import java.nio.charset.Charset

fun <T> Activity.restoreObject(key: String, data: Intent? = null, type: Class<T>) : T?{
    val json = if (data != null) data.getStringExtra(key)
    else intent.extras?.getString(key)
    json.caseNull { return null }

    return json!!.objectFromJson(type)
}

/**
 * @type = TypeToken<List<T>>(){}.type
 * */
fun <T> Activity.restoreList(key: String, data: Intent? = null, type: Type): List<T>? {
    val json = if (data != null) data.getStringExtra(key)
    else intent.extras?.getString(key)

    json.caseNull { return null }

    return json!!.listFromJson(type)
}

fun Activity.restoreString(key: String) : String? {
    return intent.extras?.getString(key)
}

fun Activity.restoreBoolean(key: String) : Boolean? {
    return intent.extras?.getBoolean(key)
}

fun Activity.open(url: String) {
    val parsable = if (!url.startsWith("http://") && !url.startsWith("https://")) {
        "http://$url"
    } else {
        url
    }

    open(Uri.parse(parsable))
}

fun Activity.open(url: Uri){
    startActivity(Intent(Intent.ACTION_VIEW, url))
}


fun Activity.share(url: String){
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

fun Activity.loadJsonFromAsset(fileName: String) : String? {
    val json: String?

    try {
        val inputStream = assets.open(fileName)
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