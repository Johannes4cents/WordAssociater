package com.example.wordassociater.nuw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wordassociater.databinding.FragmentNuwsListBinding

class NuwsListFragment: Fragment() {
    lateinit var b : FragmentNuwsListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentNuwsListBinding.inflate(inflater)
        return b.root
    }

    private fun setTopBar() {
        b.topBar.setLeftBtn {

        }

        b.topBar.setBtn1 {

        }

        b.topBar.setBtn2 {

        }

        b.topBar.setBtn3 {

        }

        b.topBar.setBtn4 {

        }

        b.topBar.setBtn5{

        }
    }
}