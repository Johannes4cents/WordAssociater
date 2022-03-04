package com.example.wordassociater.firestore
import android.util.Log
import com.example.wordassociater.Main
import com.example.wordassociater.StartFragment
import com.example.wordassociater.bars.AddWordBar
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.display_filter.FilterSettings
import com.example.wordassociater.fire_classes.*
import com.example.wordassociater.lists.NoteLists
import com.example.wordassociater.notes.NotesFragment
import com.example.wordassociater.utils.CommonWord

object FireStoreListener {

    fun getTheStuff() {
        getWordCatsOnce()
        getSpheresOneTime()

        getCharacters()
        getNotes()
        getStats()
        getSnippets()
        getWords()
        getDialogues()
        getBubbles()
        getSpheres()
        getWordConnections()
        getWordCats()
        getNuws()
        getCommonWords()
        getStoryLines()
        getEvents()
        getProse()
        getFams()
        getFilterOptions()
        getItems()
    }




    private fun getCharacters() {
        FireLists.characterList.addSnapshotListener { docs, _ ->
            if(docs != null) {
                val charList = mutableListOf<Character>()
                for(doc in docs) {
                    val character = doc.toObject(Character::class.java)
                    if(character.id != 22L) character.selected = false
                    charList.add(character)
                }
                Main.characterList.value = charList
            }
        }
    }

    private fun getFams() {
        FireLists.famList.addSnapshotListener { docs, error ->
            if(docs != null) {
                val famList = mutableListOf<Fam>()
                for(doc in docs) {

                    val fam = doc.toObject(Fam::class.java)
                    famList.add(fam)
                }
                Main.famList.value = famList
            }
        }
    }

    private fun getProse() {
        FireLists.proseList.addSnapshotListener { docs, error ->
            if(docs != null) {
                val proseList = mutableListOf<Prose>()
                for(doc in docs) {

                    val prose = doc.toObject(Prose::class.java)
                    proseList.add(prose)
                }
                Main.proseList.value = proseList
            }
        }
    }

    private fun getEvents() {
        FireLists.eventsList.addSnapshotListener { docs, error ->
            if(docs != null) {
                val eventList = mutableListOf<Event>()
                for(doc in docs) {
                    Log.i("lagProb", "listener - getEvents")
                    val event = doc.toObject(Event::class.java)
                    eventList.add(event)
                }
                Main.eventList.value = eventList
            }
        }
    }

    private fun getStoryLines() {
        FireLists.storyLineList.addSnapshotListener { docs, error ->
            if(docs != null) {
                val storyLines = mutableListOf<StoryLine>()
                for(doc in docs) {
                    val storyLine = doc.toObject(StoryLine::class.java)
                    storyLine.selected = false
                    storyLines.add(storyLine)
                }

                Main.storyLineList.value = storyLines
            }
        }
    }

    private fun getItems() {
        FireLists.itemsList.addSnapshotListener { docs, error ->
            if(docs != null) {
                val items = mutableListOf<Item>()
                for(doc in docs) {
                    val item = doc.toObject(Item::class.java)
                    items.add(item)
                }

                Main.itemList.value = items
            }
        }
    }

    private fun getLocations() {
        FireLists.locationsList.addSnapshotListener { docs, error ->
            if(docs != null) {
                val locations = mutableListOf<Location>()
                for(doc in docs) {
                    val location = doc.toObject(Location:: class.java)
                    locations.add(location)
                }
                Main.locationList.value = locations
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

    private fun getFilterOptions() {
        FireLists.filterList.addSnapshotListener { docs, error ->
            if(docs != null) {
                for(doc in docs) {
                    val settings = doc.toObject(FilterSettings::class.java)
                    DisplayFilter.contentShown.value = settings.contentShown
                    DisplayFilter.storyLineShown.value = settings.storyLineShown
                    DisplayFilter.layerShown.value = settings.layerShown
                    DisplayFilter.titleShown.value = settings.titleShown
                    DisplayFilter.contentShown.value = settings.titleShown
                    DisplayFilter.barColorDark.value = settings.barColorDark
                    DisplayFilter.characterShown.value = settings.characterShown
                    DisplayFilter.dateShown.value = settings.dateShown
                    DisplayFilter.dramaShown.value = settings.dramaShown
                    DisplayFilter.wordsShown.value = settings.wordsShown
                    DisplayFilter.linesShown.value = settings.linesShown
                }
            }
        }
    }

    private fun getCommonWords() {
        for(language in FireCommonWords.languageList) {
            FireLists.getCommonWordsCollectionRef(language).addSnapshotListener { docs, _ ->
                if(docs != null) {
                    val commonWordsListVery = mutableListOf<CommonWord>()
                    val commonWordsListSomewhat = mutableListOf<CommonWord>()
                    for(doc in docs) {
                        val cw = doc.toObject(CommonWord::class.java)
                        if(cw.type == CommonWord.Type.Very) commonWordsListVery.add(cw)
                        else if (cw.type == CommonWord.Type.Somewhat) commonWordsListSomewhat.add(cw)
                    }
                    Main.getCommonWordsListReference(language, CommonWord.Type.Very).value = commonWordsListVery
                    Main.getCommonWordsListReference(language, CommonWord.Type.Somewhat).value = commonWordsListSomewhat
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

    private fun getSnippets() {
        FireLists.snippetsList.addSnapshotListener { docs, firebaseFirestoreException ->
            val newSnippetList = mutableListOf<Snippet>()
            if(docs != null) {
                for(doc in docs) {
                    Log.i("snippet", "$doc" )
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
                for(doc in docs) {
                    val wordCat = doc.toObject(WordCat::class.java)
                    Log.i("wordCatProb", "getWordCats - wordCat is = $wordCat")
                    wordCat.countUsed()
                    wordCatList.add(wordCat)
                    FireWordCats.add(wordCat, null)
                }
                Main.wordCatsList.value = wordCatList
            }
        }
    }

    private fun getWordCatsOnce() {
        FireLists.wordCatList.get().addOnSuccessListener { docs ->
            if(docs != null) {
                val wordCatList = mutableListOf<WordCat>()
                for(doc in docs) {
                    Log.i("lagProb", "listener - get")
                    val wordCat = doc.toObject(WordCat::class.java)
                    wordCat.countUsed()
                    wordCatList.add(wordCat)
                }
                StartFragment.selectedWordCats.value = wordCatList
            }
        }
    }


}