package com.example.learnconnectapp.data.datasource

import com.example.learnconnectapp.data.entity.Kisiler
import com.example.learnconnectapp.room.KisilerDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class KisilerDataSource (var kdao:KisilerDao){

suspend fun kisiKurslariYukle(kisiId:Int) : List<Kisiler> =
    withContext(Dispatchers.IO) {
        return@withContext kdao.kisiKurslariYukle(kisiId)
    }

    suspend fun register(kisi_username:String,kisi_email:String,kisi_sifre:String){
        val yeniKisi = Kisiler(0,kisi_username,kisi_email,kisi_sifre,"",0,"","",0,"")
        kdao.kaydet(yeniKisi)


    }



  /*  suspend fun kisifavKursYukle() : List<Kisiler> =
        withContext(Dispatchers.IO) {
            return@withContext kdao.kisiFavKursYukle()
        }

    suspend fun kisiKursIndirmeYukle() : List<Kisiler> =
        withContext(Dispatchers.IO) {
            return@withContext kdao.kisiKursIndirmeYukle()
        }

    suspend fun kisiKursYorum() : List<Kisiler> =
        withContext(Dispatchers.IO) {
            return@withContext kdao.kisiKursYorum()
        }




   */



}