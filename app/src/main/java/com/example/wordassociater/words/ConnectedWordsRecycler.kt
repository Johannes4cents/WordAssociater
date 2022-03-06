package com.example.wordassociater.words

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.ConnectedWord

class ConnectedWordsRecycler(context : Context, attributeSet: AttributeSet): RecyclerView(context, attributeSet) {
    private lateinit var liveWordsList : MutableLiveData<List<Word>>
    private val wordAdapter = ConnectedWordsAdapter(::onWordSelected)


    fun initRecycler(liveList: MutableLiveData<List<Word>>) {
        liveWordsList = liveList
        adapter = wordAdapter
        setObserver()
    }

    private fun onWordSelected(word: Word) {

    }

    private fun setObserver() {
        liveWordsList.observe(context as LifecycleOwner) {
            var connectedWordsList = mutableListOf<ConnectedWord>()
            for(word in it) {
                for(wc in word.getWordConnections()) {
                    val words = Word.convertIdListToWord(wc.wordsList)
                    val theOtherWord = words.find { w -> w != word }!!
                    connectedWordsList = ConnectedWord.addWord(theOtherWord, connectedWordsList.toMutableList())
                }
            }
            wordAdapter.submitList(connectedWordsList)
        }


    }

}

