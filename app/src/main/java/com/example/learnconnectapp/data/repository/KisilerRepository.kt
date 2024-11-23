package com.example.learnconnectapp.data.repository

import com.example.learnconnectapp.data.datasource.KisilerDataSource
import com.example.learnconnectapp.data.entity.Kisiler
import com.example.learnconnectapp.room.KisilerDao

class KisilerRepository(var kds : KisilerDataSource,var kdao:KisilerDao) {

    suspend fun registerUser(user: Kisiler) { kdao.kaydet(user) }
    suspend fun loginUser(email: String, password: String): Kisiler? { return kdao.getUser(email, password) }


    suspend fun kisiKurslariYukle(kisiId : Int) : List<Kisiler> = kds.kisiKurslariYukle(kisiId)



 /*   suspend fun kisifavKursYukle() : List<Kisiler> = kds.kisifavKursYukle()
    suspend fun kisiKursIndirmeYukle() : List<Kisiler> = kds.kisiKursIndirmeYukle()


    suspend fun kisiKursYorum() : List<Kisiler> = kds.kisiKursYorum()




  */

}