package com.example.learnconnectapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.learnconnectapp.R
import com.example.learnconnectapp.databinding.FragmentSignUpBinding
import com.example.learnconnectapp.ui.viewmodel.UsersViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ViewModelScoped

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: UsersViewModel by viewModels()
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.buttonSignIn.setOnClickListener {
            val username = binding.usernameSignUp.text.toString()
            val email = binding.emailSignUp.text.toString()
            val password = binding.passSignUp.text.toString()

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        viewModel.registerUser(username, email, password)
                        Navigation.findNavController(it).navigate(R.id.action_signUpFragment_to_loginFragment)
                    } else {
                        Toast.makeText(requireContext(), "Registration Failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        return binding.root
    }
}
