package com.example.learnconnectapp.data.datasource

import com.example.learnconnectapp.data.entity.CommentsSimple
import com.example.learnconnectapp.data.entity.DownloadKurslarSimple
import com.example.learnconnectapp.data.entity.FavoriKurslar
import com.example.learnconnectapp.data.entity.FavoriKurslarSimple
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



    suspend fun kisifavKursYukle(favKursId: Int) : List<FavoriKurslar> =
        withContext(Dispatchers.IO) {
            return@withContext kdao.kisiFavKursYukle(favKursId)
        }

    suspend fun kisiKursIndirmeYukle(kisiId: Int) : List<DownloadKurslarSimple> =
        withContext(Dispatchers.IO) {
            return@withContext kdao.kisiKursIndirmeYukle(kisiId)
        }

    suspend fun kisiKursYorum(kisiId: Int) : List<CommentsSimple> =
        withContext(Dispatchers.IO) {
            return@withContext kdao.kisiKursYorum(kisiId)
        }




}