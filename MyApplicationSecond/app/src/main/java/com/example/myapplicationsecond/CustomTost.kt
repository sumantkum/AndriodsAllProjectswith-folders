package com.example.myapplicationsecond

import android.content.Context
import android.content.LocusId
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CustomTost : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_custom_tost)

//        fun Context.showCustomTost(message: String, iconResId: Int){
//            val inflater = LayoutInflater.from(this);
//            val layout: View = inflater.inflate(R.layout.activity_main, null)
//
//            val tostText: TextView = layout.findViewById(R.id.toast_txt);
//            val tostIcon: ImageView = layout.findViewById(R.id.img_icon);
//
//            tostText.text = message
//            tostIcon.setImageResource(iconResId)
//
//            val toast = Toast(this)
//            toast.duration = Toast.LENGTH_SHORT
//            toast.view = layout
//            toast.show();
//        }
//        showCustomTost("success", R.drawable.toast_background)

    }
}