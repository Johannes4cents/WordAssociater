package com.example.wordassociater.live_recycler


import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.utils.LiveClass


class LiveRecycler(context: Context, attributeSet: AttributeSet): RecyclerView(context, attributeSet) {
    enum class Mode { Popup, List, Preview }

    fun initRecycler(
            mode: Mode,
            onItemClicked: (item: LiveClass) -> Unit,
            liveList: MutableLiveData<List<LiveClass>>,
            holder: RecyclerView.ViewHolder,
            onHeaderClicked: (() -> Unit)? = null) {

        val liveAdapter = LiveAdapter(onItemClicked, holder, onHeaderClicked)
        layoutManager = if(mode == Mode.Popup) LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false) else LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = liveAdapter
        liveList.observe(context as LifecycleOwner) {
            val selectedList = it.filter { item -> item.selected && item.id != 0L }.sortedBy { i -> i.sortingOrder }
            val allList = it.filter { item -> item.id != 0L }.sortedBy { i -> i.sortingOrder }
            liveAdapter.submitList(if(mode == Mode.Preview) selectedList else allList)
        }


    }

}

class LiveAdapter(
        private val onItemClicked: (item: LiveClass) -> Unit,
        private val holder: RecyclerView.ViewHolder,
        private val onHeaderClicked: (() -> Unit)? = null,


) : ListAdapter<LiveClass, RecyclerView.ViewHolder>(LiveDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as LiveHolder).onBind(item, onItemClicked)
    }

}

class  LiveDiff<T: LiveClass>: DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.id == newItem.id

    }
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.selected == newItem.selected
    }
}

interface LiveHolder {
    var item: LiveClass
    abstract fun onBind(item: LiveClass, takeItemFunc: (item: LiveClass) -> Unit)
}