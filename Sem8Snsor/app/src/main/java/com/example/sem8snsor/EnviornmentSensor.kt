package com.example.sem8snsor

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class EnviornmentSensor : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager

    // Sensors
    private var temperatureSensor: Sensor? = null
    private var lightSensor: Sensor? = null
    private var pressureSensor: Sensor? = null
    private var humiditySensor: Sensor? = null

    // UI
    private lateinit var tvTemp: TextView
    private lateinit var tvLight: TextView
    private lateinit var tvPressure: TextView
    private lateinit var tvHumidity: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enviornment_sensor)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        setupUI()
        initSensors()
    }

    private fun setupUI() {

        val cardTemp = findViewById<View>(R.id.cardTemp)
        tvTemp = cardTemp.findViewById(R.id.sensorValue)

        val cardLight = findViewById<View>(R.id.cardLight)
        tvLight = cardLight.findViewById(R.id.sensorValue)

        val cardPressure = findViewById<View>(R.id.cardPressure)
        tvPressure = cardPressure.findViewById(R.id.sensorValue)

        val cardHumidity = findViewById<View>(R.id.cardHumidity)
        tvHumidity = cardHumidity.findViewById(R.id.sensorValue)
    }

    private fun initSensors() {
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        humiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)

        checkAvailability(temperatureSensor, tvTemp)
        checkAvailability(lightSensor, tvLight)
        checkAvailability(pressureSensor, tvPressure)
        checkAvailability(humiditySensor, tvHumidity)
    }

    private fun checkAvailability(sensor: Sensor?, view: TextView) {
        if (sensor == null) {
            view.text = "Not Available"
            view.setTextColor(Color.RED)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val value = String.format("%.1f", it.values[0])

            when (it.sensor.type) {
                Sensor.TYPE_AMBIENT_TEMPERATURE -> tvTemp.text = "$value °C"
                Sensor.TYPE_LIGHT -> tvLight.text = "$value lx"
                Sensor.TYPE_PRESSURE -> tvPressure.text = "$value hPa"
                Sensor.TYPE_RELATIVE_HUMIDITY -> tvHumidity.text = "$value %"
            }
        }
    }

    override fun onResume() {
        super.onResume()

        temperatureSensor?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI) }
        lightSensor?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI) }
        pressureSensor?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI) }
        humiditySensor?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI) }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
