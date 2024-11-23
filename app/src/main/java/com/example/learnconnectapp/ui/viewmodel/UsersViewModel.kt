package com.example.learnconnectapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnconnectapp.data.entity.Kisiler
import com.example.learnconnectapp.data.repository.KisilerRepository
import com.example.learnconnectapp.room.KisilerDao
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val repository: KisilerRepository) : ViewModel() {

    private lateinit var firebaseAuth: FirebaseAuth

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    private val _userCourses = MutableLiveData<List<Kisiler>>()
    val userCourses: LiveData<List<Kisiler>> get() = _userCourses

    fun registerUser(username: String, email: String, password: String) {
        viewModelScope.launch {
            val user = Kisiler(
                kisi_username = username,
                kisi_email = email,
                kisi_sifre = password,
                kisi_kurs_isim = "",
                kisi_video_ilerleme = 0,
                kisi_id = 0,
                kisi_fav_kurs = "",
                kisi_kurs_indirme = "",
                kisi_kurs_puan = 0,
                kisi_kurs_yorum = ""
            )
            repository.registerUser(user)
            Log.d("UsersViewModel", "User Registered: ${user.kisi_email}")
        }
    }

    fun signInWithEmail(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _loginResult.postValue(true)
                } else {
                    _loginResult.postValue(false)
                }
            }
    }

    fun signOut() {
        firebaseAuth.signOut()
        _loginResult.postValue(false)
    }

    fun loadUserCourses(kisiId: Int) {
        viewModelScope.launch {
            val courses = repository.kisiKurslariYukle(kisiId)
            _userCourses.postValue(courses)
        }
    }
}
