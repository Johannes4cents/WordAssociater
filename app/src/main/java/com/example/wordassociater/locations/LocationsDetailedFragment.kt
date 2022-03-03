package com.example.wordassociater.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wordassociater.databinding.FragmentLocationDetailedBinding

class LocationsDetailedFragment: Fragment() {
    lateinit var b: FragmentLocationDetailedBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentLocationDetailedBinding.inflate(inflater)
        return b.root
    }
}