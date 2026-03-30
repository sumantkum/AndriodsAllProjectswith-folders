package com.example.bluethuwifiaroplanmode

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import java.util.concurrent.TimeUnit

class StepCounterSensorbeasd : AppCompatActivity(), SensorEventListener {

    // Sensor & Logic Variables
    private lateinit var sensorManager: SensorManager
    private var stepSensor: Sensor? = null
    private var initialStepCount = 0f
    private var isFirstReading = true
    private val stepGoal = 10000
    private var countDownTimer: CountDownTimer? = null

    // UI Elements
    private lateinit var stepText: TextView
    private lateinit var distanceText: TextView
    private lateinit var calorieText: TextView
    private lateinit var timeText: TextView
    private lateinit var stepProgress: CircularProgressIndicator
    private lateinit var etHours: EditText
    private lateinit var etMinutes: EditText
    private lateinit var etSeconds: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_step_counter_sensorbeasd)

        // 1. Initialize UI Views
        initViews()

        // 2. Permission Check (Android 10+)
        checkActivityPermission()

        // 3. Sensor Initialization
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor == null) {
            Toast.makeText(this, "Step Counter Sensor NOT available!", Toast.LENGTH_LONG).show()
        }

        // 4. Button Listeners
        findViewById<MaterialButton>(R.id.btnSetTime).setOnClickListener {
            startActivityTimer()
        }

        findViewById<MaterialButton>(R.id.resetBtn).setOnClickListener {
            resetActivity()
        }
    }

    private fun initViews() {
        stepText = findViewById(R.id.stepText)
        distanceText = findViewById(R.id.distanceText)
        calorieText = findViewById(R.id.calorieText)
        timeText = findViewById(R.id.timeText)
        stepProgress = findViewById(R.id.stepProgress)
        etHours = findViewById(R.id.etHours)
        etMinutes = findViewById(R.id.etMinutes)
        etSeconds = findViewById(R.id.etSeconds)
    }

    private fun startActivityTimer() {
        val hrs = etHours.text.toString().toLongOrNull() ?: 0
        val mins = etMinutes.text.toString().toLongOrNull() ?: 0
        val secs = etSeconds.text.toString().toLongOrNull() ?: 0

        val totalMillis = (hrs * 3600 + mins * 60 + secs) * 1000

        if (totalMillis <= 0) {
            Toast.makeText(this, "Set a valid time first", Toast.LENGTH_SHORT).show()
            return
        }

        countDownTimer?.cancel() // Stop any running timer

        countDownTimer = object : CountDownTimer(totalMillis, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val h = TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
                val m = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60
                val s = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60
                timeText.text = String.format("%02dh %02dm %02ds", h, m, s)
            }

            override fun onFinish() {
                timeText.text = "Goal Finished!"
                Toast.makeText(this@StepCounterSensorbeasd, "Activity Session Complete!", Toast.LENGTH_LONG).show()
            }
        }.start()

        Toast.makeText(this, "Timer Started", Toast.LENGTH_SHORT).show()
    }

    private fun resetActivity() {
        isFirstReading = true
        initialStepCount = 0f
        countDownTimer?.cancel()

        stepText.text = "0"
        distanceText.text = "0.00 km"
        calorieText.text = "0 kcal"
        timeText.text = "00h 00m 00s"
        stepProgress.setProgress(0, true)

        etHours.text.clear()
        etMinutes.text.clear()
        etSeconds.text.clear()

        Toast.makeText(this, "All Stats Reset ✅", Toast.LENGTH_SHORT).show()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {

            if (isFirstReading) {
                initialStepCount = it.values[0]
                isFirstReading = false
            }

            val currentSteps = (it.values[0] - initialStepCount).toInt().coerceAtLeast(0)

            // Update Steps UI
            stepText.text = currentSteps.toString()

            // Update Progress Bar
            val progress = ((currentSteps.toFloat() / stepGoal) * 100).toInt().coerceAtMost(100)
            stepProgress.setProgress(progress, true)

            // Calculations
            val distanceKm = (currentSteps * 0.75) / 1000
            val calories = currentSteps * 0.045f

            distanceText.text = String.format("%.2f km", distanceKm)
            calorieText.text = String.format("%.0f kcal", calories)

        }
    }

    private fun checkActivityPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 100)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        stepSensor?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}