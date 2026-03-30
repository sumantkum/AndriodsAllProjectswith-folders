package com.example.sem8realtimedatabase

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var entId: EditText
    private lateinit var entName: EditText
    private lateinit var entEmail: EditText
    private lateinit var entPhone: EditText
    private lateinit var entAddress: EditText
    private lateinit var btnRead: Button
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    private lateinit var btnCreate: Button

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize Firebase
        database = FirebaseDatabase.getInstance().reference.child("Users")

        entId = findViewById(R.id.etUserId)
        entName = findViewById(R.id.etUserName)
        entEmail = findViewById(R.id.etUserEmail)
        entPhone = findViewById(R.id.etUserPhone)
        entAddress = findViewById(R.id.etUserAddress)

        btnRead = findViewById(R.id.btnRead)
        btnCreate = findViewById(R.id.btnCreate)
        btnDelete = findViewById(R.id.btnDelete)
        btnUpdate = findViewById(R.id.btnUpdate)

        // CREATE
        btnCreate.setOnClickListener { createUser() }

        // READ
        btnRead.setOnClickListener { readUser() }

        // UPDATE
        btnUpdate.setOnClickListener { updateUser() }

        // DELETE
        btnDelete.setOnClickListener { deleteUser() }
    }

    private fun createUser() {
        val id = entId.text.toString()
        val user = User(
            id = id.toInt(),
            name = entName.text.toString(),
            email = entEmail.text.toString(),
            mobile = entPhone.text.toString(),
            address = entAddress.text.toString()
        )

        database.child(id).setValue(user)
            .addOnSuccessListener {
                Toast.makeText(this, "User Created", Toast.LENGTH_SHORT).show()
                clearFields()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to Create", Toast.LENGTH_SHORT).show()
            }
    }

    private fun readUser() {
        val id = entId.text.toString()

        database.child(id).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = snapshot.getValue(User::class.java)
                    entName.setText(user?.name)
                    entEmail.setText(user?.email)
                    entPhone.setText(user?.mobile)
                    entAddress.setText(user?.address)
                    Toast.makeText(this@MainActivity, "User Found", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "User Not Found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateUser() {
        val id = entId.text.toString()

        val updates = mapOf(
            "name" to entName.text.toString(),
            "email" to entEmail.text.toString(),
            "mobile" to entPhone.text.toString(),
            "address" to entAddress.text.toString()
        )

        database.child(id).updateChildren(updates)
            .addOnSuccessListener {
                Toast.makeText(this, "User Updated", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show()
            }
    }

    private fun deleteUser() {
        val id = entId.text.toString()

        database.child(id).removeValue()
            .addOnSuccessListener {
                Toast.makeText(this, "User Deleted", Toast.LENGTH_SHORT).show()
                clearFields()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Delete Failed", Toast.LENGTH_SHORT).show()
            }
    }

    private fun clearFields() {
        entId.text.clear()
        entName.text.clear()
        entEmail.text.clear()
        entPhone.text.clear()
        entAddress.text.clear()
    }
}
