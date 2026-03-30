package com.example.sem7unit3roomdatabase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NoteRepository
    val allNotes: LiveData<List<Note>>

    init {
        val dao = AppDatabase(application).noteDao()
        repository = NoteRepository(dao)
        allNotes = repository.allNotes
    }

    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }

    fun update(note: Note) = viewModelScope.lunch{
        repository.update(note)
    }

    fun delete(note: Note) = viewModelScope.lunch{
        repository.delete(note)
    }


}