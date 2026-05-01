    package com.example.sem8transition

    import android.content.Intent
    import android.os.Bundle
    import android.widget.Button
    import android.widget.EditText
    import android.widget.Toast
    import androidx.activity.enableEdgeToEdge
    import androidx.appcompat.app.AppCompatActivity
    import androidx.core.view.ViewCompat
    import androidx.core.view.WindowInsetsCompat
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.database.DatabaseReference
    import com.google.firebase.database.FirebaseDatabase
    class MainActivity : AppCompatActivity() {

        private lateinit var database: DatabaseReference
        private lateinit var auth: FirebaseAuth

        private lateinit var entName: EditText
        private lateinit var entEmail: EditText
        private lateinit var entPass: EditText
        private lateinit var Signup: Button

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.activity_main)


            entName = findViewById(R.id.entName)
            entEmail = findViewById(R.id.entEmail)
            entPass = findViewById(R.id.entPass)
            Signup = findViewById(R.id.btnSub)

            auth = FirebaseAuth.getInstance()
            database = FirebaseDatabase.getInstance().getReference("Users")


            Signup.setOnClickListener {

                val name = entName.text.toString().trim()
                val email = entEmail.text.toString().trim()
                val pass = entPass.text.toString().trim()

                if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(this, "Enter valid email", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (pass.length < 6) {
                    Toast.makeText(
                        this,
                        "Password must be at least 6 characters",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                auth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            val userId = auth.currentUser?.uid ?: ""
                            if (userId.isEmpty()) {
                                Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show()
                                return@addOnCompleteListener
                            }

                            val userMap = HashMap<String, String>()
                            userMap["name"] = name
                            userMap["email"] = email

                            database.child(userId).setValue(userMap)
                                .addOnCompleteListener { dbTask ->
                                    if (dbTask.isSuccessful) {
                                        Toast.makeText(this, "Signup Successfully", Toast.LENGTH_LONG).show()
                                        val intent = Intent(this, HomePage::class.java)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        Toast.makeText(
                                            this,
                                            "Database Error: ${dbTask.exception?.message}",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }

                        } else {
                            Toast.makeText(
                                this,
                                "Error: ${task.exception?.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }

        }
    }
