package com.example.learnconnectapp.util

import android.content.Context
import android.content.SharedPreferences
import com.example.learnconnectapp.data.entity.Comments
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SharedPreferencesHelper {

    private const val PREFS_NAME = "comments_prefs"
    private const val COMMENTS_KEY = "comments"

    fun saveComments(context: Context, comments: List<Comments>) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(comments)
        editor.putString(COMMENTS_KEY, json)
        editor.apply()
    }

    fun getComments(context: Context): List<Comments> {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = sharedPreferences.getString(COMMENTS_KEY, null)
        return if (json != null) {
            val type = object : TypeToken<List<Comments>>() {}.type
            Gson().fromJson(json, type)
        } else {
            emptyList()
        }
    }
}