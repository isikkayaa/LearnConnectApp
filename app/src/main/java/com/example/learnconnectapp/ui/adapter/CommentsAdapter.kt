package com.example.learnconnectapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.learnconnectapp.R
import com.example.learnconnectapp.data.entity.Comments
import com.example.learnconnectapp.data.entity.ImageLinks
import com.example.learnconnectapp.data.entity.Kurslar
import com.example.learnconnectapp.databinding.CardCommentsTasarimBinding
import com.example.learnconnectapp.ui.fragment.ProfileFragmentDirections
import com.example.learnconnectapp.ui.viewmodel.ProfileViewModel
import com.example.learnconnectapp.util.gecisYap

class CommentsAdapter(var mContext: Context, var yorumListesi : List<Comments>, var yorumlananKitaplarListesi: List<Comments>, var comments:List<Comments>, var viewModel: ProfileViewModel) : RecyclerView.Adapter<CommentsAdapter.CardCommentsTasarimTutucu>() {


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
        val isRemoved = yorumlananKitaplarListesi.any {it.courseTitle == yorum.courseTitle}


        //  holder.tasarim.gorsel = yorum.imageLinks?????

        holder.tasarim.textView16.text = yorum.courseTitle

        holder.tasarim.textView17.text = yorum.userComment

        t.ratingBar.rating = yorum.rating


        if (yorum.courseImageUrl != null) {
            /*Glide.with(mContext)
                .load(yorum.bookImageUrl)
                .placeholder(R.drawable.baseline_menu_book_24)
                .error(R.drawable.baseline_list_24)
                .into(holder.tasarim.imageView2)

             */
        } else {
            holder.tasarim.imageView2.setImageResource(R.drawable.baseline_menu_24)
        }

        t.cardViewComments.setOnClickListener {
            val imageLinks = ImageLinks(
                thumbnail = yorum.courseImageUrl?.thumbnail.toString(),
                smallThumbnail = null
            )


            val kurslar = Kurslar(
                kurs_isim = yorum.courseTitle,
                kurs_gorsel = yorum.courseImageUrl,
                kurs_id = yorum.comment_id)



                val gecis = ProfileFragmentDirections.actionProfileFragment2ToCourseDetailFragment(
                kurs = kurslar,
                gorsel = imageLinks
            )

            if (gecis != null) {
                Navigation.gecisYap(it, gecis)
            }
        }

   /*     t.imageView8.setOnClickListener {
            viewModel.removeFromComments(yorum.bookTitle) { success ->
                if (success) {
                    yorumListesi = yorumListesi.filter { it.bookTitle != yorum.bookTitle }
                    Log.d("CommentsAdapter", "List updated. Remaining items: ${yorumListesi.size}")
                    notifyDataSetChanged()  // Güncellemeyi bildir
                    Snackbar.make(it, mContext.getString(R.string.remove_from_comments_success, yorum.bookTitle), Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(it, mContext.getString(R.string.remove_from_comments_fail), Snackbar.LENGTH_SHORT).show()
                }
            }
        }



    */

    }


    override fun getItemCount(): Int {
        return yorumListesi.size
    }




    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newComments: List<Comments>?) {
        yorumListesi = newComments ?: emptyList()
        notifyDataSetChanged()
    }


    fun updateBooks(newBooks: List<Comments>) {
        val oldList = yorumListesi
        yorumListesi = newBooks
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize() = oldList.size
            override fun getNewListSize() = newBooks.size
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition].courseTitle == newBooks[newItemPosition].courseTitle
            }
            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newBooks[newItemPosition]
            }
        }).dispatchUpdatesTo(this)
    }



    @SuppressLint("NotifyDataSetChanged")
    fun commentBooks(yorumlananKitaplar : List<Comments>) {
        yorumlananKitaplarListesi = yorumlananKitaplar
        notifyDataSetChanged()
    }






}