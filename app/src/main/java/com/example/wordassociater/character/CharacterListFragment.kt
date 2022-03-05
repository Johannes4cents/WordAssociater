package com.example.wordassociater.character

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
import com.example.wordassociater.live_recycler.LiveRecycler
import com.example.wordassociater.utils.LiveClass

class CharacterListFragment: Fragment() {
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
        b.liveRecycler.initRecycler(LiveRecycler.Mode.List, LiveRecycler.Type.Character, ::onCharacterSelected, livePartsList)
    }

    private fun setObserver() {
        Main.characterList.observe(context as LifecycleOwner) {
            val liveClassList = mutableListOf<LiveClass>()
            for(char in it) {
                liveClassList.add(char)
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

    private fun onCharacterSelected(character: LiveClass) {
        findNavController().navigate(R.id.action_ViewPagerFragment_to_characterFragment)
    }

}