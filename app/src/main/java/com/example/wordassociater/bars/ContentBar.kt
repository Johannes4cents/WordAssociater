package com.example.wordassociater.bars

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.wordassociater.R
import com.example.wordassociater.databinding.BarContentBinding

class ContentBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = BarContentBinding.inflate(LayoutInflater.from(context), this, true)
    lateinit var navController: NavController
    init {
        setClickListener()
        setObserver()
    }

    private fun setClickListener() {
        b.btnStoryLines.setOnClickListener {
            findNavController().navigate(R.id.action_ViewPagerFragment_to_storyLinesListFragment)
        }


        b.btnSnippets.setOnClickListener {
            navController.navigate(R.id.action_startFragment_to_snippetFragment)
        }
    }

    private fun setObserver() {
        AddStuffBar.snippetInputOpen.observe(context as LifecycleOwner) {
            if(it) {
                b.root.visibility = View.GONE
            }
            else {
                b.root.visibility = View.VISIBLE
            }
        }

        AddStuffBar.newWordInputOpen.observe(context as LifecycleOwner) {
            if(it) {
                b.root.visibility = View.GONE
            }
            else {
                b.root.visibility = View.VISIBLE
            }
        }
    }
}