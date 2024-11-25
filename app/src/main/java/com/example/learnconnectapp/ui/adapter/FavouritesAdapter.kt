package com.example.learnconnectapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.learnconnectapp.R
import com.example.learnconnectapp.data.entity.FavoriKurslar
import com.example.learnconnectapp.data.entity.Kurslar
import com.example.learnconnectapp.databinding.CardFavTasarimBinding
import com.example.learnconnectapp.ui.fragment.FavouritesFragmentDirections
import com.example.learnconnectapp.ui.viewmodel.HomePageViewModel
import com.example.learnconnectapp.util.gecisYap

class FavouritesAdapter (var mContext: Context, var favKurslarListesi : List<FavoriKurslar>, var viewModel : HomePageViewModel) : RecyclerView.Adapter<FavouritesAdapter.CardTasarimTutucuFav>(){

    inner class CardTasarimTutucuFav(var tasarim: CardFavTasarimBinding) : RecyclerView.ViewHolder(tasarim.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimTutucuFav {
        val binding: CardFavTasarimBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            R.layout.card_fav_tasarim,parent,false)
        return CardTasarimTutucuFav(binding)
    }




    @SuppressLint("StringFormatInvalid")
    override fun onBindViewHolder(holder: CardTasarimTutucuFav, position: Int) {
        val favKurs = favKurslarListesi[position]
        val t = holder.tasarim


        t.favKursNesnesi = favKurs

        val isRemoved = favKurslarListesi.any {it.fav_kurs_isim == favKurs.fav_kurs_isim}

        if(isRemoved) {
            t.imageViewfavkitapheart.setImageResource(R.drawable.baseline_favorite_24)

        }else{

            t.imageViewfavkitapheart.setImageResource(R.drawable.baseline_favorite_border_24)
        }

        holder.tasarim.textViewfavKitapAd.text = favKurs.fav_kurs_isim

        val thumbnailUrl = favKurs.imageLinks?.thumbnail
        if(thumbnailUrl != null){
            /* Glide.with(holder.itemView.context)
                .load(thumbnailUrl)
                .into(holder.tasarim.imageViewfavKitap)

             */
        }else {
            holder.tasarim.imageViewfavKitap.setImageResource(R.drawable.baseline_menu_24)

        }


        t.cardViewFav.setOnClickListener {

            val kurslar = Kurslar(
                kurs_isim = favKurs.fav_kurs_isim,
                kurs_gorsel = favKurs.imageLinks,
                kurs_id = favKurs.fav_kurs_id

            )
            val gecis = favKurs.imageLinks?.let { it1 ->
                FavouritesFragmentDirections.actionFavouritesFragment2ToCourseDetailFragment(
                    kurs = kurslar,
                    gorsel = it1
                )
            }
            if (gecis != null) {
                Navigation.gecisYap(it,gecis)
            }


        }



      /*  t.imageViewfavkitapheart.setOnClickListener {
            val context = t.imageViewfavkitapheart.context
            if (isRemoved) {
                viewModel.removeFromFavorites(favKitap.title) { success ->
                    if (success) {
                        t.imageViewfavkitapheart.setImageResource(R.drawable.baseline_favorite_border_24)
                        Snackbar.make(it, context.getString(R.string.remove_from_favorites_success, favKitap.title), Snackbar.LENGTH_SHORT).show()
                    } else {
                        Snackbar.make(it, context.getString(R.string.remove_from_favorites_fail), Snackbar.LENGTH_SHORT).show()
                    }
                }
            } else {
                /*
                viewModel.addBookToFavorites(favKitap) { success ->
                    if (success) {
                        t.imageViewfavkitapheart.setImageResource(R.drawable.baseline_favorite_24)
                        Snackbar.make(it, getString(R.string.add_to_favorites_success, favKitap.title), Snackbar.LENGTH_SHORT).show()
                    } else {
                        Snackbar.make(it, getString(R.string.add_to_favorites_fail), Snackbar.LENGTH_SHORT).show()
                    }
                }

                 */
            }
        }

       */
    }
    override fun getItemCount(): Int {
        return favKurslarListesi.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateFavorites(newFavorites: List<FavoriKurslar>?) {
        if (newFavorites != null) {
            favKurslarListesi = newFavorites
            notifyDataSetChanged()

        }
    }

}