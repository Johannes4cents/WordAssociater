package com.example.wordassociater.fams

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.fire_classes.Fam
import com.example.wordassociater.fire_classes.Word

class FamRecycler(context: Context, attributeSet: AttributeSet): RecyclerView(context, attributeSet) {
    enum class Type { List, Popup}
    lateinit var type: Type
    lateinit var famAdapter: FamAdapter
    lateinit var famList: MutableLiveData<List<Fam>>


    fun initRecycler(type: Type, word: Word, famList: MutableLiveData<List<Fam>>, onHeaderClicked: () -> Unit, takeFamFunc: (fam: Fam) -> Unit) {
        famAdapter = FamAdapter(type, famList, word, onHeaderClicked, takeFamFunc)
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        this.famList = famList
        adapter = famAdapter
        setObserver()
    }

    fun setObserver() {
        famList.observe(context as LifecycleOwner) {
            val newList = it.toMutableSet()
            if(type != Type.Popup) {
                famAdapter.submitList(newList.sortedBy { f -> f.text }.reversed() + listOf(makeHeader()))
            }
            else {
                famAdapter.submitList(it)
            }
            smoothScrollToPosition(famAdapter.currentList.count() - 1)

        }
    }

    private fun makeHeader(): Fam {
        val header = Fam()
        header.isHeader = true
        return header
    }


}