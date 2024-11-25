package com.example.learnconnectapp.di

import android.content.Context
import androidx.room.Room
import com.example.learnconnectapp.data.datasource.KisilerDataSource
import com.example.learnconnectapp.data.datasource.KurslarDataSource
import com.example.learnconnectapp.data.entity.Kurslar
import com.example.learnconnectapp.data.repository.KisilerRepository
import com.example.learnconnectapp.data.repository.KurslarRepository
import com.example.learnconnectapp.room.CommentsDao
import com.example.learnconnectapp.room.DownloadDao
import com.example.learnconnectapp.room.KisilerDao
import com.example.learnconnectapp.room.KurslarDao
import com.example.learnconnectapp.room.Veritabani
import com.example.learnconnectapp.room.VideoDao
import com.example.learnconnectapp.room.VideoProgressDao
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideKisilerRepository(kdao: KisilerDao,kds: KisilerDataSource) : KisilerRepository{
        return KisilerRepository(kds, kdao)
    }

    @Provides
    @Singleton
    fun provideKisilerDataSource(kdao: KisilerDao) : KisilerDataSource{
        return KisilerDataSource(kdao)
    }

    @Provides
    @Singleton
    fun provideKisilerDao(db:Veritabani) : KisilerDao = db.kisilerDao()


    @Provides
    @Singleton
    fun provideKurslarDataSource(kurslarDao: KurslarDao) : KurslarDataSource{
        return KurslarDataSource(kurslarDao)
    }


    @Provides
    @Singleton
    fun provideKurslarRepository(kurslarDataSource: KurslarDataSource,kurslarDao: KurslarDao) : KurslarRepository{
        return KurslarRepository(kurslarDataSource, kurslarDao)
    }



    @Provides
    @Singleton
    fun provideKurslarDao(db: Veritabani) : KurslarDao = db.kurslarDao()


    @Provides
    @Singleton
    fun provideCommentsDao(db: Veritabani) : CommentsDao = db.commentsDao()


    @Provides
    @Singleton
    fun provideVideoDao(db: Veritabani) : VideoDao = db.videoDao()

    @Provides
    @Singleton
    fun provideDownloadDao(db: Veritabani) : DownloadDao = db.downloadDao()

    @Provides
    @Singleton
    fun provideVideoProgressDao(db: Veritabani) : VideoProgressDao = db.videoProgressDao()

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): Veritabani =
        Room.databaseBuilder(context, Veritabani::class.java, "video.sqlite")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }









}