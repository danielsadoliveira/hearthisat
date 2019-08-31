package br.com.hearthisat.service.config.adapter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

class DateTypeAdapter(private val dateFormats : List<String>) : JsonDeserializer<Date> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Date? {
        dateFormats.forEach {
            try { return SimpleDateFormat(it, Locale.US).parse(json.asString) }
            catch (_ : Exception) {}
        }

        throw JsonParseException("Unparseable date: \"" + json.asString + "\". \n Supported formats: " + dateFormats.joinToString())
    }
}