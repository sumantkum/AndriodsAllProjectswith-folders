package com.example.enablebluetoothandwifiproject

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnWifi = findViewById<Button>(R.id.btnWifi)
        val btnBluetooth = findViewById<Button>(R.id.btnBluetooth)
        val btnAirplane = findViewById<Button>(R.id.btnAirplane)

        // WiFi Button
        btnWifi.setOnClickListener {
            val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE)
                    as WifiManager
            if (wifiManager.isWifiEnabled) {
                wifiManager.isWifiEnabled = false
                Toast.makeText(this,
                    "WiFi Disabled", Toast.LENGTH_SHORT).show()
            } else {
                wifiManager.isWifiEnabled = true
                Toast.makeText(this,
                    "WiFi Enabled", Toast.LENGTH_SHORT).show()
            }
        }

        // Bluetooth Button
        btnBluetooth.setOnClickListener@androidx.annotation.RequiresPermission(
            android.Manifest.permission.BLUETOOTH_CONNECT) {
            val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
            if (bluetoothAdapter == null) {
                Toast.makeText(this,
                    "Bluetooth not supported", Toast.LENGTH_SHORT).show()
            } else {
                if (bluetoothAdapter.isEnabled) {
                    bluetoothAdapter.disable()
                    Toast.makeText(this,
                        "Bluetooth Disabled", Toast.LENGTH_SHORT).show()
                } else {
                    bluetoothAdapter.enable()
                    Toast.makeText(this,
                        "Bluetooth Enabled", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Airplane Mode Button (opens settings panel — direct toggle restricted by Android)
        btnAirplane.setOnClickListener {
            // Open the Airplane Mode Settings screen
            val intent = Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS)
            startActivity(intent)
        }
    }
}
