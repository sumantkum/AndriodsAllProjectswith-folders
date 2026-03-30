package com.example.sem7unit4

import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseNameHelperK0002
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

        dbHelper = DatabaseNameHelperK0002(this)
        etName = findViewById(R.id.etName)
        etAge = findViewById(R.id.etAge)
        tvResult = findViewById(R.id.textResult)
        btnInsert = findViewById(R.id.btnInsert)
        btnView = findViewById(R.id.btnView)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)

        // INSERT
        btnInsert.setOnClickListener {
            val name = etName.text.toString().trim()
            val age = etAge.text.toString().toIntOrNull()
            if (name.isNotEmpty() && age != null) {
                val success = dbHelper.insertUser(name, age)
                Toast.makeText(this, if (success) "Inserted Successfully" else "Insert Failed", Toast.LENGTH_LONG).show()
                Log.d("DB_DEBUG", "Insert $name, $age: $success")
                clearInputs()
            } else {
                Toast.makeText(this, "Enter valid name and age", Toast.LENGTH_LONG).show()
            }
        }

        // VIEW
        btnView.setOnClickListener {
            val cursor: Cursor = dbHelper.getAllUsers()
            val result = StringBuilder()
            if (cursor.moveToFirst()) {
                do {
                    result.append("ID: ${cursor.getInt(0)}\n")
                    result.append("Name: ${cursor.getString(1)}\n")
                    result.append("Age: ${cursor.getInt(2)}\n\n")
                } while (cursor.moveToNext())
            }
            cursor.close()
            tvResult.text = if (result.isNotEmpty()) result.toString() else "No data found"
        }

        // UPDATE
        btnUpdate.setOnClickListener {
            val name = etName.text.toString().trim()
            val newAge = etAge.text.toString().toIntOrNull()
            if (name.isNotEmpty() && newAge != null) {
                val success = dbHelper.updateUser(name, newAge)
                Toast.makeText(this, if (success) "Updated Successfully" else "No data found to update", Toast.LENGTH_LONG).show()
                Log.d("DB_DEBUG", "Update $name -> $newAge: $success")
                clearInputs()
            } else {
                Toast.makeText(this, "Enter valid name and age", Toast.LENGTH_LONG).show()
            }
        }

        // DELETE
        btnDelete.setOnClickListener {
            val name = etName.text.toString().trim()
            if (name.isNotEmpty()) {
                val success = dbHelper.deleteUser(name)
                Toast.makeText(this, if (success) "Deleted Successfully" else "No data found to delete", Toast.LENGTH_LONG).show()
                Log.d("DB_DEBUG", "Delete $name: $success")
                clearInputs()
            } else {
                Toast.makeText(this, "Enter a name to delete", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun clearInputs() {
        etName.text.clear()
        etAge.text.clear()
    }
}
