package com.example.learnconnectapp.data.repository

import com.example.learnconnectapp.data.datasource.KisilerDataSource
import com.example.learnconnectapp.data.entity.CommentsSimple
import com.example.learnconnectapp.data.entity.DownloadKurslarSimple
import com.example.learnconnectapp.data.entity.FavoriKurslar
import com.example.learnconnectapp.data.entity.FavoriKurslarSimple
import com.example.learnconnectapp.data.entity.Kisiler
import com.example.learnconnectapp.room.KisilerDao

class KisilerRepository(var kds : KisilerDataSource,var kdao:KisilerDao) {

    suspend fun registerUser(user: Kisiler) { kdao.kaydet(user) }
    suspend fun loginUser(email: String, password: String): Kisiler? { return kdao.getUser(email, password) }


    suspend fun kisiKurslariYukle(kisiId : Int) : List<Kisiler> = kds.kisiKurslariYukle(kisiId)


    suspend fun kisifavKursYukle(favKursId: Int) : List<FavoriKurslar> = kds.kisifavKursYukle(favKursId)
    suspend fun kisiKursIndirmeYukle(kisiId: Int) : List<DownloadKurslarSimple> = kds.kisiKursIndirmeYukle(kisiId)

    suspend fun kisiKursYorum(kisiId: Int) : List<CommentsSimple> = kds.kisiKursYorum(kisiId)



}