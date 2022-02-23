package com.example.wordassociater.firestore
import android.util.Log
import com.example.wordassociater.Main
import com.example.wordassociater.StartFragment
import com.example.wordassociater.bars.AddStuffBar
import com.example.wordassociater.bars.AddWordBar
import com.example.wordassociater.fire_classes.*
import com.example.wordassociater.lists.DramaLists
import com.example.wordassociater.lists.NoteLists
import com.example.wordassociater.notes.NotesFragment
import com.example.wordassociater.strains.StrainEditFragment
import com.example.wordassociater.words.WordLinear

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
        getDramas()
        getSpheres()
        getSpheresOneTime()
        getWordConnections()
    }

    private fun getCharacters() {
        FireLists.characterList.addSnapshotListener { docs, error ->
            if(docs != null) {
                var charList = mutableListOf<Character>()
                for(doc in docs) {
                    val character = doc.toObject(Character::class.java)
                    character.selected = false
                    charList.add(character)
                }
                Main.characterList.value = charList
            }
            else {
            }
        }
    }

    private fun getWordConnections() {
        FireLists.wordConnectionList.addSnapshotListener { docs, error ->
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

    private fun getDramas() {
        for(dramaType in FireDrama.typeList) {
            FireLists.getDramaCollectionRef(dramaType).addSnapshotListener { docs, error ->
                if(docs != null) {
                    DramaLists.getList(dramaType).clear()
                    for(doc in docs) {
                        val drama = doc.toObject(Drama::class.java)
                        DramaLists.getList(dramaType).add(drama)
                    }
                }
            }
        }
    }

    private fun getSpheresOneTime() {
        FireLists.spheresList.get().addOnSuccessListener { docs ->
            val sphereList = mutableListOf<Sphere>()
            if(docs != null) {
                for(doc in docs) {
                    val sphere = doc.toObject<Sphere>(Sphere::class.java)
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
                val submitList = listOf(randomPerson) + charList
                AddStuffBar.popUpCharacterList.value = submitList
                StrainEditFragment.popUpCharacterList.value = charList
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
        val collectionReference = FireLists.getWordCollectionRef(type)
        collectionReference.addSnapshotListener { docs, error ->
            val nameList = mutableListOf<String>()
            if(docs != null) {
                WordLinear.getWordList(type).clear()
                for(doc in docs) {
                    val word = doc.toObject(Word::class.java)
                    nameList.add(word.text)
                    word.selected = false
                    WordLinear.getWordList(type).add(word)
                    if(WordLinear.allWords.find { w -> w.id == word.id } == null) WordLinear.allWords.add(word)
                }
            }
        }
    }

}