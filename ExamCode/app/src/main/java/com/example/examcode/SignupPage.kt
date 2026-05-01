package com.example.examcode

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import com.google.android.material.textfield.TextInputEditText

class SignupPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_page)

        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().reference

        val name = findViewById<TextInputEditText>(R.id.name)
        val email = findViewById<TextInputEditText>(R.id.email)
        val password = findViewById<TextInputEditText>(R.id.password)
        val signupBtn = findViewById<Button>(R.id.signupBtn)

        signupBtn.setOnClickListener {

            val userName = name.text.toString().trim()
            val userEmail = email.text.toString().trim()
            val userPass = password.text.toString().trim()

            // ✅ Validation
            if (userName.isEmpty()) {
                name.error = "Enter name"
                return@setOnClickListener
            }

            if (userEmail.isEmpty()) {
                email.error = "Enter email"
                return@setOnClickListener
            }

            if (userPass.length < 6) {
                password.error = "Password must be 6+ characters"
                return@setOnClickListener
            }

            // 🔥 Firebase Signup
            auth.createUserWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {

                        val userId = auth.currentUser?.uid ?: return@addOnCompleteListener

                        val userMap = mapOf(
                            "name" to userName,
                            "email" to userEmail
                        )

                        db.child("Users").child(userId).setValue(userMap)

                        Toast.makeText(this, "Signup Success", Toast.LENGTH_SHORT).show()

                        startActivity(Intent(this, Loginpage::class.java))


                    } else {
                        Toast.makeText(
                            this,
                            "Error: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}