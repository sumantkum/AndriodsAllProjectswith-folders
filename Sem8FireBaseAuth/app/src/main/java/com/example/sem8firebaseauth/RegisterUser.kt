package com.example.sem8firebaseauth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.security.MessageDigest

class RegisterUser : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var registerBtn: Button
    private lateinit var sign: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        name = findViewById(R.id.name_reg)
        email = findViewById(R.id.email_reg)
        password = findViewById(R.id.pass_reg)
        registerBtn = findViewById(R.id.reg_user)
        sign = findViewById(R.id.sign_in)

        registerBtn.setOnClickListener { registerUser() }

        sign.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun registerUser() {
        val regName = name.text.toString().trim()
        val regEmail = email.text.toString().trim()
        val regPass = password.text.toString().trim()

        // Input Validation
        when {
            regName.isEmpty() || regEmail.isEmpty() || regPass.isEmpty() -> {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show()
                return
            }
            !Patterns.EMAIL_ADDRESS.matcher(regEmail).matches() -> {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
                return
            }
            regPass.length < 6 -> {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return
            }
        }

        // Create FirebaseAuth user
        auth.createUserWithEmailAndPassword(regEmail, regPass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val uid = auth.currentUser!!.uid
                    val passwordHash = hashPassword(regPass)

                    val userData = mapOf(
                        "name" to regName,
                        "email" to regEmail,
                        "password_hash" to passwordHash
                    )

                    // Save user data in Realtime Database
                    val ref = database.reference.child("users").child(uid)
                    ref.setValue(userData)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Registered Successfully", Toast.LENGTH_LONG).show()

                            val intent = Intent(this, MainActivity::class.java)

                            startActivity(intent)
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Database Error: ${it.message}", Toast.LENGTH_LONG).show()
                        }

                } else {
                    Toast.makeText(
                        this,
                        task.exception?.message ?: "Registration Failed",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    // HASH FUNCTION

    private fun hashPassword(password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(password.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }

}
