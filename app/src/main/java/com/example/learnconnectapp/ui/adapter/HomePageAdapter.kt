package com.example.learnconnectapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.learnconnectapp.R
import com.example.learnconnectapp.data.entity.FavoriKurslar
import com.example.learnconnectapp.data.entity.ImageLinks
import com.example.learnconnectapp.data.entity.Kurslar
import com.example.learnconnectapp.databinding.CardTasarimBinding
import com.example.learnconnectapp.ui.fragment.HomePageFragmentDirections
import com.example.learnconnectapp.ui.viewmodel.HomePageViewModel
import com.example.learnconnectapp.util.gecisYap
import com.google.android.material.snackbar.Snackbar

class HomePageAdapter(
    var mContext: Context,
    var kurslarListesi: List<Kurslar>,
    var favoriKurslar: List<FavoriKurslar>,
 //   var okunanKitaplarListesi: List<VolumeInfo>,
   // var okunacakKitaplarListesi: List<VolumeInfo>,
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

      /*  holder.tasarim.imageView5.setOnClickListener {


            viewModel.addBookToReadList(kitap) { success ->
                val context = t.imageView5.context
                if (success) {
                    Snackbar.make(it, context.getString(R.string.add_to_read_list_success, kitap.title), Snackbar.LENGTH_SHORT).show()
                    viewModel.fetchokunanBooks()

                    readBooks(okunanKitaplarListesi)

                }
            }


        }


        holder.tasarim.imageViewList.setOnClickListener {
            viewModel.addBookToReadingList(kitap) { success ->
                val context = t.imageViewList.context
                if (success) {
                    Snackbar.make(it, context.getString(R.string.add_to_reading_list_success, kitap.title), Snackbar.LENGTH_SHORT).show()
                    readingBooks(okunacakKitaplarListesi)


                }

            }
        }

       */

      //  holder.tasarim.gorsel = kitap.imageLinks
        holder.tasarim.kursNesnesi = kurs


        holder.tasarim.textView8.text = kurs.kurs_isim
        //holder.tasarim.textView9.text = kitap.authors?.joinToString(" , ") ?: "Unknown Author"


        val thumbnailUrl = kurs.kurs_gorsel?.thumbnail
        if (kurs.kurs_gorsel?.thumbnail.isNullOrEmpty()) {
            Log.d("ImageLinks", "ImageLinks for ${kurs.kurs_isim}: No thumbnail available.")
        } else {
            Log.d("ImageLinks", "Thumbnail URL for ${kurs.kurs_isim}: ${kurs.kurs_gorsel?.thumbnail}")
        }


      /*  if (thumbnailUrl != null && thumbnailUrl.isNotBlank()) {
            Glide.with(holder.itemView.context)
                .load(thumbnailUrl)
                .placeholder(R.drawable.baseline_menu_book_24)
                .error(R.drawable.baseline_list_24)
                .into(holder.tasarim.imageViewKitapGorsel)
        } else {
            holder.tasarim.imageViewKitapGorsel.setImageResource(R.drawable.baseline_menu_book_24)
        }




       */



        t.cardViewSatir.setOnClickListener {
            when (fragmentType) {
                "HomePageFragment" -> {
                    val gecis = HomePageFragmentDirections.detayGecis(
                        kurs = kurs,
                        gorsel = kurs.kurs_gorsel ?: ImageLinks(thumbnail = null, smallThumbnail = null)
                    )
                    Navigation.gecisYap(it, gecis)
                }
             /*   "SearchFragment" -> {
                    val geciss = SearchFragmentDirections.actionSearchFragmentToBookDetailFragment(
                        kitap = kitap,
                        gorsel = kitap.imageLinks ?: ImageLinks(thumbnail = null, smallThumbnail = null)
                    )
                    Navigation.gecisYap(it, geciss)
                }
            }

              */
        }



/*
        t.imageViewkalpborder.setOnClickListener {
            val context = t.imageViewkalpborder.context
            if (isFavourited) {
                viewModel.removeFromFavorites(kurs.kurs_isim) { success ->
                    if (success) {
                        t.imageViewkalpborder.setImageResource(R.drawable.baseline_favorite_border_24)
                        Snackbar.make(it, context.getString(R.string.remove_from_favorites_success, kurs.kurs_isim), Snackbar.LENGTH_SHORT).show()
                    } else {
                        Snackbar.make(it, context.getString(R.string.remove_from_favorites_fail), Snackbar.LENGTH_SHORT).show()
                    }
                }
            } else {
                viewModel.addBookToFavorites(kurs) { success ->
                    if (success) {
                        t.imageViewkalpborder.setImageResource(R.drawable.baseline_favorite_24)
                        Snackbar.make(it, context.getString(R.string.add_to_favorites_success, kurs.kurs_isim), Snackbar.LENGTH_SHORT).show()
                    } else {
                        Snackbar.make(it, context.getString(R.string.add_to_favorites_fail), Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


 */


    @SuppressLint("NotifyDataSetChanged")
    fun updateFavorites(newFavorites: List<FavoriKurslar>) {
        favoriKurslar = newFavorites
        notifyDataSetChanged()
    }


}}}