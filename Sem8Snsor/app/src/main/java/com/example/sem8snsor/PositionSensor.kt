package com.example.sem8snsor
import android.content.res.ColorStateList
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.sem8snsor.R
import com.google.android.material.card.MaterialCardView
import kotlin.math.abs

class PositionSensor : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var rotationVector: Sensor? = null

    private lateinit var tvPosition: TextView
    private lateinit var resultCard: MaterialCardView
    private lateinit var tvPitchValue: TextView
    private lateinit var tvRollValue: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_position_sensor)

        // Initialize Views
        tvPosition = findViewById(R.id.tvPosition)
        resultCard = findViewById(R.id.resultCard)

        // Finding views inside the <include> tags
        val viewPitch = findViewById<View>(R.id.statPitch)
        val viewRoll = findViewById<View>(R.id.statRoll)

        viewPitch.findViewById<TextView>(R.id.statLabel).text = "PITCH"
        viewRoll.findViewById<TextView>(R.id.statLabel).text = "ROLL"

        tvPitchValue = viewPitch.findViewById(R.id.sensorValue)
        tvRollValue = viewRoll.findViewById(R.id.sensorValue)

        // Sensor Setup
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        rotationVector = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ROTATION_VECTOR) {
            val rotationMatrix = FloatArray(9)
            val orientationAngles = FloatArray(3)

            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
            SensorManager.getOrientation(rotationMatrix, orientationAngles)

            // Indices: 1 = Pitch, 2 = Roll
            updateUI(orientationAngles[1], orientationAngles[2])
        }
    }

    private fun updateUI(pitch: Float, roll: Float) {
        val pDeg = Math.toDegrees(pitch.toDouble()).toFloat()
        val rDeg = Math.toDegrees(roll.toDouble()).toFloat()

        val predicted = classifyPosition(pDeg, rDeg)

        tvPosition.text = predicted
        tvPitchValue.text = String.format("%.1f°", pDeg)
        tvRollValue.text = String.format("%.1f°", rDeg)

        // Visual Feedback based on state
        when (predicted) {
            "Flat" -> setUIStyle("#00E676") // Green
            "In Motion" -> setUIStyle("#2196F3") // Blue
            else -> setUIStyle("#FFAB00") // Amber for tilted states
        }
    }

    private fun setUIStyle(colorHex: String) {
        val color = Color.parseColor(colorHex)
        tvPosition.setTextColor(color)
        resultCard.setStrokeColor(ColorStateList.valueOf(color))
    }

    private fun classifyPosition(pDeg: Float, rDeg: Float): String {
        return when {
            abs(pDeg) < 10 && abs(rDeg) < 10 -> "Flat"
            pDeg < -45 -> "Upright"
            pDeg > 45 -> "Upside Down"
            rDeg > 40 -> "Tilted Right"
            rDeg < -40 -> "Tilted Left"
            else -> "In Motion"
        }
    }

    override fun onResume() {
        super.onResume()
        rotationVector?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}