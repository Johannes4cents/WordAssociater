package com.example.wordassociater.fams

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.fire_classes.Fam
import com.example.wordassociater.utils.CommonWord

class FamRecycler(context: Context, attributeSet: AttributeSet): RecyclerView(context, attributeSet) {
    enum class Type { List, Popup}
    lateinit var type: Type
    lateinit var famAdapter: FamAdapter
    lateinit var famList: MutableLiveData<List<Fam>>


    fun initRecycler(type: Type, famList: MutableLiveData<List<Fam>>,
                     onFamClicked: (fam: Fam) -> Unit,
                     onUpgradeFam: (fam: Fam) -> Unit,
                     onMakeCommonWord: (fam: Fam, type: CommonWord.Type) -> Unit)
                     {
        famAdapter = FamAdapter(type, onFamClicked, onUpgradeFam, onMakeCommonWord)
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        this.type = type
        this.famList = famList
        adapter = famAdapter
        setObserver()
    }

    fun setObserver() {
        famList.observe(context as LifecycleOwner) {
            if(type != Type.Popup) {
                famAdapter.submitList(it.sortedBy { f -> f.id }.reversed())
            }
            else {
                famAdapter.submitList(it)
            }

        }
    }


}