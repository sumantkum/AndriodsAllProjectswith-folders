package com.example.examcode

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class Loginpage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ IMPORTANT
        setContentView(R.layout.activity_loginpage)

        auth = FirebaseAuth.getInstance()

        val email = findViewById<TextInputEditText>(R.id.email)
        val password = findViewById<TextInputEditText>(R.id.password)
        val loginBtn = findViewById<Button>(R.id.loginBtn)

        loginBtn.setOnClickListener {

            val userEmail = email.text.toString().trim()
            val userPass = password.text.toString().trim()

            // ✅ Validation
            if (userEmail.isEmpty()) {
                email.error = "Enter email"
                return@setOnClickListener
            }

            if (userPass.isEmpty()) {
                password.error = "Enter password"
                return@setOnClickListener
            }

            // 🔥 Firebase Login
            auth.signInWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()

                        // 👉 Move to Home Screen
                         startActivity(Intent(this, MainActivity::class.java))
                        finish()


                    } else {
                        Toast.makeText(
                            this,
                            "Login Failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}