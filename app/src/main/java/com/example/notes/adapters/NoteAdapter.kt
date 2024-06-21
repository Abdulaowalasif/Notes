package com.example.notes.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.activity.EditNote
import com.example.notes.databinding.FragmentNoteBinding
import com.example.notes.models.Notes

class NoteAdapter() :
    ListAdapter<Notes, NoteAdapter.NoteViewHolder>(DiffUtil()) {
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        context = parent.context
        return NoteViewHolder(
            FragmentNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.noteTitle.text = item.noteTitle
        holder.binding.noteDesc.text = item.noteDesc
        holder.itemView.setOnClickListener {
            val intent = Intent(context, EditNote::class.java)
            intent.putExtra("title", item.noteTitle)
            intent.putExtra("desc", item.noteDesc)
            intent.putExtra("id", item.id.toString())
            context.startActivity(intent)
        }

    }

    class NoteViewHolder(val binding: FragmentNoteBinding) : RecyclerView.ViewHolder(binding.root)

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Notes>() {
        override fun areItemsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            return oldItem.id == newItem.id && oldItem.noteDesc == newItem.noteDesc && oldItem.noteTitle == newItem.noteTitle
        }

        override fun areContentsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            return oldItem == newItem
        }
    }
}