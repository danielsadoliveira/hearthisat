package br.com.hearthisat.extensions

import android.annotation.SuppressLint
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.Patterns
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


fun String.getInitials(): String {
    val strings = this.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    var initials  = ""
    if (strings.size == 1) {
        initials = strings[0].substring(0, 2)
    } else {
        strings.asSequence()
                .filter { it.isNotEmpty() }
                .forEach {
                    if (initials.length < 2){
                        initials += it.substring(0, 1)
                    }
                }
    }
    return initials.toUpperCase()
}

fun String.isJson(): Boolean{
    try {
        JSONObject(this)
    } catch (e : JSONException) {
        return false
    }
    return true
}

fun String?.md5(): String {
    if (this != null){
        try {
            // Create MD5 Hash
            val digest = MessageDigest.getInstance("MD5")
            digest.update(this.toByteArray())
            val messageDigest = digest.digest()

            // Create Hex String
            val hexString = StringBuffer()
            for (i in messageDigest.indices) {
                var h = Integer.toHexString(0xFF and messageDigest[i].toInt())
                while (h.length < 2)
                    h = "0$h"
                hexString.append(h)
            }
            return hexString.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
    }

    return ""
}

fun String.sha256(): String {
    val bytes = this.toByteArray()
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(bytes)
    return digest.fold("", { str, it -> str + "%02x".format(it) })
}

fun String.getUrls() : ArrayList<String> {
    val array = ArrayList<String>()
    val matcher = Patterns.WEB_URL.matcher(this)

    while (matcher.find()) {
        array.add(matcher.group())
    }

    return array
}

fun String.getYoutubeId() : String {
    val pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*"
    val compiledPattern = Pattern.compile(pattern)
    val matcher = compiledPattern.matcher(this)

    return if (matcher.find()) {
        matcher.group()
    } else {
        ""
    }
}

@SuppressLint("SimpleDateFormat")
fun String.getDate(format : String) : Date? {
    return try {
        SimpleDateFormat(format).parse(this)
    } catch (e: Exception){
        null
    }
}

fun String.remove(chars : String) : String {
    var newValue = this
    chars.forEach {
        newValue = newValue.replace(it.toString(), "")
    }

    return newValue
}

fun String?.toHtml() : Spanned? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
        Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(this)
    }
}

fun String.isValidDate(format: String): Boolean {
    val dateFormat = SimpleDateFormat(format, Locale.ROOT)
    dateFormat.isLenient = false

    try {
        dateFormat.parse(this.trim())
    } catch (e: ParseException){
        return false
    }

    return true
}