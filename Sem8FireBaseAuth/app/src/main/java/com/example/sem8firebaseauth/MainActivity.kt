package com.example.sem8firebaseauth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginBtn: Button
    private lateinit var signUp: TextView
    private lateinit var forgetPass: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        loginBtn = findViewById(R.id.login)
        signUp = findViewById(R.id.sign_up)
        forgetPass = findViewById(R.id.forget_password)

        loginBtn.setOnClickListener { loginUser() }

        signUp.setOnClickListener {
            startActivity(Intent(this, RegisterUser::class.java))
        }

        forgetPass.setOnClickListener {
            startActivity(Intent(this, ForgetPassword::class.java))
        }
    }

    override fun onStart() {
        super.onStart()

        // Auto-login check
        auth.currentUser?.let {
            val intent = Intent(this, HomePage::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun loginUser() {
        val emailText = email.text.toString().trim()
        val passwordText = password.text.toString().trim()

        // Input Validation
        when {
            emailText.isEmpty() || passwordText.isEmpty() -> {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show()
                return
            }
            !Patterns.EMAIL_ADDRESS.matcher(emailText).matches() -> {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
                return
            }
            passwordText.length < 6 -> {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return
            }
        }

        // FirebaseAuth login
        auth.signInWithEmailAndPassword(emailText, passwordText)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, HomePage::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this,
                        task.exception?.message ?: "Login Failed",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}
