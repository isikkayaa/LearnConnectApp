package com.example.learnconnectapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.learnconnectapp.data.entity.Kisiler
import com.example.learnconnectapp.data.repository.KisilerRepository
import com.example.learnconnectapp.ui.viewmodel.UsersViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class UsersViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: UsersViewModel
    private lateinit var repository: KisilerRepository
    private lateinit var firebaseAuth: FirebaseAuth

    @Before
    fun setUp() {
        repository = mock(KisilerRepository::class.java)
        firebaseAuth = mock(FirebaseAuth::class.java)
        viewModel = UsersViewModel(repository, firebaseAuth)
    }

    @Test
    fun testRegisterUser() = runTest {
        val dummyUser = Kisiler(
            kisi_id = 0,
            kisi_username = "testuser",
            kisi_email = "test@example.com",
            kisi_sifre = "password",
            kisi_kurs_isim = "",
            kisi_video_ilerleme = 0,
            kisi_fav_kurs = "",
            kisi_kurs_indirme = "",
            kisi_kurs_puan = 0,
            kisi_kurs_yorum = ""
        )

        viewModel.registerUser(dummyUser.kisi_username, dummyUser.kisi_email, dummyUser.kisi_sifre)
        verify(repository).registerUser(dummyUser)
    }

    @Test
    fun testSignInWithEmail() = runTest {
        val email = "test@example.com"
        val password = "password"
        val task: Task<*>? = mock(Task::class.java)

        if (task != null) {
            `when`(task.isSuccessful).thenReturn(true)
        }
        `when`(firebaseAuth.signInWithEmailAndPassword(email, password)).thenReturn(task as Task<AuthResult>?)

        viewModel.signInWithEmail(email, password)

        verify(firebaseAuth).signInWithEmailAndPassword(email, password)

        assertEquals(true, viewModel.loginResult.getOrAwaitValue())
    }

    @Test
    fun testSignOut() {
        viewModel.signOut()

        verify(firebaseAuth).signOut()

        assertEquals(false, viewModel.loginResult.getOrAwaitValue())
    }

    fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(value: T) {
                data = value
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }
        this.observeForever(observer)
        assertTrue(latch.await(time, timeUnit))
        return data as T
    } }


