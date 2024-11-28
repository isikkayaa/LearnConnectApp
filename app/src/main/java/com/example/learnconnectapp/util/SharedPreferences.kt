package com.example.learnconnectapp.util

import android.content.Context
import com.example.learnconnectapp.data.entity.FavoriKurslar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferences(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("favorites_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveFavorites(favorites: List<FavoriKurslar>) {
        val json = gson.toJson(favorites)
        sharedPreferences.edit().putString("favorite_courses", json).apply()
    }

    fun getFavorites(): List<FavoriKurslar> {
        val json = sharedPreferences.getString("favorite_courses", null) ?: return emptyList()
        val type = object : TypeToken<List<FavoriKurslar>>() {}.type
        return gson.fromJson(json, type)
    }
}