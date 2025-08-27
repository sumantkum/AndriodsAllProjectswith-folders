package com.example.unit3app

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class DateTimePicker : AppCompatActivity() {
    private lateinit var dateButton: Button
    private lateinit var timeButton: Button
    private lateinit var resultTextView: TextView

    private var selectedDate = ""
    private var selectedTime = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_time_picker)

        dateButton = findViewById(R.id.date_pick)
        timeButton = findViewById(R.id.time_pick)
        resultTextView = findViewById(R.id.resultTextView)

        dateButton.setOnClickListener {
            showDatePicker()
        }

        timeButton.setOnClickListener {
            showTimePicker()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(this, { _, y, m, d ->
            selectedDate = "$d/${m + 1}/$y"
            updateResult()
        }, year, month, day)

        datePicker.show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePicker = TimePickerDialog(this, { _, h, m ->
            selectedTime = String.format("%02d:%02d", h, m)
            updateResult()
        }, hour, minute, true)

        timePicker.show()
    }

    private fun updateResult() {
        resultTextView.text = "Selected Date: $selectedDate\nSelected Time: $selectedTime"
    }
}
