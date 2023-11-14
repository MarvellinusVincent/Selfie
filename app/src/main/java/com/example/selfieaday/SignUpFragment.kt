package com.example.selfieaday

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth


class SignUpFragment : Fragment() {
    private val TAG = "SignUpFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        val auth = FirebaseAuth.getInstance()
        val btnLogin = view.findViewById<Button>(R.id.btnSignUp)
        btnLogin.setOnClickListener {
            btnLogin.isEnabled = false
            val etEmail = view.findViewById<TextView>(R.id.signUpEmail)
            val etPassword = view.findViewById<TextView>(R.id.signUpPassword)
            val verifyPassword = view.findViewById<TextView>(R.id.verifyPassword)
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = verifyPassword.text.toString()
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this.context, "Email/password cannot be empty", Toast.LENGTH_SHORT).show()
                btnLogin.isEnabled = true
                return@setOnClickListener
            }
            if (password != confirmPassword) {
                Toast.makeText(this.context, "Passwords dont match", Toast.LENGTH_SHORT).show()
                btnLogin.isEnabled = true
                return@setOnClickListener
            }
            // Firebase authentication check
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    btnLogin.isEnabled = true
//                    goToSignInScreen()
                } else {
                    Toast.makeText(this.context, "Unsuccessful", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return view
    }

//    private fun goToSignInScreen() {
//        this.findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
//    }

}