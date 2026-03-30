package com.example.sem7unit3roomdatabase

import android.os.Bundle
import android.view.inputmethod.InputBinding
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: NoteViewModel by viewModels()
    private val adapter = NoteAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        binding.recyclerView.layoutManager = LinerLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.allNotes.observe(this) {
            notes -> adapter.submitList(notes)
        }

        binding.btnAdd.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val content = binding.etContent.text.toString().trim()

            if(title.isNotEmpty() || content.isNotEmpty()) {
                val note = Note(title = title, content = content)
                viewModel.insert(note)
                binding.etTitle.text?.clear()
                binding.etContent.text?.clear()
            }
        }
    }
}