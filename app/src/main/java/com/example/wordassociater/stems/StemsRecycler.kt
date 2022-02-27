package com.example.wordassociater.stems
import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.SwipeToDeleteCallback

class StemsRecycler(context: Context, attributeSet: AttributeSet): RecyclerView(context, attributeSet) {
    lateinit var stemAdapter: StemAdapter
    lateinit var stemList: MutableLiveData<List<String>>


    fun initRecycler(word: Word, stemList: MutableLiveData<List<String>>, onHeaderClicked: () -> Unit, takeContentFunc: (text: String) -> Unit) {
        stemAdapter = StemAdapter(stemList, word, onHeaderClicked, takeContentFunc)
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        this.stemList = stemList
        adapter = stemAdapter
        val callback = SwipeToDeleteCallback(stemAdapter)
        ItemTouchHelper(callback).attachToRecyclerView(this)
        setObserver()
    }

    fun setObserver() {
        stemList.observe(context as LifecycleOwner) {
            it.toMutableList().remove("stemHeader")
            if(it != null) stemAdapter.submitList(it.sorted().reversed() + listOf("stemHeader"))
            else stemAdapter.submitList(listOf("stemHeader"))
            smoothScrollToPosition(stemAdapter.currentList.count() - 1)
        }
    }


}