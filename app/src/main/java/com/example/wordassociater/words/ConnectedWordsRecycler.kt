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


    fun setLiveWords(liveList: MutableLiveData<List<Word>>) {
        liveWordsList = liveList
        adapter = wordAdapter
        setObserver()
    }

    private fun onWordSelected(word: Word) {

    }

    private fun setObserver() {
        var connectedWordsList = mutableListOf<ConnectedWord>()
        liveWordsList.observe(context as LifecycleOwner) {
            for(word in it) {
                for(wc in word.getWordConnections()) {
                    val words = Word.convertIdListToWord(wc.wordsList)
                    connectedWordsList = ConnectedWord.addWord(words.find { w -> w != word }!!, connectedWordsList)
                }
            }
        }
        wordAdapter.submitList(connectedWordsList)

    }

}

