package com.example.wordassociater.merge_words

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wordassociater.databinding.FragmentMergeWordsBinding

class MergeWordsFragment: Fragment() {
    lateinit var b : FragmentMergeWordsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentMergeWordsBinding.inflate(inflater)
        return b.root
    }

    private fun setTopBar() {

    }
}