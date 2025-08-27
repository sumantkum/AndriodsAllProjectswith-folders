package com.example.unit3app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load SenderFragment and ReceiverFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.sender_container, SenderFragment())
            .commit()

        supportFragmentManager.beginTransaction()
            .replace(R.id.receiver_container, ReceiverFragment())
            .commit()
    }
}
