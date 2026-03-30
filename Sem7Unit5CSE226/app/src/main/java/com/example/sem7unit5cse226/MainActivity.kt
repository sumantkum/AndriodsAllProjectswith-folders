package com.example.sem7unit5cse226

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Button
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.inappmessaging.model.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btngeocode = findViewById<Button>(R.id.btngeocode);
        val tvGeocodeResult = findViewById<TextView>(R.id.tvGeocodeResult);
        val btnReverseGeocode = findViewById<Button>(R.id.btnReverseGeocode);
        val tvReverseGeocodeResult = findViewById<TextView>(R.id.tvReverseGeocodeResult);


        val geocoder = Geocoder(this, Locale.getDefault())

        btngeocode.setOnClickListener {
            val locationName = "Kernal India"
            val addressList = geocoder.getFromLocationName(locationName, 1)
            if(!addressList.isNullOrEmpty()){
                val address = addressList[0]
                val lat = address.latitude
                val lon = address.latitude
                tvGeocodeResult.text = "Latitude: $lat, Longitude: $lon"
            }
            else{
                tvGeocodeResult.text = "No corrdinates found for this location"
            }

        }


        btnReverseGeocode.setOnClickListener{

            val lat = 12.9716
            val lon = 77.5946

            val addressList = geocoder.getFromLocation(lat, lon, 1)
            if(!addressList.isNullOrEmpty()){
                val address = addressList[0]
                val locationName = address.getAddressLine(0)
                tvReverseGeocodeResult.text = locationName
            }
            else{
                tvGeocodeResult.text = "No address found for this coordinates."
            }
        }
    }
}