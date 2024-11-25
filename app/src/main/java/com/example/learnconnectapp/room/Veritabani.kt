package com.example.learnconnectapp.room

import android.content.Context
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


@Database(entities = [Kisiler::class,Kurslar::class,Comments::class,CurrentlyCourseList::class,DownloadKurslar::class,FavoriKurslar::class,Video::class,VideoProgress::class], version = 6)
@TypeConverters(Converters::class)
abstract class Veritabani : RoomDatabase(){

    abstract fun kisilerDao() : KisilerDao
    abstract fun kurslarDao() : KurslarDao
    abstract fun videoDao() : VideoDao
    abstract fun commentsDao() : CommentsDao
    abstract fun videoProgressDao() : VideoProgressDao
    abstract fun downloadDao() : DownloadDao


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
                        val dummyKurslar = listOf(
                            Kurslar(kurs_id = 1, kurs_isim = "Kotlin ile Android Programlama", kurs_gorsel = ImageLinks("small_thumbnail_url", "thumbnail_url")),
                            Kurslar(kurs_id = 2, kurs_isim = "Swift ile iOS Programlama", kurs_gorsel = ImageLinks("small_thumbnail_url", "thumbnail_url"))
                        )

                        val dummyComments = listOf(
                            Comments(comment_id = 1, courseTitle = "Kotlin ile Android Programlama", userComment = "Harika bir kurs!", courseImageUrl = ImageLinks("small_thumbnail_url", "thumbnail_url"), rating = 5),
                            Comments(comment_id = 2, courseTitle = "Swift ile iOS Programlama", userComment = "Çok öğretici!", courseImageUrl = ImageLinks("small_thumbnail_url", "thumbnail_url"), rating = 4)
                        )

                        val dummyVideos = listOf(
                            Video(courseId = 1, videoId = 1, videoTitle = "Kotlin Giriş", videoUrl = "https://www.example.com/videos/kotlin_giris.mp4"),
                            Video(courseId = 1, videoId = 2, videoTitle = "Kotlin İleri Seviye", videoUrl = "https://www.example.com/videos/kotlin_ileri.mp4"),
                            Video(courseId = 2, videoId = 1, videoTitle = "Swift Giriş", videoUrl = "https://www.example.com/videos/swift_giris.mp4"),
                            Video(courseId = 2, videoId = 2, videoTitle = "Swift İleri Seviye", videoUrl = "https://www.example.com/videos/swift_ileri.mp4")
                        )

                        INSTANCE?.let { database ->
                            CoroutineScope(Dispatchers.IO).launch {
                                database.kurslarDao().insertAll(dummyKurslar)
                                database.commentsDao().insertAll(dummyComments)
                                database.videoDao().insertAll(dummyVideos)
                            }
                        } }
                }).build()
                INSTANCE = instance
                instance
            }
        }


    }



}