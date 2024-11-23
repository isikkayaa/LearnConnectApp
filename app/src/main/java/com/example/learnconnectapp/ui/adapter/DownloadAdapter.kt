package com.example.learnconnectapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.learnconnectapp.R
import com.example.learnconnectapp.data.entity.DownloadKurslar
import com.example.learnconnectapp.data.entity.FavoriKurslar
import com.example.learnconnectapp.data.entity.Kurslar
import com.example.learnconnectapp.databinding.CardDownloadTasarimBinding
import com.example.learnconnectapp.databinding.CardFavTasarimBinding
import com.example.learnconnectapp.ui.fragment.FavouritesFragmentDirections
import com.example.learnconnectapp.ui.viewmodel.HomePageViewModel
import com.example.learnconnectapp.util.gecisYap

class DownloadAdapter (var mContext: Context, var downloadsListesi : List<DownloadKurslar>, var viewModel : HomePageViewModel) : RecyclerView.Adapter<DownloadAdapter.CardTasarimTutucuDownload>() {

    inner class CardTasarimTutucuDownload(var tasarim: CardDownloadTasarimBinding) :
        RecyclerView.ViewHolder(tasarim.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimTutucuDownload {
        val binding: CardDownloadTasarimBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            R.layout.card_download_tasarim, parent, false
        )
        return CardTasarimTutucuDownload(binding)
    }




    @SuppressLint("StringFormatInvalid")
    override fun onBindViewHolder(holder: CardTasarimTutucuDownload, position: Int) {
        val downloadKurs = downloadsListesi[position]
        val t = holder.tasarim


        t.downloadKursNesnesi = downloadKurs

        val isRemoved = downloadsListesi.any { it.download_kurs_isim == downloadKurs.download_kurs_isim }



        holder.tasarim.textViewfavKitapAd.text = downloadKurs.download_kurs_isim

        val thumbnailUrl = downloadKurs.imageLinks?.thumbnail
        if (thumbnailUrl != null) {
            /* Glide.with(holder.itemView.context)
                .load(thumbnailUrl)
                .into(holder.tasarim.imageViewfavKitap)

             */
        } else {
            holder.tasarim.imageViewfavKitap.setImageResource(R.drawable.baseline_menu_24)

        }


        t.cardViewDown.setOnClickListener {

            val kurslar = Kurslar(
                kurs_isim = downloadKurs.download_kurs_isim,
                kurs_gorsel = downloadKurs.imageLinks,
                kurs_id = downloadKurs.download_kurs_id

            )
            val gecis = downloadKurs.imageLinks?.let { it1 ->
                FavouritesFragmentDirections.actionFavouritesFragment2ToCourseDetailFragment(
                    kurs = kurslar,
                    gorsel = it1
                )
            }
            if (gecis != null) {
                Navigation.gecisYap(it, gecis)
            }


        }



    }

    override fun getItemCount(): Int {
        return downloadsListesi.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateDownloads(newDownloads: List<DownloadKurslar>?) {
        if (newDownloads != null) {
            downloadsListesi = newDownloads
            notifyDataSetChanged()

        }
    }

}