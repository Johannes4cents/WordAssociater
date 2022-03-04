package com.example.wordassociater.character
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.utils.LiveClass

class CharacterRecycler(context: Context, attributeSet: AttributeSet): RecyclerView(context, attributeSet) {
    enum class Mode { Preview, Popup, List }
    lateinit var charAdapter : CharacterAdapter
    lateinit var liveList: MutableLiveData<List<Character>>
    lateinit var mode: Mode

    fun initRecycler(mode: Mode, liveList: MutableLiveData<List<Character>>?, onCharSelected: ((character: LiveClass) -> Unit)? ) {
        this.mode = mode
        layoutManager =   if(mode == Mode.Preview) LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false ) else LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false )
        charAdapter = CharacterAdapter(mode, onCharSelected)
        adapter = charAdapter
        this.liveList = liveList ?: MutableLiveData<List<Character>>()
        setObserver()

    }

    fun setCharacterLiveData(list: List<Character>) {
        for(c in list) {
            if(c.selected) Log.i("lagProb", "selectedCharacter is ${c.name}")
        }
        liveList.value = list
    }

    private fun setObserver() {
        liveList.observe(context as LifecycleOwner) {
            val selectedChars = it.filter { c -> c.selected }.filter { c -> c.id != 0L }.sortedBy { c -> c.name }
            if(mode == Mode.Preview) charAdapter.submitList(selectedChars)
            else charAdapter.submitList(it.filter { c -> c.id != 0L }.sortedBy { c -> c.name })
            charAdapter.notifyDataSetChanged()
            if(charAdapter.currentList.count() > 0 && mode == Mode.Preview) smoothScrollToPosition(charAdapter.currentList.count() - 1)
        }
    }
}