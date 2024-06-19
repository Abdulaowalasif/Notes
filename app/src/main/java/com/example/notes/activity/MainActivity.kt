package com.example.notes.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes.R
import com.example.notes.adapters.NoteAdapter
import com.example.notes.database.NoteDatabase
import com.example.notes.databinding.ActivityMainBinding
import com.example.notes.repository.NoteRepo
import com.example.notes.viewmodels.NoteViewModel
import com.example.notes.viewmodels.NotesViewModelFactory

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NoteAdapter
    private lateinit var noteViewModel: NoteViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteViewModel = ViewModelProvider(
            this,
            NotesViewModelFactory(NoteRepo(NoteDatabase(this)))
        )[NoteViewModel::class.java]

        adapter = NoteAdapter()
        binding.homeRecyclerView.adapter = adapter

        val layout = StaggeredGridLayoutManager(2, VERTICAL)
        binding.homeRecyclerView.layoutManager = layout

        noteViewModel.getAllNotes().observe(this) {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        }

        binding.addNoteFab.setOnClickListener {
            startActivity(Intent(this, AddNote::class.java))
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun searchQuery(query: String?) {
        val search = "%$query"

        noteViewModel.searchNote(search).observe(this) { list ->
            adapter.submitList(list)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home, menu)

        val m = menu?.findItem(R.id.searchMenu)?.actionView as SearchView
        m.isSubmitButtonEnabled = false
        m.setOnQueryTextListener(this)

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchQuery(newText)
        }
        return true
    }


}