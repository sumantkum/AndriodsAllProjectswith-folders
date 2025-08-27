package com.example.unit3app

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class DateAndTime : AppCompatActivity() {

    private val dateAndTime = Calendar.getInstance()
    private lateinit var tuDate: TextView
    private lateinit var btnShow: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_date_and_time)

        // Link to correct IDs
        tuDate = findViewById(R.id.selectDateTime)
        btnShow = findViewById(R.id.date_pick)

        btnShow.setOnClickListener {
            showDatePicker()
        }

        updateDateInTextView() // optional to show current date
    }

    private fun showDatePicker() {
        val year = dateAndTime.get(Calendar.YEAR)
        val month = dateAndTime.get(Calendar.MONTH)
        val day = dateAndTime.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(this, { _, y, m, d ->
            dateAndTime.set(y, m, d)
            updateDateInTextView()
        }, year, month, day)

        datePicker.show()
    }

    private fun updateDateInTextView() {
        val day = dateAndTime.get(Calendar.DAY_OF_MONTH)
        val month = dateAndTime.get(Calendar.MONTH) + 1
        val year = dateAndTime.get(Calendar.YEAR)

        tuDate.text = "Selected Date: $day/$month/$year"
    }
}
