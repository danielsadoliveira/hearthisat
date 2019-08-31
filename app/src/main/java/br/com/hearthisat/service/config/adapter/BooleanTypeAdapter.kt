package br.com.hearthisat.service.config.adapter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

open class BooleanTypeAdapter : JsonDeserializer<Boolean> {
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Boolean? {
        if (json.isJsonPrimitive) {
            val primitive = json.asJsonPrimitive
            when {
                primitive.isBoolean -> {
                    return primitive.asBoolean
                }
                primitive.isNumber -> {
                    return primitive.asInt == 1
                }
                primitive.isString -> {
                    return when (primitive.asString.toLowerCase()) {
                        "yes" -> true
                        "sim" -> true
                        "1" -> true
                        "true" -> true

                        "no" -> false
                        "nao" -> false
                        "0" -> false
                        "false" -> false
                        else -> null
                    }
                }
            }
        }
        throw JsonParseException("BOOLEAN DESERIALIZE ERROR: JSON MALFORMED!")
    }
}