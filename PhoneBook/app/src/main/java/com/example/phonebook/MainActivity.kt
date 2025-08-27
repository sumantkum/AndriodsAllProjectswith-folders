package com.example.phonebook

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var listView : ListView
    private lateinit var  titleInput : EditText
    private lateinit var descriptionInput : EditText
    private lateinit var selectImageBtn : Button
    private lateinit var addItemBtn: Button
    private val itemList = mutableListOf<dynamiclistadaptor>()
    private lateinit var adapter : dynamiclistadaptor
    private var selectImageUri:Uri? = null
    private val imagepickcode = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.daymiclistview)
        titleInput = findViewById(R.id.title)
        descriptionInput = findViewById(R.id.description)


    }
}