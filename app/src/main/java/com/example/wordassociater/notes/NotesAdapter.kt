package com.example.wordassociater.notes

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.databinding.HolderNoteBinding
import com.example.wordassociater.fire_classes.Note
import com.example.wordassociater.firestore.FireNotes
import com.example.wordassociater.utils.ItemTouchHelperAdapter

class NoteAdapter(val takeNoteFunc: (note: Note) -> Unit): ListAdapter<Note, RecyclerView.ViewHolder>(NoteDiff()), ItemTouchHelperAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NoteHolder(HolderNoteBinding.inflate(LayoutInflater.from(parent.context)), takeNoteFunc)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val note = currentList[position]
        (holder as NoteHolder).onBind(note)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Log.i("swipeFunc", "item swiped")
        return false
    }

    override fun onItemDismiss(position: Int) {
        Log.i("swipeFunc", "NotesAdapter / onItemDismiss")
        val note = currentList[position]
        note.archived = true
        FireNotes.update(note, "archived", true)
    }
}

class NoteDiff: DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }
}