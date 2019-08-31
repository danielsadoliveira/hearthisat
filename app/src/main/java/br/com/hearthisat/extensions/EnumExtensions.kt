package br.com.hearthisat.extensions

import com.google.gson.JsonObject
import org.json.JSONObject
import kotlin.reflect.KClass

fun <T : Enum<*>> KClass<T>.getBy(name: String?) : T? {
    if (name != null) {
        for (value in this.java.enumConstants)  {
            if (value.name == name) {
                return value
            }
        }
    }

    return null
}

fun <T : Enum<*>> T.compile(extras : Map<String, Any>? = null) : String {
    val json = JsonObject().apply {
        addProperty("enum_compiled_name", this@compile.name)

        extras.caseNotNull { ex ->
            for ((key, value) in ex) {
                when (value) {
                    is Boolean -> addProperty(key, value)
                    is Char -> addProperty(key, value)
                    is Number -> addProperty(key, value)
                    is String -> addProperty(key, value)
                }
            }
        }
    }

    return json.toString()
}

fun <T : Enum<*>> KClass<T>.decompile(json: String?) : Pair<T, MutableMap<String, Any>?>? {
    if (json != null && json.isJson()) {
        val map = HashMap<String, Any>()
        val jsonObject = JSONObject(json)
        var enum : T? = null

        for (value in this.java.enumConstants) {
            if (value.name == jsonObject["enum_compiled_name"]){
                jsonObject.remove("enum_compiled_name")
                enum = value
                break
            }
        }

        return if (enum != null) {
            val keys = jsonObject.keys()

            while (keys.hasNext()) {
                val key = keys.next() as String
                map[key] = jsonObject[key]
            }

            Pair(first = enum, second = map)
        } else {
            null
        }
    } else {
        return null
    }
}