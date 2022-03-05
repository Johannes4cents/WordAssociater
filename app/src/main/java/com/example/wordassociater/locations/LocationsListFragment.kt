package com.example.wordassociater.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.Frags
import com.example.wordassociater.Main
import com.example.wordassociater.databinding.FragmentSnippetPartListsBinding
import com.example.wordassociater.databinding.HolderSnippetPartListBinding
import com.example.wordassociater.live_recycler.LiveRecycler
import com.example.wordassociater.utils.LiveClass

class LocationsListFragment: Fragment() {
    lateinit var b: FragmentSnippetPartListsBinding
    private val livePartsList = MutableLiveData<List<LiveClass>>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        Main.inFragment = Frags.CHARACTERLIST
        b = FragmentSnippetPartListsBinding.inflate(layoutInflater)
        setLiveRecycler()
        setObserver()
        setClickListener()
        return b.root
    }

    private fun setLiveRecycler() {
        val listHolder = LocationHolderList(HolderSnippetPartListBinding.inflate(LayoutInflater.from(context), null, false))
        b.liveRecycler.initRecycler(LiveRecycler.Mode.List, LiveRecycler.Type.Location, ::onItemSelected, livePartsList)
    }

    private fun setObserver() {
        Main.locationList.observe(context as LifecycleOwner) {
            val liveClassList = mutableListOf<LiveClass>()
            for(item in it) {
                liveClassList.add(item)
            }
            livePartsList.value = liveClassList
        }
    }

    private fun setClickListener() {
        b.importanceBar.setMainFunc {

        }

        b.importanceBar.setSideFunc {

        }

        b.importanceBar.setMentionedFunc {

        }
    }

    private fun onItemSelected(item: LiveClass) {
        //findNavController().navigate()
    }

}