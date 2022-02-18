package com.example.wordassociater.firestore
import android.util.Log
import com.example.wordassociater.Main
import com.example.wordassociater.bars.DialogueNotesBar
import com.example.wordassociater.fire_classes.*
import com.example.wordassociater.start_fragment.WordLinear

object FireStoreListener {

    fun getTheStuff() {
        getCharacters()
        getStrains()
        getNotes()
        getStats()
        getSnippets()
        getWords()
        getDialogues()
        getBubbles()

        getCharactersOneTimeForSelectionList()
    }

    private fun getCharacters() {
        FireLists.characterList.addSnapshotListener { docs, error ->
            if(docs != null) {
                var charList = mutableListOf<Character>()
                for(doc in docs) {
                    Log.i("fireProb", "$doc")
                    val character = doc.toObject(Character::class.java)
                    character.id = doc.id
                    charList.add(character)
                }
                Main.characterList.value = charList
            }
            else {
            }
        }
    }

    private fun getCharactersOneTimeForSelectionList() {
        FireLists.characterList.get().addOnSuccessListener{ docs->
            if(docs != null) {
                var charList = mutableListOf<Character>()
                for(doc in docs) {
                    Log.i("fireProb", "$doc")
                    val character = doc.toObject(Character::class.java)
                    character.id = doc.id
                    charList.add(character)
                }
                DialogueNotesBar.popUpCharacterList.value = charList
            }
            else {
            }
        }
    }

    private fun getSnippets() {
        FireLists.snippetsList.addSnapshotListener { docs, firebaseFirestoreException ->
            val newSnippetList = mutableListOf<Snippet>()
            if(docs != null) {
                for(doc in docs) {
                    val snippet = doc.toObject(Snippet::class.java)
                    newSnippetList.add(snippet)
                }
                Main.snippetList.value = newSnippetList
            }
            else {
            }
        }
    }

    private fun getNotes() {
        FireLists.noteList.addSnapshotListener { docs, error ->
            val newNotesList = mutableListOf<Note>()
            if(docs != null) {
                for(doc in docs) {
                    val note = doc.toObject(Note::class.java)
                    newNotesList.add(note)
                }
                Main.notesList.value = newNotesList
            }

        }
    }

    private fun getStrains() {
        FireLists.fireStrainsList.addSnapshotListener { docs, error ->
            if(docs != null) {
                val newStrainList = mutableListOf<Strain>()
                for(doc in docs) {
                    Log.i("wordId", "$doc")
                    val strain = doc.toObject(Strain::class.java)
                    newStrainList.add(strain)
                    if(strain.connectionLayer > Main.maxLayers) Main.maxLayers = strain.connectionLayer
                }
                Main.strainsList.value = newStrainList.toMutableList()
            }

        }
    }

    private fun getStats() {
        FireLists.fireStats.addSnapshotListener { doc, error ->
            if(doc != null) {
                val newStats = doc.toObject(Stats::class.java)
                FireLists.stats = newStats
            }
        }
    }

    private fun getBubbles() {
        FireLists.bubbleList.addSnapshotListener { docs, error ->
            if(docs != null) {
                val bubbleList = mutableListOf<Bubble>()
                for(doc in docs) {
                    val bubble = doc.toObject(Bubble::class.java)
                    bubbleList.add(bubble)
                }
                Main.bubbleList.value = bubbleList
            }
        }
    }

    private fun getDialogues() {
        FireLists.dialogueList.addSnapshotListener { docs, error ->
            if(docs != null) {
                val dialogueList = mutableListOf<Dialogue>()
                for(doc in docs) {
                    val dialogue = doc.toObject(Dialogue::class.java)
                    dialogueList.add(dialogue)
                }
                Main.dialogueList.value = dialogueList
            }
        }
    }


    private fun getWords() {
        val typeList = mutableListOf(Word.Type.Object, Word.Type.Person, Word.Type.Place, Word.Type.CHARACTER,
                Word.Type.Action, Word.Type.Adjective)
        for(type in typeList) {
            getWords(type)
        }
    }

    private fun getWords(type: Word.Type) {
        val collectionReference = FireLists.getCollectionRef(type)
        collectionReference.addSnapshotListener { docs, error ->
            if(docs != null) {
                for(doc in docs) {
                    val word = doc.toObject(Word::class.java)
                    word.selected = false
                    WordLinear.getWordList(type).add(word)
                    WordLinear.allWords.add(word)
                }
            }
        }
    }
}