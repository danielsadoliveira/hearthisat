package br.com.hearthisat.extensions

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.json.JSONObject
import java.lang.reflect.Type

fun Any?.toJson() : String {
    return Gson().toJson(this)
}

fun Any?.toJsonObject() : JsonObject  {
    return JsonParser().parse(this.toJson()).asJsonObject
}

fun Any?.toJsonArray() : JsonArray {
    return JsonParser().parse(this.toJson()).asJsonArray
}

fun <T> String.objectFromJson(type: Class<T>) : T {
    return Gson().fromJson(this, type)
}

fun <T> CharSequence.objectFromJson(type: Class<T>) : T {
    return Gson().fromJson(this.toString(), type)
}

/**
 * @type = TypeToken<List<T>>(){}.type
 * */
fun <T> String.listFromJson(type: Type): List<T>? {
    return Gson().fromJson(this, type)
}


fun JSONObject.toMapString() : Map<String, String>{
    val map = HashMap<String, String>()
    try {
        val keys = this.keys()

        while (keys.hasNext()) {
            val key = keys.next() as String
            val value = this.getString(key)
            map[key] = value
        }
    } catch (e: Exception) {
        error { "ERROR TRANSFORM JSON TO MAP" }
    }
    return map
}
