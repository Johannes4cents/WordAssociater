package com.example.wordassociater.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wordassociater.R
import com.example.wordassociater.databinding.FragmentEventsListBinding

class EventsListFragment: Fragment() {
    lateinit var b : FragmentEventsListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentEventsListBinding.inflate(inflater)
        setTopBar()
        return b.root
    }

    private fun setTopBar() {
        b.topBar.setLeftBtnIconAndVisibility(R.drawable.icon_word, false)
        b.topBar.setRightBtnIconAndVisibility(R.drawable.back_icon, true)
        b.topBar.setRightButton {

        }
    }
}