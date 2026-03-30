package com.example.sem8snsor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlin.math.sqrt

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var gyroscope: Sensor? = null

    private lateinit var tvAccelValues: TextView
    private lateinit var tvGyroValues: TextView
    private lateinit var progressAccel: LinearProgressIndicator
    private lateinit var progressGyro: LinearProgressIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvAccelValues = findViewById(R.id.tvAccelValues)
        tvGyroValues = findViewById(R.id.tvGyroValues)
        progressAccel = findViewById(R.id.progressAccel)
        progressGyro = findViewById(R.id.progressGyro)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI) }
        gyroscope?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI) }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val x = it.values[0]
            val y = it.values[1]
            val z = it.values[2]

            // Calculate total magnitude: sqrt(x^2 + y^2 + z^2)
            val magnitude = sqrt((x * x + y * y + z * z).toDouble()).toInt()

            when (it.sensor.type) {
                Sensor.TYPE_ACCELEROMETER -> {
                    tvAccelValues.text = String.format("X: %.2f  Y: %.2f  Z: %.2f", x, y, z)
                    // Scale progress: Earth gravity is ~9.8, so 20 is a good "high" movement
                    progressAccel.setProgressCompat(magnitude * 5, true)
                }
                Sensor.TYPE_GYROSCOPE -> {
                    tvGyroValues.text = String.format("X: %.2f  Y: %.2f  Z: %.2f", x, y, z)
                    // Gyroscope values are smaller (rad/s), scale accordingly
                    progressGyro.setProgressCompat(magnitude * 20, true)
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}