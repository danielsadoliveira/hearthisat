package br.com.hearthisat.extensions

import com.google.gson.JsonObject
import org.json.JSONObject

fun Map<String, Any>.toJsonString() : String {
    val json = JsonObject().apply {
        for ((key, value) in this@toJsonString) {
            when (value) {
                is Boolean -> addProperty(key, value)
                is Char -> addProperty(key, value)
                is Number -> addProperty(key, value)
                is String -> addProperty(key, value)
            }
        }
    }

    return json.toString()
}

fun String.jsonToMap() : Map<String, Any>? {
    return if (this.isJson()) {
        val map = HashMap<String, Any>()
        val jsonObject = JSONObject(this)
        val keys = jsonObject.keys()

        while (keys.hasNext()) {
            val key = keys.next() as String
            map[key] = jsonObject[key]
        }
        map
    } else {
        null
    }
}