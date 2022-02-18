package com.example.wordassociater.snippet_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.Frags
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.databinding.FragmentSnippetsBinding
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
        return b.root
    }

    private fun setSnippetRecycler() {
        snippetAdapter = SnippetAdapter()
        b.snippetsRecycler.adapter = snippetAdapter
    }



    private fun setClickListener() {
        b.backBtn.setOnClickListener {
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