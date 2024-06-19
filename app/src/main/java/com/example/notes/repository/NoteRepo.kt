package com.example.notes.repository

import com.example.notes.database.NoteDatabase
import com.example.notes.models.Notes

class NoteRepo(private val noteDatabase: NoteDatabase) {

    suspend fun insertNote(notes: Notes) = noteDatabase.getNoteDao().insertNote(notes)
    suspend fun updateNote(notes: Notes) = noteDatabase.getNoteDao().updateNote(notes)
    suspend fun deleteNote(notes: Notes) = noteDatabase.getNoteDao().deleteNote(notes)

    fun getAllNotes() = noteDatabase.getNoteDao().getAllNote()
    fun searchNote(query: String?) = noteDatabase.getNoteDao().searchNote(query)
}