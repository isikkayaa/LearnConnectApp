package com.example.learnconnectapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.learnconnectapp.R
import com.example.learnconnectapp.data.entity.FavoriKurslar
import com.example.learnconnectapp.data.entity.ImageLinks
import com.example.learnconnectapp.data.entity.Kurslar
import com.example.learnconnectapp.data.entity.Video
import com.example.learnconnectapp.databinding.CardTasarimBinding
import com.example.learnconnectapp.ui.fragment.HomePageFragmentDirections
import com.example.learnconnectapp.ui.viewmodel.HomePageViewModel
import com.example.learnconnectapp.util.gecisYap
import com.google.android.material.snackbar.Snackbar

class HomePageAdapter(
    var mContext: Context,
    var kurslarListesi: List<Kurslar>,
    var favoriKurslar: List<FavoriKurslar>,
    var viewModel: HomePageViewModel, var fragmentType: String
) : RecyclerView.Adapter<HomePageAdapter.CardTasarimTutucu>() {

    inner class CardTasarimTutucu(var tasarim: CardTasarimBinding) : RecyclerView.ViewHolder(tasarim.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimTutucu {
        val binding: CardTasarimBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            R.layout.card_tasarim, parent, false
        )
        return CardTasarimTutucu(binding)
    }

    override fun getItemCount(): Int {
        return kurslarListesi.size
    }

    @SuppressLint("StringFormatInvalid")
    override fun onBindViewHolder(holder: CardTasarimTutucu, position: Int) {
        val kurs = kurslarListesi[position]
        val t = holder.tasarim

        t.kursNesnesi = kurs


        val isFavourited = favoriKurslar.any { it.fav_kurs_isim == kurs.kurs_isim }


        if (isFavourited) {
            t.imageViewkalpborder.setImageResource(R.drawable.baseline_favorite_24)
        } else {
            t.imageViewkalpborder.setImageResource(R.drawable.baseline_favorite_border_24)
        }

        holder.tasarim.imageViewKitapGorsel.setImageResource(kurs.kurs_gorsel)
        holder.tasarim.kursNesnesi = kurs


        holder.tasarim.textView8.text = kurs.kurs_isim


        t.imageViewkalpborder.setOnClickListener {
            val context = t.imageViewkalpborder.context
            it.isClickable = false
            if (isFavourited) {
                viewModel.removeCourseFromFavorites(kurs.kurs_id)
                t.imageViewkalpborder.setImageResource(R.drawable.baseline_favorite_border_24)
                Snackbar.make(it, "${kurs.kurs_isim} removed from favorites.", Snackbar.LENGTH_SHORT).show()
            } else {
                viewModel.addCourseToFavorites(kurs)
                t.imageViewkalpborder.setImageResource(R.drawable.baseline_favorite_24)
                Snackbar.make(it, "${kurs.kurs_isim} added to favorites.", Snackbar.LENGTH_SHORT).show()
                updateFavorites(viewModel.getFavorites().value ?: emptyList())
            }
            it.isClickable = true


        }

        t.cardViewSatir.setOnClickListener {
            if (it.id != t.imageViewkalpborder.id) {
                when (fragmentType) {
                    "HomePageFragment" -> {
                        val gecis = HomePageFragmentDirections.detayGecis(
                            kurs = kurs
                        )
                        Navigation.gecisYap(it, gecis)
                    }
                }
            }
        }


    }

    fun updateFavorites(newFavorites: List<FavoriKurslar>) {
        favoriKurslar = newFavorites
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateKurslar(newKurslar: List<Kurslar>) {
        kurslarListesi = newKurslar
        notifyDataSetChanged()
    }}


