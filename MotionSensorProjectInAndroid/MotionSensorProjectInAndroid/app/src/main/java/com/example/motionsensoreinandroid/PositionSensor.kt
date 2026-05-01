package com.example.motionsensoreinandroid

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs

class PositionSensor : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var rotationVector: Sensor? = null

    private lateinit var tvPosition: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_position_sensor)
        tvPosition = findViewById(R.id.tvPosition)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        rotationVector = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
    }

    override fun onResume() {
        super.onResume()
        rotationVector?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ROTATION_VECTOR) {
            val rotationMatrix = FloatArray(9)
            val orientationAngles = FloatArray(3)

            // Convert rotation vector to rotation matrix
            SensorManager.getRotationMatrixFromVector(rotationMatrix,
                event.values)

            // Get orientation: azimuth (z), pitch (x), roll (y)
            SensorManager.getOrientation(rotationMatrix, orientationAngles)

            val azimuth = orientationAngles[0] // rotation around z
            val pitch = orientationAngles[1]   // rotation around x
            val roll = orientationAngles[2]    // rotation around y

            // 👉 Fake ML classification logic
            val predicted = classifyPosition(pitch, roll, azimuth)
            tvPosition.text = "Position: $predicted\nPitch=$pitch, Roll=$roll"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    // Simple ML-like logic (could replace with TensorFlow Lite model)
    private fun classifyPosition(pitch: Float, roll: Float, azimuth: Float): String {
        return when {
            abs(pitch) < 0.3 && abs(roll) < 0.3 -> "Flat"
            pitch < -1 -> "Upright"
            pitch > 1 -> "Upside Down"
            roll > 1 -> "Tilted Right"
            roll < -1 -> "Tilted Left"
            else -> "Unknown"
        }
    }
}
