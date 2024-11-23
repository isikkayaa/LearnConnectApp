package com.example.learnconnectapp.ui.viewmodel

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.learnconnectapp.data.entity.Comments
import com.example.learnconnectapp.data.entity.CurrentlyCourseList
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel  @Inject constructor(@ApplicationContext private val context: Context, private val firebaseAuth: FirebaseAuth) : ViewModel() {


    val userName = MutableLiveData<String>()



    private val sharedPreferences =
        context.getSharedPreferences("BookClubApp", Context.MODE_PRIVATE)

    private val _commentsList = MutableLiveData<List<Comments>?>()
    val commentsList: LiveData<List<Comments>?> get() = _commentsList


    private val _currentlyList = MutableLiveData<List<CurrentlyCourseList>?>()
    val currentlyList : LiveData<List<CurrentlyCourseList>?> get() = _currentlyList


    init {
    }

   /* fun loadProfileData() {
        val userId = firebaseAuth.currentUser?.uid
        userId?.let {
            firestore.collection("users").document(it).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        userName.value = document.getString("username") ?: "User"
                        booksGoal.value = document.getLong("booksGoal")?.toInt() ?: 0
                        booksRead.value = document.getLong("booksRead")?.toInt() ?: 0
                        isGoalSet.value = booksGoal.value != 0

                        val encodedImage = document.getString("profileImage")
                        profileImage.value = encodedImage?.let { decodeBase64ToBitmap(it) }
                        fetchReadBooksCount()
                    }
                }
                .addOnFailureListener {
                    Log.e("ProfileViewModel", "Error loading user data", it)
                }
        }
    }

    */



    companion object {

        @JvmStatic
        @BindingAdapter("imageUri")
        fun loadImage(view: ImageView, uri: Any?) {
            val context = view.context
          /*  val request = Glide.with(context)
                .load(uri)

           */


        }


    }



   /* fun saveComment(book: VolumeInfo, comment: String, imageUrl: String?,rating:Float) {
        val db = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        val newComment = hashMapOf(
            "bookTitle" to book.title,
            "userComment" to comment,
            "bookImageUrl" to imageUrl,
            "rating" to rating// Eğer imageUrl varsa
        )

        db.collection("users").document(userId).collection("comments")
            .document(book.title)
            .set(newComment)
            .addOnSuccessListener {
                Log.d("ProfileViewModel", "Yorum başarıyla kaydedildi.")
            }
            .addOnFailureListener { e ->
                Log.e("ProfileViewModel", "Yorum kaydedilemedi: ", e)
            }



    }


    fun fetchComments() {
        val userId = firebaseAuth.currentUser?.uid ?: return
        firestore.collection("users").document(userId).collection("comments")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w("ProfileViewModel", "Error while fetching comments.", e)
                    return@addSnapshotListener
                }

                val comments = snapshots?.documents?.map { doc ->
                    Comment(
                        bookTitle = doc.getString("bookTitle") ?: "",
                        userComment = doc.getString("userComment") ?: "",
                        bookImageUrl = doc.getString("bookImageUrl") ?: "",
                        rating = (doc.get("rating") as? Double)?.toFloat() ?: 0f
                    )
                } ?: emptyList()

                _commentsList.value = comments
            }


    }


    fun removeFromComments(title: String, onComplete: (Boolean) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        db.collection("users").document(userId).collection("comments")
            .document(title)
            .delete()
            .addOnSuccessListener {
                Log.d("ProfileViewModel", "Comment removed successfully.")
                onComplete(true)
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error removing book from comments", e)
                onComplete(false)
            }
    }


    fun addBookCurrentlyList(book: VolumeInfo,onComplete: (Boolean) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        val currentlyBook = hashMapOf(
            "title" to book.title,
            "authors" to book.authors,
            "thumbnail" to book.imageLinks?.thumbnail
        )


        db.collection("users").document(userId).collection("currentlyList")
            .document(book.title)
            .set(currentlyBook)
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error adding book to currentlylist",e)
                onComplete(false)
            }
    }



    fun fetchCurrentlyList() {
        val userId = firebaseAuth.currentUser?.uid ?: return

        firestore.collection("users").document(userId).collection("currentlyList")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w("ProfileViewModel", "Error fetching currentlyList", e)
                    return@addSnapshotListener
                }

                val currentlyList = snapshots?.documents?.map { doc ->
                    CurrentlyBookList(
                        bookTitle = doc.getString("title") ?: "",
                        bookAuthors = doc.get("authors") as? List<String>,
                        bookImageUrl = doc.getString("thumbnail")
                    )
                } ?: emptyList()

                _currentlyList.value = currentlyList
            }
    }




    */





}