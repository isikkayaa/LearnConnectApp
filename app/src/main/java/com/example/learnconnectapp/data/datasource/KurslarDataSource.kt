package com.example.learnconnectapp.data.datasource

import com.example.learnconnectapp.data.entity.Kurslar
import com.example.learnconnectapp.room.KurslarDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class KurslarDataSource(var kurslarDao: KurslarDao) {

    suspend fun kurslariYukle() : List<Kurslar> =
        withContext(Dispatchers.IO) {
            return@withContext kurslarDao.kurslariYukle()
        }

    suspend fun searchCourses(query:String) : List<Kurslar> =
        withContext(Dispatchers.IO) {
        return@withContext kurslarDao.searchCourses(query)
         }

}