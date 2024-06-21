package com.example.notes.activity

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.notes.R
import com.example.notes.database.NoteDatabase
import com.example.notes.databinding.ActivityEditNoteBinding
import com.example.notes.models.Notes
import com.example.notes.repository.NoteRepo
import com.example.notes.viewmodels.NoteViewModel
import com.example.notes.viewmodels.NotesViewModelFactory

class EditNote : AppCompatActivity() {
    var id = 0
    private var title = ""
    private var desc = ""
    private val binding: ActivityEditNoteBinding by lazy {
        ActivityEditNoteBinding.inflate(layoutInflater)
    }

    private lateinit var noteViewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        noteViewModel = ViewModelProvider(
            this, NotesViewModelFactory(NoteRepo(NoteDatabase(this)))
        )[NoteViewModel::class.java]

        title = intent.getStringExtra("title").toString()
        desc = intent.getStringExtra("desc").toString()
        id = (intent.getStringExtra("id"))!!.toInt()

        binding.editNoteTitle.setText(title)
        binding.editNoteDesc.setText(desc)

        binding.editNoteFab.setOnClickListener {
            val edtTitle = binding.editNoteTitle.text.toString()
            val edtDesc = binding.editNoteDesc.text.toString()


            showDialog(this, "update")?.setNegativeButton(
                "No"
            ) { _, _ -> }?.setPositiveButton(
                "Yes"
            ) { _, _ ->
                noteViewModel.updateNote(
                    Notes(
                        id = id, noteTitle = edtTitle, noteDesc = edtDesc
                    )
                )

                finish()

            }?.show()


        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        showDialog(this, "delete")?.setNegativeButton(
            "No"
        ) { _, _ -> }?.setPositiveButton(
            "Yes"
        ) { _, _ ->
            noteViewModel.deleteNote(
                Notes(
                    id = id,
                    noteDesc = desc,
                    noteTitle = title
                )
            )

            finish()
        }?.show()

        return true
    }
}

fun showDialog(context: Context, title: String): AlertDialog.Builder? {

    val dialog = AlertDialog.Builder(context)
        .setIcon(R.drawable.baseline_delete_24)
        .setTitle("Do you want to $title?")
        .setMessage("Are you sure?")
        .setCancelable(true)

    return dialog
}