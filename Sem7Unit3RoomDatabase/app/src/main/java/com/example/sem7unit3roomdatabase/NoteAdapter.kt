package com.example.sem7unit3roomdatabase

import android.content.ClipData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Locale

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteVH>() {
    private var notes: List<Note> = emptyList()
    fun submitList(list: List<Note>) {
        notes = list
        notifyDataSetChanged()
    }

    inner class NoteVH(val  binding: ItemNoteBinding) : RecyclerView.viewHolder(binding.root){
        fun bind(note: Note) {
            binding.tvTitle.text = note.title
            binding.tvContent.text = note.content
            val sdf = SimpleDateFormat("dd MM yyy HH:mm", Locale.getDefault())
            binding.tvTimeStamp.text = sdf.format(note.timestamp)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteVH {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteVH(binding)
    }

    override fun onBindViewHolder(holder: NoteVH, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount() = notes.size

}