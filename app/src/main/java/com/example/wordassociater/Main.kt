package com.example.wordassociater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.character.Character
import com.example.wordassociater.notes.Note
import com.example.wordassociater.utils.Snippet
import com.example.wordassociater.strain_edit_fragment.Strain
import com.example.wordassociater.firestore.FireStoreListener

class Main : AppCompatActivity() {

    companion object {
        var inFragment = Frags.START

        var characterList = mutableListOf<Character>()
        var snippetList = mutableListOf<Snippet>()
        var strainsList = MutableLiveData<List<Strain>?>()
        var notesList = MutableLiveData<List<Note>?>()
        var maxLayers = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FireStoreListener.getTheStuff()
        inFragment = Frags.START
    }


}

enum class Frags { START, READ, WRITE, SNIPPET, CHARACTERLIST, CHARACTER, CONNECTSNIPPETS, EDITSNIPPETS, WORDLIST}