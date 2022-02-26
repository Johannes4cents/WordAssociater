package com.example.wordassociater.snippets

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.Frags
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.ViewPagerFragment
import com.example.wordassociater.databinding.FragmentSnippetsBinding
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.utils.Page

class SnippetFragment: Fragment() {
    lateinit var b: FragmentSnippetsBinding

    companion object {
        lateinit var navController: NavController
        var selectedSnippet : Snippet? = null
        lateinit var snippetAdapter: SnippetAdapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentSnippetsBinding.inflate(inflater)
        Main.inFragment = Frags.SNIPPET
        navController = findNavController()
        setSnippetRecycler()
        setClickListener()
        setObserver()
        setSearchBar()
        return b.root
    }

    private fun handleLayerButton() {
        b.strainLayerButton.setClickListener()
        b.strainLayerButton.currentLayer.observe(context as LifecycleOwner) {
            var filteredList = Main.snippetList.value?.filter { snippet -> snippet.layer >= it }?.sortedBy { s -> s.id }?.reversed()
            snippetAdapter.submitList(filteredList)
        }
    }

    private fun setSnippetRecycler() {
        snippetAdapter = SnippetAdapter(::snippetClickedFunc)
        b.snippetsRecycler.adapter = snippetAdapter
    }

    private fun setSearchBar() {
        b.searchSnippetsInput.getSnippets {
            if(it.isEmpty()) snippetAdapter.submitList(Main.snippetList.value?.sortedBy { s -> s.id }?.reversed())
            else snippetAdapter.submitList(it.sortedBy { s -> s.id }.reversed())
        }
    }

    private fun snippetClickedFunc(snippet: Snippet) {
        Log.i("characterProb", "oldSnippet set")
        EditSnippetFragment.oldSnippet = snippet.copyMe()
        EditSnippetFragment.snippet = snippet
        findNavController().navigate(R.id.action_snippetFragment_to_editSnippetFragment)
    }

    private fun setClickListener() {
        b.backBtn.setOnClickListener {
            ViewPagerFragment.comingFrom = Page.Start
            findNavController().navigate(R.id.action_snippetFragment_to_startFragment)
        }
    }

    private fun setObserver() {
        Main.snippetList.observe(viewLifecycleOwner) {
            if(it != null) {
            snippetAdapter.submitList(it.sortedBy { s -> s.id }.reversed()) }
            b.snippetsRecycler.smoothScrollToPosition(0)
        }


    }
}