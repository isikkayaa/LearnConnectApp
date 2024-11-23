package com.example.learnconnectapp.data.repository

import com.example.learnconnectapp.data.datasource.KurslarDataSource
import com.example.learnconnectapp.data.entity.Kurslar
import com.example.learnconnectapp.room.KurslarDao

class KurslarRepository(var kurslarDataSource: KurslarDataSource,var kurslarDao: KurslarDao) {


  /*  suspend fun kurslariYukle() : List<Kurslar> =  kurslarDataSource.kurslariYukle()

   */
}