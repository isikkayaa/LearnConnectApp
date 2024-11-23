package com.example.learnconnectapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.learnconnectapp.R
import com.example.learnconnectapp.databinding.FragmentLoginBinding
import com.example.learnconnectapp.ui.viewmodel.UsersViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val usersViewModel: UsersViewModel by viewModels()
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.buttonLogin.setOnClickListener {
            val email = binding.emailLogin.text.toString()
            val pass = binding.emailPassword.text.toString()
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            navigateToHomePage()
                        } else {
                            Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(requireContext(), "Empty Fields Are Not Allowed!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonRegister.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.signupaGecis)
        }

        return binding.root
    }

    private fun navigateToHomePage() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homePageFragment)
        }
    }
}
