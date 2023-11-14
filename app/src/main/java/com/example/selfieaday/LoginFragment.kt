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

/**
 * Fragment for handling user login.
 */
class LoginFragment : Fragment() {
    private val TAG = "LoginFragment"

    /**
     * Inflates the layout for this fragment and sets up the login functionality.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /** Inflate the layout for this fragment */
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val auth = FirebaseAuth.getInstance()
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)
        val btnSignUpInitial = view.findViewById<Button>(R.id.btnGoSignUp)

        /** Set up click listener for the login button */
        btnLogin.setOnClickListener {
            btnLogin.isEnabled = false
            val etEmail = view.findViewById<TextView>(R.id.etEmail)
            val etPassword = view.findViewById<TextView>(R.id.etPassword)
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            /** Check if email and password are provided */
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this.context, "Email/password cannot be empty", Toast.LENGTH_SHORT)
                    .show()
                btnLogin.isEnabled = true
                return@setOnClickListener
            }

            /** Firebase authentication check */
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                btnLogin.isEnabled = true
                if (task.isSuccessful) {
                    Toast.makeText(this.context, "Success!", Toast.LENGTH_SHORT).show()
                    goToPostsScreen()
                } else {
                    Toast.makeText(this.context, "Unsuccessful!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        /** Set up click listener for the sign-up button */
        btnSignUpInitial.setOnClickListener {
            this.findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        return view
    }

    /**
     * Navigates to the Posts screen after successful login.
     */
    private fun goToPostsScreen() {
        this.findNavController().navigate(R.id.action_loginFragment_to_postsFragment)
    }
}
