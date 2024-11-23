package com.example.learnconnectapp.data.entity

data class Comments(  val courseTitle: String,
                      val userComment: String,
                      val courseImageUrl: ImageLinks? = null,
                      val rating: Float,
    val comment_id : Int)
