package com.example.sem8uiux

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        val etGender = findViewById<AutoCompleteTextView>(R.id.spinnerGender)
        val etDate = findViewById<EditText>(R.id.etDate)
        val gotoLogin = findViewById<TextView>(R.id.logintext)
        val createAcc = findViewById<Button>(R.id.creatAcc)

        val genderOptions = arrayOf("Male", "Female", "Other")

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, genderOptions)
        etGender.setAdapter(adapter)

        etGender.setOnClickListener {
            etGender.showDropDown()
        }

        gotoLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            Toast.makeText(this, "Redirecting to Login Page", Toast.LENGTH_SHORT).show()
        }

        etDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog =
                DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                    // Format: DD/MM/YYYY
                    val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    etDate.setText(formattedDate)
                }, year, month, day)

            datePickerDialog.show()
        }

        createAcc.setOnClickListener {
            startActivity(Intent(this, MainActivity3::class.java))
            Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show()

        }
    }
}