package com.example.wordassociater.notes

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.wordassociater.R
import com.example.wordassociater.viewpager.ViewPagerMainFragment
import com.example.wordassociater.databinding.FragmentNotesBinding
import com.example.wordassociater.fire_classes.Note
import com.example.wordassociater.firestore.FireNotes
import com.example.wordassociater.firestore.FireStats
import com.example.wordassociater.lists.NoteLists
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.utils.Page
import com.example.wordassociater.utils.SwipeToDeleteCallback

class NotesFragment: Fragment() {
    lateinit var b : FragmentNotesBinding
    companion object {
        lateinit var noteAdapter : NoteAdapter
        val noteTrigger = MutableLiveData<Unit>()
    }

    var whatType = Note.Type.Other
    var currentListType = Note.Type.Other

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentNotesBinding.inflate(inflater)
        noteAdapter = NoteAdapter(::handleNoteSelected)
        setRecycler()
        setObserver()
        setClickListener()
        setRadioBtnClicks()
        setCategoryClicks()
        return b.root
    }

    private fun handleNoteSelected(note: Note) {
        EditNoteFragment.note = note
        findNavController().navigate(R.id.action_notesFragment_to_editNoteFragment)
    }

    private fun setClickListener() {
        b.backBtn.setOnClickListener {
            ViewPagerMainFragment.goTopage(Page.Start)
        }

        b.saveBtn.setOnClickListener {
            saveNote()
        }

        b.noteInput.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                saveNote()
                return@OnKeyListener true
            }
            false
        })
    }

    private fun selectInitialButton(type: Note.Type) {
        when (type) {
            Note.Type.Story -> {
                whatType = Note.Type.Story
                b.radioStory.isChecked = true
            }
            Note.Type.WordsApp -> {
                whatType = Note.Type.WordsApp
                b.radioWords.isChecked = true
            }
            Note.Type.GameApp -> {
                whatType = Note.Type.GameApp
                b.radioGameApp.isChecked = true
            }
            Note.Type.Other -> {
                whatType = Note.Type.Other
                b.radioOther.isChecked = true
            }
        }
    }

    private fun setRecycler() {
        b.noteRecycler.adapter = noteAdapter
        noteAdapter.submitList(NoteLists.otherNotes.filter { n -> !n.archived })
        b.noteRecycler.scrollToPosition(noteAdapter.itemCount - 1)
        val callback: ItemTouchHelper.Callback = SwipeToDeleteCallback(noteAdapter)
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(b.noteRecycler)
    }

    private fun setObserver() {
        noteTrigger.observe(context as LifecycleOwner) {
            noteAdapter.submitList(NoteLists.getList(currentListType).sortedBy { n -> n.id }.reversed())
        }
    }

    private fun setRadioBtnClicks() {
        b.radioOther.setOnClickListener {
            whatType = Note.Type.Other
        }
        b.radioStory.setOnClickListener {
            whatType = Note.Type.Story
        }
        b.radioWords.setOnClickListener {
            whatType = Note.Type.WordsApp
        }
        b.radioGameApp.setOnClickListener {
            whatType = Note.Type.GameApp
        }
    }


    private fun setCategoryClicks() {
        b.btnOther.setOnClickListener {
            selectInitialButton(Note.Type.Other)
            currentListType = Note.Type.Other
            noteAdapter.submitList(NoteLists.otherNotes.filter { n -> !n.archived }.sortedBy { n -> n.id }.reversed())
            b.noteRecycler.scrollToPosition(noteAdapter.itemCount - 1)
        }

        b.btnStoryLines.setOnClickListener {
            selectInitialButton(Note.Type.Story)
            currentListType = Note.Type.Story
            noteAdapter.submitList(NoteLists.storyNotes.filter { n -> !n.archived }.sortedBy { n -> n.id }.reversed())
            b.noteRecycler.scrollToPosition(noteAdapter.itemCount - 1)

        }

        b.btnGameApp.setOnClickListener {
            selectInitialButton(Note.Type.GameApp)
            currentListType = Note.Type.GameApp
            noteAdapter.submitList(NoteLists.gameAppNotes.filter { n -> !n.archived }.sortedBy { n -> n.id }.reversed())
            b.noteRecycler.scrollToPosition(noteAdapter.itemCount - 1)
        }

        b.btnWordsApp.setOnClickListener {
            selectInitialButton(Note.Type.WordsApp)
            currentListType = Note.Type.WordsApp
            noteAdapter.submitList(NoteLists.wordsAppNotes.filter { n -> !n.archived }.sortedBy { n -> n.id }.reversed())
            b.noteRecycler.scrollToPosition(noteAdapter.itemCount - 1)
        }
    }

    private fun saveNote() {
        if(b.noteInput.text.isNotEmpty()) {
            val newNote = Note(
                id = FireStats.getNoteId(),
                content = b.noteInput.text.toString(),
                archived = false,
                type = whatType
                )
            FireNotes.add(newNote, requireContext())
            b.noteInput.setText("")
        }
        else {
            Toast.makeText(context, "Note can't be empty", Toast.LENGTH_SHORT).show()
        }
        Helper.getIMM(requireContext()).hideSoftInputFromWindow(b.noteInput.windowToken, 0)
    }


}