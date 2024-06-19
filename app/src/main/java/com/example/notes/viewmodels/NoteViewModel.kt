package com.example.notes.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.models.Notes
import com.example.notes.repository.NoteRepo
import kotlinx.coroutines.launch

class NoteViewModel(private val noteRepo: NoteRepo) : ViewModel() {


    fun insertNote(notes: Notes) = viewModelScope.launch {
        noteRepo.insertNote(notes)
    }

    fun updateNote(notes: Notes) = viewModelScope.launch {
        noteRepo.updateNote(notes)
    }

    fun deleteNote(notes: Notes) = viewModelScope.launch {
        noteRepo.deleteNote(notes)
    }

    fun getAllNotes() = noteRepo.getAllNotes()

    fun searchNote(query: String?) = noteRepo.searchNote(query)
}