package com.example.unit3sem7

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class FloatingButtonAction : AppCompatActivity() {

    private lateinit var fabMain: FloatingActionButton
    private lateinit var fabMini: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_floating_button_action)

        fabMain = findViewById(R.id.fabmain)
        fabMini = findViewById(R.id.fabmini)

        fabMain.setOnClickListener {
            showMessage("Green Floating action button clicked!")
        }

        fabMini.setOnClickListener {
            showMessage("Blue Floating action button clicked!")
        }
        findViewById<android.widget.Button>(R.id.btnToggle).setOnClickListener {
            toggleFABVissibilty()
        }
    }

    private fun toggleFABVissibilty() {
        if(fabMain.isOrWillBeShown){
            fabMain.hide()
            fabMini.hide()
        }
        else{
            fabMain.show()
            fabMini.show()
        }
    }

    private fun showMessage(message: String){
        Snackbar.make(fabMain,message, Snackbar.LENGTH_SHORT)
            .setAction("Undo") {
                Toast.makeText(this, "Action Done!!", Toast.LENGTH_LONG).show()
            }
            .show()
    }

}
