package com.example.motionsensoreinandroid

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class EnviornmentSensor : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager

    private var temperatureSensor: Sensor? = null
    private var lightSensor: Sensor? = null
    private var pressureSensor: Sensor? = null
    private var humiditySensor: Sensor? = null

    private lateinit var tvTemperature: TextView
    private lateinit var tvLight: TextView
    private lateinit var tvPressure: TextView
    private lateinit var tvHumidity: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enviornment_sensor)

        // Initialize TextViews
        tvTemperature = findViewById(R.id.tvTemperature)
        tvLight = findViewById(R.id.tvLight)
        tvPressure = findViewById(R.id.tvPressure)
        tvHumidity = findViewById(R.id.tvHumidity)

        // Get SensorManager
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        // Get Environment Sensors
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        humiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)
    }

    override fun onResume() {
        super.onResume()
        temperatureSensor?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
        lightSensor?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
        pressureSensor?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
        humiditySensor?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            when (event.sensor.type) {
                Sensor.TYPE_AMBIENT_TEMPERATURE -> {
                    tvTemperature.text = "Temperature: ${event.values[0]} °C"
                }
                Sensor.TYPE_LIGHT -> {
                    tvLight.text = "Light: ${event.values[0]} lx"
                }
                Sensor.TYPE_PRESSURE -> {
                    tvPressure.text = "Pressure: ${event.values[0]} hPa"
                }
                Sensor.TYPE_RELATIVE_HUMIDITY -> {
                    tvHumidity.text = "Humidity: ${event.values[0]} %"
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not used
    }
}
