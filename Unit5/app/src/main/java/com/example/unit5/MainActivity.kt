package com.example.unit5

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
//import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.unit5.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

// ✅ This must be outside the class

class MainActivity : AppCompatActivity() {

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example usage:
//        runBlocking {
//            save("username", "Sumant")
//            val result = read("username")
//            println("Saved username: $result")
//        }
    }

//    private suspend fun save(key: String, value: String) {
//        val dataStoreKey = preferencesKey<String>(key)
//        applicationContext.dataStore.edit { settings ->
//            settings[dataStoreKey] = value
//        }
//    }
//
//    private suspend fun read(key: String): String? {
//        val dataStoreKey = preferencesKey<String>(key)
//        val preferences = applicationContext.dataStore.data.first()
//        return preferences[dataStoreKey]
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
