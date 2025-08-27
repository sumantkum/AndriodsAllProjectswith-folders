package com.example.unit3app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.unit3app.databinding.ActivityMyFragmentBinding

class MyFragment : AppCompatActivity(), onPassData {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_fragment)

        val fragment1 = Fragm1()
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainContainer, fragment1)  // Make sure R.id.mainContainer exists
            .commit()
    }

    override fun onDataPass(data: String) {
        val bundle = Bundle().apply {
            putString("Inputdata", data)
        }

        val fragment2 = Fragm2().apply {
            arguments = bundle
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.mainContainer, fragment2)
            .addToBackStack(null)
            .commit()
    }
}
