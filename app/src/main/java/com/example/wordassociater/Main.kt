package com.example.wordassociater

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.fire_classes.*
import com.example.wordassociater.firestore.FireStoreListener
import com.example.wordassociater.utils.CommonWord
import com.example.wordassociater.utils.Language


class Main : AppCompatActivity() {

    companion object {


        var inFragment = Frags.START
        var characterList = MutableLiveData<List<Character>>(mutableListOf())
        var snippetList = MutableLiveData<List<Snippet>?>(mutableListOf())
        var notesList = MutableLiveData<List<Note>?>(mutableListOf())
        var bubbleList = MutableLiveData<List<Bubble>?>(mutableListOf())
        var dialogueList = MutableLiveData<List<Dialogue>?>(mutableListOf())
        val sphereList = MutableLiveData<List<Sphere>?>(mutableListOf())
        val wordsList = MutableLiveData<List<Word>?>(mutableListOf())
        val wordCatsList = MutableLiveData<List<WordCat>>(mutableListOf())
        val nuwsList = MutableLiveData<List<Nuw>?>(mutableListOf())
        val activeWordCats = MutableLiveData<List<WordCat>?>(mutableListOf())
        val storyLineList = MutableLiveData<List<StoryLine>?>(mutableListOf())
        val proseList = MutableLiveData<List<Prose>?>(mutableListOf())
        val eventList = MutableLiveData<List<Event>?>(mutableListOf())
        val famList = MutableLiveData<List<Fam>?>(mutableListOf())
        val itemList = MutableLiveData<List<Item>>(mutableListOf())
        val locationList = MutableLiveData<List<Location>>(mutableListOf())


        //commonWOrds
        val commonWordsVeryGerman = MutableLiveData<List<CommonWord>?>()
        val commonWordsSomewhatGerman = MutableLiveData<List<CommonWord>?>()
        val commonWordsVeryEnglish = MutableLiveData<List<CommonWord>?>()
        val commonWordsSomewhatEnglish = MutableLiveData<List<CommonWord>?>()


        var wordConnectionsList = mutableListOf<WordConnection>()
        var maxLayers = 0

        val outsideEditClicked = MutableLiveData<Unit>()



        fun getWordConnection(id: Long): WordConnection? {
            return wordConnectionsList.find { wc -> wc.id == id }
        }

        fun getCommonWord(language: Language, text: String, type: CommonWord.Type): CommonWord? {
            return getCommonWordsListReference(language, type).value?.find { cm -> cm.text == text }
        }

        fun getEvent(id: Long): Event? {
            return eventList.value!!.find { e -> e.id == id }
        }

        fun getProse(id: Long): Prose? {
            return proseList.value!!.find { p -> p.id == id}
        }

        fun getCharacterByConnectId(connectId: Long): Character? {
            return characterList.value?.find { c -> c.connectId == connectId }
        }

        fun getItem(id:Long): Item? {
            return itemList.value!!.find { i -> i.id == id }
        }

        fun getLocation(id: Long): Location?  {
            return locationList.value!!.find { l -> l.id == id }
        }

        fun getFam(id:Long): Fam? {
            return famList.value!!.find { f -> f.id == id }
        }

        fun getStoryLine(id: Long): StoryLine? {
            return storyLineList.value!!.find { s -> s.id == id }
        }
        fun getCommonWordType(language: Language, text: String): CommonWord.Type {
            val allCommonWordsGerman = commonWordsVeryGerman.value!! + commonWordsSomewhatGerman.value!!
            val allCommonWordsEnglsih = commonWordsVeryEnglish.value!! + commonWordsSomewhatEnglish.value!!
            val list = if(language == Language.German) allCommonWordsGerman else allCommonWordsEnglsih
            val commonWord= list.find { cm -> cm.text == text }
            return when(commonWord?.type) {
                null -> CommonWord.Type.Uncommon
                CommonWord.Type.Very -> CommonWord.Type.Very
                CommonWord.Type.Somewhat -> CommonWord.Type.Somewhat
                CommonWord.Type.Uncommon -> CommonWord.Type.Uncommon
            }
        }
        
        fun getWordCat(id:Long): WordCat? {
            val wordCat = wordCatsList.value?.find { wordCat -> wordCat.id == id }
            Log.i("wordCatProb","wordCatsList .value is ${wordCatsList.value}")
            Log.i("wordCatProb", "wordcat: $wordCat  id searched is = $id")
            return wordCat

        }

        fun getCharacter(id: Long): Character? {
            return characterList.value?.find { c -> c.id == id }
        }

        fun getStoryPart(id: Long): StoryPart? {
            val parts = (snippetList.value!! + eventList.value!!).toList()
            return parts.find { sp -> sp.id == id }
        }

        fun getNuw(name: String): Nuw? {
            return nuwsList.value?.find { n -> n.text == name }
        }

        fun getNuwById(id: Long): Nuw? {
            return nuwsList.value?.find { n -> n.id == id }
        }

        fun getSnippet(id: Long): Snippet? {
            return snippetList.value?.find { s -> s.id == id }
        }

        fun getNote(id: Long): Note? {
            return notesList.value?.find { note -> note.id == id }
        }

        fun getWord(id: Long): Word? {
            return wordsList.value?.find { w -> w.id == id }
        }

        fun getWordByText(text: String): Word? {
            var word: Word? = null
            for(w in wordsList.value!!) {
                if(w.text == text) {
                    word = w
                    break
                }
                for(fam in w.getFams()) {
                    if(fam.text == text) {
                        word = w
                        break
                    }
                }
            }
            return word
        }

        fun getBubble(id: Long): Bubble? {
            return bubbleList.value?.find { b -> b.id == id }
        }

        fun getDialogue(id: Long): Dialogue? {
            return dialogueList.value?.find { d -> d.id == id }
        }

        fun getSphere(id: Long): Sphere? {
            return sphereList.value?.find { s -> s.id == id }
        }

        fun getCommonWordsListReference(language: Language, type: CommonWord.Type): MutableLiveData<List<CommonWord>?> {
            return when(language) {
                Language.German -> {
                    if(type == CommonWord.Type.Very) commonWordsVeryGerman else commonWordsSomewhatGerman
                }
                Language.English -> {
                    if(type == CommonWord.Type.Very) commonWordsVeryEnglish else commonWordsSomewhatEnglish
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        FireStoreListener.getTheStuff()


        inFragment = Frags.START
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus;
            if (v is EditText) {
                val outRect = Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus();
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    outsideEditClicked.value = Unit
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }


}


enum class Frags { START, SNIPPETLIST, WRITE, SNIPPET, CHARACTERLIST, CHARACTER, CONNECTSNIPPETS, EDITSNIPPETS, WORDLIST, WORDDETAILED, NOTES, ITEMS, LOCATIONS, EVENTS, NUWSLIST}