package com.example.wordassociater.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wordassociater.databinding.FragmentEventDetailedBinding

class EventDetailedFragment: Fragment() {
    lateinit var b : FragmentEventDetailedBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentEventDetailedBinding.inflate(inflater)
        return b.root
    }
}