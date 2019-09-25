package com.kennygunderman.ombrello.util

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.*
import java.util.concurrent.TimeUnit

class DateDeserializer : JsonDeserializer<Date> {
    override fun deserialize(json: JsonElement?, typeOfT: Type, context: JsonDeserializationContext) =
        if (json == null) null else Date(TimeUnit.SECONDS.toMillis(json.asLong))
}