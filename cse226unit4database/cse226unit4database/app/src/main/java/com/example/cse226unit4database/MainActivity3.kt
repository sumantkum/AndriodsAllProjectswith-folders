package com.example.cse226unit4database

import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity3 : AppCompatActivity() {
    private lateinit var dbHelper : DatabaseHelperKO003
    private lateinit var etName : EditText
    private lateinit var etAge :EditText
    private lateinit var tvResult : TextView
    private lateinit var btnInsert : Button
    private lateinit var btnView : Button
    private lateinit var btnUpdate : Button
    private lateinit var btnDelete : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)

dbHelper = DatabaseHelperKO003(this)
        etName = findViewById(R.id.etname)
        etAge = findViewById(R.id.etage)
        tvResult = findViewById(R.id.tvResult)
        btnInsert = findViewById(R.id.btnInsert)
        btnView = findViewById(R.id.btnView)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)

        btnInsert.setOnClickListener {
        val  name = etName.text.toString()
        val  age = etAge.text.toString().toIntOrNull()
        if (name.isNotEmpty() && age!= null){
            val success = dbHelper.insertUser(name,age)
            Toast.makeText(this,if (success) "Inserted" else "Failed",Toast.LENGTH_LONG).show()
        }
            else{
                Toast.makeText(this,"Enter valid data",Toast.LENGTH_LONG).show()
        }
        }
        btnView.setOnClickListener {
            val cursor: Cursor = dbHelper.getAllUsers()
            val result = StringBuilder()
            if (cursor.moveToFirst()) {
                do {
                    result.append("ID: ${cursor.getInt(0)},")
                    result.append("Name: ${cursor.getString(1)},")
                    result.append("Age: ${cursor.getInt(2)} \n")
                } while (cursor.moveToNext())
            }
        cursor.close()
            tvResult.text= if (result.isNotEmpty()) result.toString() else "No data found"
        }
        btnUpdate.setOnClickListener {
            val  name = etName.text.toString()
            val  newAge = etAge.text.toString().toIntOrNull()
            if (name.isNotEmpty() && newAge!= null)
            {
                val success = dbHelper.updateUser(name,newAge)
                Toast.makeText(this,if (success) "Updated" else "Failed",Toast.LENGTH_LONG).show()
            }

        }
        btnDelete.setOnClickListener {
            val  name = etName.text.toString()
            if (name.isNotEmpty()){
                val success = dbHelper.deleteUser(name)
                Toast.makeText(this,if (success) "Deleted" else "Failed",Toast.LENGTH_LONG).show()
            }
        }
    }
}