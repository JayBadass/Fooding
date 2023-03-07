package com.example.fooding.data.model

import android.net.Uri
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.ToJson

class Converters {

    @ToJson
    fun toJson(value: Uri): String = value.toString()

    @FromJson
    fun fromJson(reader: JsonReader): Uri? {
        return Uri.parse(reader.nextString())
    }
}