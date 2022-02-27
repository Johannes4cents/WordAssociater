package com.example.wordassociater.nuw

import android.content.Context
import android.util.AttributeSet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.fire_classes.Nuw

class NuwRecycler(context: Context, attributeSet: AttributeSet): RecyclerView(context, attributeSet) {
    lateinit var nuwAdapter: NuwAdapter
    lateinit var nuwLiveList : MutableLiveData<List<Nuw>?>

    fun initRecycler(
            liveList: MutableLiveData<List<Nuw>?>,
            onUpgradeClicked: (nuw: Nuw) -> Unit,
            onDirtyClicked: (nuw: Nuw) -> Unit,
            onPotatoClicked: (nuw: Nuw) -> Unit,
            onRootClicked: (nuw: Nuw) -> Unit) {
        nuwAdapter = NuwAdapter(onUpgradeClicked,onDirtyClicked, onPotatoClicked, onRootClicked)
        adapter = nuwAdapter
        nuwLiveList = liveList
        setObserver()
    }

    private fun setObserver() {
        nuwLiveList.observe(context as LifecycleOwner) { list ->
            val orderedList = list?.sortedBy { it.usedAmount }?.sortedBy { it.isWord }?.sortedBy { it.isUsed }?.reversed()
            nuwAdapter.submitList(orderedList)
            nuwAdapter.notifyDataSetChanged()
        }
    }
}