package com.example.wordassociater

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.fire_classes.*
import com.example.wordassociater.firestore.FireStoreListener
import com.example.wordassociater.words.WordLinear


class Main : AppCompatActivity() {

    companion object {
        var inFragment = Frags.START
        var characterList = MutableLiveData<List<Character>?>()
        var snippetList = MutableLiveData<List<Snippet>?>()
        var strainsList = MutableLiveData<List<Strain>?>()
        var notesList = MutableLiveData<List<Note>?>()
        var bubbleList = MutableLiveData<List<Bubble>?>()
        var dialogueList = MutableLiveData<List<Dialogue>?>()
        val sphereList = MutableLiveData<List<Sphere>?>()
        var wordConnectionsList = mutableListOf<WordConnection>()
        var maxLayers = 0

        val outsideEditClicked = MutableLiveData<Unit>()

        fun getWordConnection(id: Long): WordConnection? {
            return wordConnectionsList.find { wc -> wc.id == id }
        }

        fun getCharacter(id: Long): Character? {
            return characterList.value?.find { c -> c.id == id }
        }

        fun getSnippet(id: Long): Snippet? {
            return snippetList.value?.find { s -> s.id == id }
        }

        fun getStrain(id: Long): Strain? {
            return strainsList.value?.find { s -> s.id == id }
        }

        fun getNote(id: Long): Note? {
            return notesList.value?.find { note -> note.id == id }
        }

        fun getWord(id: String): Word? {
            return WordLinear.allWords.find { w -> w.id == id }
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


enum class Frags { START, READ, WRITE, SNIPPET, CHARACTERLIST, CHARACTER, CONNECTSNIPPETS, EDITSNIPPETS, WORDLIST, WORDDETAILED}