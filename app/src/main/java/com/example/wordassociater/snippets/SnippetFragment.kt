package com.example.wordassociater.snippets

import android.os.Bundle
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
import com.example.wordassociater.databinding.FragmentSnippetsBinding
import com.example.wordassociater.databinding.HolderSnippetBinding
import com.example.wordassociater.fire_classes.Snippet

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
        handleLayerButton()
        attachSnippetBinding()
        return b.root
    }

    private fun attachSnippetBinding() {
        val snippetBinding = HolderSnippetBinding.inflate(layoutInflater, b.pinnedSnippetContainer, true)
        snippetBinding.root.visibility = View.GONE
        b.pinBar.takeBindingAndNavController(snippetBinding, findNavController(), R.id.action_snippetFragment_to_editSnippetFragment)
    }

    private fun handleLayerButton() {
        b.layerButton.setClickListener()
        b.layerButton.currentLayer.observe(context as LifecycleOwner) {
            var filteredList = Main.snippetList.value?.filter { snippet -> snippet.layer >= it }?.sortedBy { s -> s.id }?.reversed()
            snippetAdapter.submitList(filteredList)
        }
    }

    private fun setSnippetRecycler() {
        snippetAdapter = SnippetAdapter(::snippetClickedFunc)
        b.snippetsRecycler.adapter = snippetAdapter
    }

    private fun setSearchBar() {
        b.searchSnippetsInput.setHint("Search")
        b.searchSnippetsInput.setTextColorToWhite()
        b.searchSnippetsInput.setGravityToCenter()
        b.searchSnippetsInput.getSnippets {
            if(it.isEmpty()) snippetAdapter.submitList(Main.snippetList.value?.sortedBy { s -> s.id }?.reversed())
            else snippetAdapter.submitList(it.sortedBy { s -> s.id }.reversed())
        }
    }

    private fun snippetClickedFunc(snippet: Snippet) {
        b.snippetsRecycler.smoothScrollToPosition(0)
        b.pinBar.attachSnippet(snippet)
    }

    private fun setClickListener() {

    }

    private fun setObserver() {
        Main.snippetList.observe(viewLifecycleOwner) {
            if(it != null) {
            snippetAdapter.submitList(it.sortedBy { s -> s.id }.reversed()) }
            b.snippetsRecycler.smoothScrollToPosition(0)
        }


    }
}