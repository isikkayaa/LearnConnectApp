package com.example.learnconnectapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.learnconnectapp.R
import com.example.learnconnectapp.data.entity.Comments
import com.example.learnconnectapp.data.entity.ImageLinks
import com.example.learnconnectapp.data.entity.Kurslar
import com.example.learnconnectapp.databinding.CardCommentsTasarimBinding
import com.example.learnconnectapp.ui.fragment.ProfileFragmentDirections
import com.example.learnconnectapp.ui.viewmodel.ProfileViewModel
import com.example.learnconnectapp.util.gecisYap

class CommentsAdapter(var mContext: Context, var yorumListesi : List<Comments>, var comments:List<Comments>, var viewModel: ProfileViewModel,var kurslarListesi: List<Kurslar>) : RecyclerView.Adapter<CommentsAdapter.CardCommentsTasarimTutucu>() {

    inner class CardCommentsTasarimTutucu(var tasarim : CardCommentsTasarimBinding) : RecyclerView.ViewHolder(tasarim.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardCommentsTasarimTutucu {
        val binding : CardCommentsTasarimBinding =  DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            R.layout.card_comments_tasarim,parent,false)
        return CardCommentsTasarimTutucu(binding)
    }

    @SuppressLint("StringFormatInvalid")
    override fun onBindViewHolder(holder: CardCommentsTasarimTutucu, position: Int) {
        val yorum = yorumListesi[position]
        val t = holder.tasarim

        t.yorumNesnesi = yorum

        t.textView16.text = mContext.getString(R.string.basic)
        t.imageView2.setImageResource(R.drawable.jetpack)


        holder.tasarim.textView17.text = yorum.userComment

        t.ratingBar.rating = yorum.rating.toFloat()



        t.cardViewComments.setOnClickListener {

            val kurslar = Kurslar(
                kurs_isim = yorum.courseTitle,
                kurs_gorsel = yorum.courseImageUrl,
                kurs_id = yorum.comment_id)



                val gecis = ProfileFragmentDirections.actionProfileFragment2ToCourseDetailFragment(
                kurs = kurslar
            )

            if (gecis != null) {
                Navigation.gecisYap(it, gecis)
            }
        }


    }


    override fun getItemCount(): Int {
        return yorumListesi.size
    }




    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newComments: List<Comments>?) {
        yorumListesi = newComments ?: emptyList()
        notifyDataSetChanged()
    }


    fun updateCommentss(newCommentss: List<Comments>) {
        val oldList = yorumListesi
        yorumListesi = newCommentss
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize() = oldList.size
            override fun getNewListSize() = newCommentss.size
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition].courseTitle == newCommentss[newItemPosition].courseTitle
            }
            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newCommentss[newItemPosition]
            }
        }).dispatchUpdatesTo(this)
    }



    @SuppressLint("NotifyDataSetChanged")
    fun updateComments(newComments : List<Comments>) {
        yorumListesi = newComments
        notifyDataSetChanged()
    }






}