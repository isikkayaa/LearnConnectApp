package com.example.learnconnectapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.learnconnectapp.data.entity.Kisiler
import com.example.learnconnectapp.data.entity.Kurslar


@Database(entities = [Kisiler::class,Kurslar::class], version = 1)
abstract class Veritabani : RoomDatabase(){

    abstract fun kisilerDao() : KisilerDao
    abstract fun kurslarDao() : KurslarDao


    companion object {
        @Volatile
        private var INSTANCE: Veritabani? = null


        fun veritabaniErisim(context: Context) : Veritabani? {
            if (INSTANCE == null) {


                synchronized(Veritabani::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        Veritabani::class.java,
                        "video.sqlite")
                        .createFromAsset("video.sqlite")
                        .build()
                }
            }
            return INSTANCE
        }

    }
}