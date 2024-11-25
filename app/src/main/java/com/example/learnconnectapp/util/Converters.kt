package com.example.learnconnectapp.util

import androidx.room.TypeConverter
import com.example.learnconnectapp.data.entity.ImageLinks
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun fromImageLinks(imageLinks: ImageLinks?): String? {
        return Gson().toJson(imageLinks)
    }

    @TypeConverter
    fun toImageLinks(imageLinksString: String?): ImageLinks? {
        val type = object : TypeToken<ImageLinks>() {}.type
        return Gson().fromJson(imageLinksString, type)
    }
}