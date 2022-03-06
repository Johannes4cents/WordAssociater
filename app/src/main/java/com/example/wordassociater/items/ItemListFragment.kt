package com.example.wordassociater.items

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.Frags
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.databinding.FragmentSnippetPartListsBinding
import com.example.wordassociater.databinding.HolderSnippetPartListBinding
import com.example.wordassociater.fire_classes.Item
import com.example.wordassociater.fire_classes.SnippetPart
import com.example.wordassociater.live_recycler.LiveRecycler
import com.example.wordassociater.snippet_parts.SnippetPartDetailedFragment
import com.example.wordassociater.utils.LiveClass

class ItemListFragment: Fragment() {
    lateinit var b: FragmentSnippetPartListsBinding
    private val livePartsList = MutableLiveData<List<LiveClass>>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        Main.inFragment = Frags.SNIPPETPARTS
        b = FragmentSnippetPartListsBinding.inflate(layoutInflater)
        setLiveRecycler()
        setObserver()
        setClickListener()
        setImportanceBar()
        return b.root
    }

    private fun setLiveRecycler() {
        val listHolder = ItemHolderList(HolderSnippetPartListBinding.inflate(LayoutInflater.from(context), null, false))
        Log.i("HolderProb", "listHolder is $listHolder")
        b.liveRecycler.initRecycler(LiveRecycler.Mode.List, LiveRecycler.Type.Item, ::onItemSelected, livePartsList)
    }

    private fun setImportanceBar() {
        b.importanceBar.setNewSnippetPartButton(SnippetPart.Type.Item, findNavController())
    }

    private fun setObserver() {
        Main.itemList.observe(context as LifecycleOwner) {
            val liveClassList = mutableListOf<LiveClass>()
            for(item in it) {
                liveClassList.add(item)
            }
            livePartsList.value = liveClassList
        }
    }

    private fun setClickListener() {
        val itemList = Main.itemList.value!!.toMutableList()
        val mainItems = itemList.filter { c -> c.importance == SnippetPart.Importance.Main }
        val sideItems = itemList.filter { c -> c.importance == SnippetPart.Importance.Side }
        val mentionedItems = itemList.filter { c -> c.importance == SnippetPart.Importance.Mentioned }

        b.importanceBar.setMainFunc {
            livePartsList.value = mainItems
        }

        b.importanceBar.setSideFunc {
            livePartsList.value = sideItems
        }

        b.importanceBar.setMentionedFunc {
            livePartsList.value = mentionedItems
        }
    }

    private fun onItemSelected(item: LiveClass) {
        SnippetPartDetailedFragment.snippetPart = item as Item
        findNavController().navigate(R.id.action_ViewPagerFragment_to_snippetPartDetailedFragment)
    }

}