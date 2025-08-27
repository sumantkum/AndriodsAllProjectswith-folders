package com.example.unit4

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var datalist: ArrayList<DataClass>
    lateinit var imageList:Array<Int>
    lateinit var titleList:Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        imageList = arrayOf(

            R.drawable.img1,
            R.drawable.img,
            R.drawable.img_1,
            R.drawable.img_2,
            R.drawable.img_3,
            R.drawable.img_4,
            R.drawable.img_5,
            R.drawable.img_6,
            R.drawable.img_7,
            R.drawable.img_8,

        )

        titleList = arrayOf(

            "ListView",
            "CheckBox",
            "ImageView",
            "Toggle Button",
            "Date Picker",
            "Time Picker",
            "Rating Bar",
            "Edit Text",
            "Camera",
            "TextView",

        )
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        datalist = ArrayList() // ✅ This creates an empty ArrayList<DataClass>
        getData()

    }

    private fun getData(){
        for (i in imageList.indices){
            val data = DataClass(imageList[i], titleList[i])
            datalist.add(data)
        }
        recyclerView.adapter = AdapterClass(datalist)

    }

}