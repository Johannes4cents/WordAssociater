package com.example.wordassociater.fams

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.fire_classes.Fam
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.utils.CommonWord
import com.example.wordassociater.utils.SwipeToDeleteCallback

class FamRecycler(context: Context, attributeSet: AttributeSet): RecyclerView(context, attributeSet) {
    enum class Type { List, Popup}
    lateinit var type: Type
    lateinit var famAdapter: FamAdapter
    lateinit var famList: MutableLiveData<List<Fam>>


    fun initRecycler(type: Type, word: Word, famList: MutableLiveData<List<Fam>>,
                     onHeaderClicked: () -> Unit, takeFamFunc: (fam: Fam) -> Unit,
                     onUpgradeFam: (fam: Fam) -> Unit,
                     onMakeCommonWord: (fam: Fam, type: CommonWord.Type) -> Unit)
                     {
        famAdapter = FamAdapter(type, famList, word, onHeaderClicked, takeFamFunc, onUpgradeFam, onMakeCommonWord)
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        this.type = type
        this.famList = famList
        adapter = famAdapter

        if(type == Type.List) {
            val callback = SwipeToDeleteCallback(famAdapter)
            val itemTouchHelper = ItemTouchHelper(callback)
            itemTouchHelper.attachToRecyclerView(this)
        }

        setObserver()
    }

    fun setObserver() {
        famList.observe(context as LifecycleOwner) {
            val newList = it.toMutableSet()
            if(type != Type.Popup) {
                famAdapter.submitList(listOf(makeHeader()) + newList.sortedBy { f -> f.id }.reversed())
            }
            else {
                famAdapter.submitList(it)
            }

        }
    }

    private fun makeHeader(): Fam {
        val header = Fam()
        header.isHeader = true
        return header
    }


}