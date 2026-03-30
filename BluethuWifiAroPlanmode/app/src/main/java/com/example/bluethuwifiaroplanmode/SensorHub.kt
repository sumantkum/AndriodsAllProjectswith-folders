package com.example.bluethuwifiaroplanmode

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SensorHub : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var stepSensor: Sensor? = null

    private lateinit var stepText: TextView
    private lateinit var resetBtn: Button

    private var initialStepCount = 0f
    private var isFirstReading = true
    private var currentSteps = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sensor_hub)

        // Edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding( systemBars.left,  systemBars.top, systemBars.right, systemBars.bottom )
            insets
        }


        // Initialize views
        stepText = findViewById(R.id.stepText)
        resetBtn = findViewById(R.id.resetBtn)

        // Sensor setup
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        // Reset button
        resetBtn.setOnClickListener {
            resetSteps()
        }
    }

    private fun resetSteps() {
        isFirstReading = true
        initialStepCount = 0f
        currentSteps = 0
        stepText.text = "Steps: 0"
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

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (isFirstReading) {
                initialStepCount = it.values[0]
                isFirstReading = false
            }

            currentSteps = (it.values[0] - initialStepCount).toInt()

            if (currentSteps < 0) currentSteps = 0

            stepText.text = "Steps: $currentSteps"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}