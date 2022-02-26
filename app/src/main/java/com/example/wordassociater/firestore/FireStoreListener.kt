package com.example.wordassociater.firestore
import com.example.wordassociater.Main
import com.example.wordassociater.StartFragment
import com.example.wordassociater.bars.AddWordBar
import com.example.wordassociater.fire_classes.*
import com.example.wordassociater.lists.NoteLists
import com.example.wordassociater.notes.NotesFragment
import com.example.wordassociater.utils.CommonWord

object FireStoreListener {

    fun getTheStuff() {
        getCharacters()
        getNotes()
        getStats()
        getSnippets()
        getWords()
        getDialogues()
        getBubbles()
        getCharactersOneTimeForSelectionList()
        getSpheres()
        getSpheresOneTime()
        getWordConnections()
        getWordCats()
        getNuws()
        getCommonWords()
    }

    private fun getCharacters() {
        FireLists.characterList.addSnapshotListener { docs, _ ->
            if(docs != null) {
                val charList = mutableListOf<Character>()
                for(doc in docs) {
                    val character = doc.toObject(Character::class.java)
                    character.selected = false
                    charList.add(character)
                }
                Main.characterList.value = charList
            }

        }
    }


    private fun getWordConnections() {
        FireLists.wordConnectionList.addSnapshotListener { docs, _ ->
            if(docs != null) {
                val connectionsList = mutableListOf<WordConnection>()
                for(doc in docs) {
                    val wordConnection = doc.toObject(WordConnection::class.java)
                    if(!WordConnection.idList.contains(wordConnection.id)) WordConnection.idList.add(wordConnection.id)
                    connectionsList.add(wordConnection)
                }
                Main.wordConnectionsList = connectionsList
            }
        }
    }

    private fun getCommonWords() {
        for(language in FireCommonWords.languageList) {
            FireLists.getCommonWordsCollectionRef(language).addSnapshotListener { docs, _ ->
                if(docs != null) {
                    val commonWordsList = mutableListOf<CommonWord>()
                    for(doc in docs) {
                        val cw = doc.toObject(CommonWord::class.java)
                        commonWordsList.add(cw)
                    }
                    Main.getCommonWordsListReference(language).value = commonWordsList
                }
            }
        }
    }

    private fun getSpheresOneTime() {
        FireLists.spheresList.get().addOnSuccessListener { docs ->
            val sphereList = mutableListOf<Sphere>()
            if(docs != null) {
                for(doc in docs) {
                    val sphere = doc.toObject(Sphere::class.java)
                    sphereList.add(sphere)
                }
                AddWordBar.selectedSphereList.value = sphereList
            }
        }
    }

    private fun getSpheres() {
        FireLists.spheresList.addSnapshotListener { docs, error ->
            val sphereList = mutableListOf<Sphere>()
            val selectedSpheres = mutableListOf<Sphere>()
            if(docs != null) {
                for(doc in docs) {
                    val sphere = doc.toObject<Sphere>(Sphere::class.java)
                    sphereList.add(sphere)
                    if(sphere.selected) selectedSpheres.add(sphere)
                }
                Main.sphereList.value = sphereList
                StartFragment.selectedSphereList.value = selectedSpheres
            }
        }
    }

    private fun getNuws() {
        FireLists.nuwList.addSnapshotListener { docs, error ->
            val nuwsList = mutableListOf<Nuw>()
            if(docs != null) {
                for(doc in docs) {
                    val nuw = doc.toObject(Nuw::class.java)
                    nuwsList.add(nuw)
                    if(!Nuw.idList.contains(nuw.id)) Nuw.idList.add(nuw.id)
                }
                Main.nuwsList.value = nuwsList
            }
        }
    }

    private fun getCharactersOneTimeForSelectionList() {
        FireLists.characterList.get().addOnSuccessListener{ docs->

            var randomPerson = Character()
            if(docs != null) {
                var charList = mutableListOf<Character>()
                for(doc in docs) {
                    val character = doc.toObject(Character::class.java)
                    character.selected = false
                    if(character.id != 16L) charList.add(character)
                    else randomPerson = character
                }
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
        val noteTypeList = listOf<Note.Type>(Note.Type.Story, Note.Type.GameApp, Note.Type.Other, Note.Type.WordsApp)
        for(type in noteTypeList) {
            getNotes(type)
        }
    }

    private fun getNotes(type: Note.Type) {
        FireLists.getNoteCollectionRef(type).addSnapshotListener { docs, error ->
            NoteLists.getList(type).clear()
            if(docs != null) {
                for(doc in docs) {
                    val note = doc.toObject(Note::class.java)
                    NoteLists.getList(type).add(note)
                }
                NotesFragment.noteTrigger.value = Unit
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
        FireLists.wordsList.addSnapshotListener { docs, _ ->
            if(docs != null) {
                val wordsList = mutableListOf<Word>()
                for(doc in docs) {
                    val word = doc.toObject(Word::class.java)
                    wordsList.add(word)
                }
                Main.wordsList.value = wordsList
            }
        }
    }

    private fun getWordCats() {
        FireLists.wordCatList.addSnapshotListener { docs, error ->
            if(docs != null) {
                val wordCatList = mutableListOf<WordCat>()
                val activeWordCats = mutableListOf<WordCat>()
                for(doc in docs) {
                    val wordCat = doc.toObject(WordCat::class.java)
                    wordCatList.add(wordCat)
                    if(wordCat.active) activeWordCats.add(wordCat)
                }
                Main.wordCatsList.value = wordCatList
                Main.activeWordCats.value = activeWordCats
            }
        }
    }


}