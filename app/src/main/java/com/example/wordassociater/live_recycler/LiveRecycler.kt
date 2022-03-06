package com.example.wordassociater.live_recycler


import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.character.CharacterHolderList
import com.example.wordassociater.character.CharacterHolderPopUp
import com.example.wordassociater.character.CharacterHolderPreview
import com.example.wordassociater.databinding.*
import com.example.wordassociater.events.EventHolderList
import com.example.wordassociater.events.EventHolderPopUp
import com.example.wordassociater.events.EventHolderPreview
import com.example.wordassociater.items.ItemHolderList
import com.example.wordassociater.items.ItemHolderPopUp
import com.example.wordassociater.items.ItemHolderPreview
import com.example.wordassociater.locations.LocationHolderList
import com.example.wordassociater.locations.LocationHolderPopup
import com.example.wordassociater.locations.LocationHolderPreview
import com.example.wordassociater.storylines.StoryLineHolderList
import com.example.wordassociater.storylines.StoryLineHolderPreview
import com.example.wordassociater.utils.LiveClass
import com.example.wordassociater.words.WordHolderList
import com.example.wordassociater.words.WordHolderPopup
import com.example.wordassociater.words.WordHolderPreview


class LiveRecycler(context: Context, attributeSet: AttributeSet): RecyclerView(context, attributeSet) {
    enum class Mode { Popup, List, Preview }
    enum class Type { Character, Item, Event, Location, StoryLine, Word }

    fun initRecycler(
            mode: Mode,
            type: Type,
            onItemClicked: ((item: LiveClass) -> Unit)?,
            liveList: MutableLiveData<List<LiveClass>>,
            onHeaderClicked: (() -> Unit)? = null) {

        val liveAdapter = LiveAdapter(mode, type, onItemClicked, onHeaderClicked)
        layoutManager = if(mode == Mode.Preview) LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false) else LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = liveAdapter
        liveList.observe(context as LifecycleOwner) {
            val selectedList = it.filter { item -> item.selected && item.id != 0L }.sortedBy { i -> i.sortingOrder }
            val allList = it.filter { item -> item.id != 0L }.sortedBy { i -> i.sortingOrder }
            liveAdapter.submitList(if(mode == Mode.Preview) selectedList else allList)
            liveAdapter.notifyDataSetChanged()
        }


    }

}

class LiveAdapter(
        private val mode: LiveRecycler.Mode,
        private val type: LiveRecycler.Type,
        private val onItemClicked: ((item: LiveClass) -> Unit)?,
        private val onHeaderClicked: (() -> Unit)? = null,


) : ListAdapter<LiveClass, RecyclerView.ViewHolder>(LiveDiff()) {

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if(!item.isAHeader) 1 else 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(mode) {
            LiveRecycler.Mode.Popup -> when(type) {
                LiveRecycler.Type.Character -> CharacterHolderPopUp(HolderSnippetPartPopupBinding.inflate(LayoutInflater.from(parent.context)))
                LiveRecycler.Type.Item -> ItemHolderPopUp(HolderSnippetPartPopupBinding.inflate(LayoutInflater.from(parent.context)))
                LiveRecycler.Type.Event -> EventHolderPopUp(HolderSnippetPartPopupBinding.inflate(LayoutInflater.from(parent.context)))
                LiveRecycler.Type.Location -> LocationHolderPopup(HolderSnippetPartPopupBinding.inflate(LayoutInflater.from(parent.context)))
                LiveRecycler.Type.StoryLine -> StoryLineHolderList(HolderStorylineBinding.inflate(LayoutInflater.from(parent.context)))
                LiveRecycler.Type.Word -> WordHolderPopup(HolderWordPopupBinding.inflate(LayoutInflater.from(parent.context)))
            }
            LiveRecycler.Mode.List -> when(type) {
                LiveRecycler.Type.Character -> CharacterHolderList(HolderSnippetPartListBinding.inflate(LayoutInflater.from(parent.context)))
                LiveRecycler.Type.Item -> ItemHolderList(HolderSnippetPartListBinding.inflate(LayoutInflater.from(parent.context)))
                LiveRecycler.Type.Event -> EventHolderList(HolderSnippetPartListBinding.inflate(LayoutInflater.from(parent.context)))
                LiveRecycler.Type.Location -> LocationHolderList(HolderSnippetPartListBinding.inflate(LayoutInflater.from(parent.context)))
                LiveRecycler.Type.StoryLine -> StoryLineHolderList(HolderStorylineBinding.inflate(LayoutInflater.from(parent.context)))
                LiveRecycler.Type.Word -> WordHolderList(HolderWordListBinding.inflate(LayoutInflater.from(parent.context)))
            }
            LiveRecycler.Mode.Preview -> when(type) {
                LiveRecycler.Type.Character -> CharacterHolderPreview(HolderSnippetPartPreviewBinding.inflate(LayoutInflater.from(parent.context)))
                LiveRecycler.Type.Item -> ItemHolderPreview(HolderSnippetPartPreviewBinding.inflate(LayoutInflater.from(parent.context)))
                LiveRecycler.Type.Event -> EventHolderPreview(HolderSnippetPartPreviewBinding.inflate(LayoutInflater.from(parent.context)))
                LiveRecycler.Type.Location -> LocationHolderPreview(HolderSnippetPartPreviewBinding.inflate(LayoutInflater.from(parent.context)))
                LiveRecycler.Type.StoryLine -> StoryLineHolderPreview(HolderStorylineSimpleBinding.inflate(LayoutInflater.from(parent.context)))
                LiveRecycler.Type.Word -> WordHolderPreview(HolderWordPreviewBinding.inflate(LayoutInflater.from(parent.context)))
            }
        }
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
    abstract fun onBind(item: LiveClass, takeItemFunc: ((item: LiveClass) -> Unit)?)
}