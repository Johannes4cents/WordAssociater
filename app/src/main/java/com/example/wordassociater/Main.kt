package com.example.wordassociater

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.fire_classes.Note
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.fire_classes.Strain
import com.example.wordassociater.firestore.FireStoreListener

class Main : AppCompatActivity() {

    companion object {
        var inFragment = Frags.START
        var characterList = mutableListOf<Character>()
        var snippetList = mutableListOf<Snippet>()
        var strainsList = MutableLiveData<List<Strain>?>()
        var notesList = MutableLiveData<List<Note>?>()
        var maxLayers = 0

        fun getCharacter(id: String): Character? {
            return characterList.find { c -> c.id == id }
        }

        fun getSnippet(id: Long): Snippet? {
            return snippetList.find { s -> s.id == id }
        }

        fun getStrain(id: String): Strain? {
            return strainsList.value?.find { s -> s.id == id }
        }

        fun getNote(id: Long): Note? {
            return notesList.value?.find { note -> note.id == id }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FireStoreListener.getTheStuff()
        inFragment = Frags.START
    }


}

enum class Frags { START, READ, WRITE, SNIPPET, CHARACTERLIST, CHARACTER, CONNECTSNIPPETS, EDITSNIPPETS, WORDLIST}