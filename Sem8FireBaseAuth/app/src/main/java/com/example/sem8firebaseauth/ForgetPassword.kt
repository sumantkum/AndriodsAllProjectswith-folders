package com.example.sem8firebaseauth

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class ForgetPassword : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var resetBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forget_password)


        auth = FirebaseAuth.getInstance()
        email = findViewById(R.id.email_reset)
        resetBtn = findViewById(R.id.reset_btn)

        resetBtn.setOnClickListener {
            val email = email.text.toString().trim()
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        Toast.makeText(this, "Password reset email sent", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }

    }

}