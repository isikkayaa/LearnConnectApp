package com.example.learnconnectapp.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.learnconnectapp.data.entity.Comments
import com.example.learnconnectapp.data.entity.CurrentlyCourseList
import com.example.learnconnectapp.data.entity.DownloadKurslar
import com.example.learnconnectapp.data.entity.FavoriKurslar
import com.example.learnconnectapp.data.entity.ImageLinks
import com.example.learnconnectapp.data.entity.Kisiler
import com.example.learnconnectapp.data.entity.Kurslar
import com.example.learnconnectapp.data.entity.Video
import com.example.learnconnectapp.data.entity.VideoProgress
import com.example.learnconnectapp.util.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [Kisiler::class, Kurslar::class, Comments::class, CurrentlyCourseList::class, DownloadKurslar::class, FavoriKurslar::class, Video::class, VideoProgress::class], version = 6, exportSchema = false  )
@TypeConverters(Converters::class)
abstract class Veritabani : RoomDatabase() {

    abstract fun kisilerDao(): KisilerDao
    abstract fun kurslarDao(): KurslarDao
    abstract fun videoDao(): VideoDao
    abstract fun commentsDao(): CommentsDao
    abstract fun videoProgressDao(): VideoProgressDao
    abstract fun downloadDao(): DownloadDao
    abstract fun currentlyCourseDao() : CurrentlyCourseDao
    companion object {
        @Volatile
        private var INSTANCE: Veritabani? = null

        fun veritabaniErisim(context: Context): Veritabani {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder( context.applicationContext,
                    Veritabani::class.java,
                    "video.sqlite"
                ).addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        INSTANCE?.let { database ->
                            CoroutineScope(Dispatchers.IO).launch {

                            }
                        } }
                }).build()
                INSTANCE = instance
                instance
            }
        }


    }



}


