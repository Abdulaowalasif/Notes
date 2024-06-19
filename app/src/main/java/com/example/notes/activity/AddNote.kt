package com.example.notes.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.notes.R
import com.example.notes.database.NoteDatabase
import com.example.notes.databinding.ActivityAddNoteBinding
import com.example.notes.models.Notes
import com.example.notes.repository.NoteRepo
import com.example.notes.viewmodels.NoteViewModel
import com.example.notes.viewmodels.NotesViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddNote : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteViewModel = ViewModelProvider(
            this,
            NotesViewModelFactory(NoteRepo(NoteDatabase(this)))
        )[NoteViewModel::class.java]

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        val title = binding.addNoteTitle.text.toString()
        val desc = binding.addNoteDesc.text.toString()

        if (id == R.id.saveMenu) {
            if (!matchNotes(title, desc)) {
                noteViewModel.insertNote(Notes(id = null, noteTitle = title, noteDesc = desc))
                finish()
            }
        }
        return true
    }


    private fun matchNotes(title: String? = null, desc: String? = null): Boolean {
        return noteViewModel.getAllNotes().value?.any { it.noteTitle == title && it.noteDesc == desc }
            ?: false
    }

}