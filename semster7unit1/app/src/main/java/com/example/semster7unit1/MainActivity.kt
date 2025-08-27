package com.example.semster7unit1

import GridAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        recyler = findViewById(R.id.gridRecycler)

        val data = listOf(
            GridItem("Camera", R.drawable.ic_camera),
            GridItem("Chat", R.drawable.ic_chat),
            GridItem("Music", R.drawable.ic_music),
            GridItem("Map", R.drawable.ic_map),
            GridItem("Files", R.drawable.ic_files),
            GridItem("Settings", R.drawable.ic_settings),
            )

        recyler.layoutManager = GridLayoutManager(this, 3)
        recyler.addItemDecoration(GridSpacingDecoration(8))
        recyler.adapter = GridAdapter(data) {
            item->
            Toast.makeText(this, "Clicked: ${item.title}", Toast.LENGTH_SHORT).show()
        }
    }
    private val Int.dp:Int get() = (this * resources.displayMetrics.density).toInt()
}