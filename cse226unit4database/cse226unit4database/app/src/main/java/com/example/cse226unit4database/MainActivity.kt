package com.example.cse226unit4database

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.database.Cursor
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var etName: EditText
    private lateinit var etAge: EditText
    private lateinit var tvResult: TextView
    private lateinit var btnInsert: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)

        etName = findViewById(R.id.etName)
        etAge = findViewById(R.id.etAge)
        tvResult = findViewById(R.id.tvResult)
        btnInsert = findViewById(R.id.btnInsert)
        btnView = findViewById(R.id.btnView)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)

        // Insert
        btnInsert.setOnClickListener {
            val name = etName.text.toString()
            val age = etAge.text.toString().toIntOrNull()
            if (name.isNotEmpty() && age != null) {
                val success = dbHelper.insertUser(name, age)
                Toast.makeText(this, if (success) "Inserted" else "Failed", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Enter valid data", Toast.LENGTH_SHORT).show()
            }
        }

        // View
        btnView.setOnClickListener {
            val cursor: Cursor = dbHelper.getAllUsers()
            val result = StringBuilder()
            if (cursor.moveToFirst()) {
                do {
                    result.append("ID: ${cursor.getInt(0)}, ")
                    result.append("Name: ${cursor.getString(1)}, ")
                    result.append("Age: ${cursor.getInt(2)}\n")
                } while (cursor.moveToNext())
            }
            cursor.close()
            tvResult.text = if (result.isNotEmpty()) result.toString() else "No data found"
        }

        // Update
        btnUpdate.setOnClickListener {
            val name = etName.text.toString()
            val newAge = etAge.text.toString().toIntOrNull()
            if (name.isNotEmpty() && newAge != null) {
                val success = dbHelper.updateUser(name, newAge)
                Toast.makeText(this, if (success) "Updated" else "Not Found", Toast.LENGTH_SHORT).show()
            }
        }

        // Delete
        btnDelete.setOnClickListener {
            val name = etName.text.toString()
            if (name.isNotEmpty()) {
                val success = dbHelper.deleteUser(name)
                Toast.makeText(this, if (success) "Deleted" else "Not Found", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
