package com.example.wordassociater.events

import android.os.Bundle
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
import com.example.wordassociater.fire_classes.Event
import com.example.wordassociater.fire_classes.SnippetPart
import com.example.wordassociater.live_recycler.LiveRecycler
import com.example.wordassociater.snippet_parts.SnippetPartDetailedFragment
import com.example.wordassociater.utils.LiveClass

class EventsListFragment: Fragment() {
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
        b.liveRecycler.initRecycler(LiveRecycler.Mode.List, LiveRecycler.Type.Event, ::onItemSelected, livePartsList)
    }

    private fun setImportanceBar() {
        b.importanceBar.setNewSnippetPartButton(SnippetPart.Type.Event, findNavController())
    }

    private fun setObserver() {
        Main.eventList.observe(context as LifecycleOwner) {
            val liveClassList = mutableListOf<LiveClass>()
            for(item in it) {
                liveClassList.add(item)
            }
            livePartsList.value = liveClassList
        }
    }

    private fun setClickListener() {
        val eventList = Main.eventList.value!!.toMutableList()
        val mainEvents = eventList.filter { c -> c.importance == SnippetPart.Importance.Main }
        val sideEvents = eventList.filter { c -> c.importance == SnippetPart.Importance.Side }
        val mentionedEvents = eventList.filter { c -> c.importance == SnippetPart.Importance.Mentioned }
        b.importanceBar.setMainFunc {
            livePartsList.value = mainEvents
        }

        b.importanceBar.setSideFunc {
            livePartsList.value = sideEvents
        }

        b.importanceBar.setMentionedFunc {
            livePartsList.value = mentionedEvents
        }
    }

    private fun onItemSelected(item: LiveClass) {
        SnippetPartDetailedFragment.snippetPart = item as Event
        findNavController().navigate(R.id.action_ViewPagerFragment_to_snippetPartDetailedFragment)
    }

}