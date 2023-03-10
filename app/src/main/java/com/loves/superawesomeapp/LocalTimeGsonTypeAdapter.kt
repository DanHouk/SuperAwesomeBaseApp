package com.loves.superawesomeapp

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class LocalTimeGsonTypeAdapter : JsonDeserializer<LocalTime> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalTime {
        val formatter = DateTimeFormatter.ofPattern("h:mm:ss a")
        return LocalTime.parse(json?.asString, formatter)
    }
}