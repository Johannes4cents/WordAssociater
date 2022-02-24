package com.example.wordassociater.snippets

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.Frags
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.ViewPagerFragment
import com.example.wordassociater.databinding.FragmentSnippetsBinding
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.utils.Helper
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

    private fun setSnippetRecycler() {
        snippetAdapter = SnippetAdapter(::snippetClickedFunc)
        b.snippetsRecycler.adapter = snippetAdapter
    }

    private fun setSearchBar() {
        b.searchSnippetsInput.searchWords.observe(viewLifecycleOwner) {
            if(it.isNotEmpty()) snippetAdapter.submitList(Helper.getOrFilteredStoryPartList(it, Main.snippetList.value!!) as List<Snippet>)
            else snippetAdapter.submitList(Main.snippetList.value)
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
        snippetAdapter.submitList(Main.snippetList.value)
        Main.snippetList.observe(viewLifecycleOwner) {
            snippetAdapter.submitList(it)
        }


    }
}