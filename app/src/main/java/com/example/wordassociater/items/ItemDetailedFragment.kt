package com.example.wordassociater.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wordassociater.databinding.FragmentItemDetailedBinding

class ItemDetailedFragment: Fragment() {
    lateinit var b: FragmentItemDetailedBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentItemDetailedBinding.inflate(inflater)
        return b.root
    }
}