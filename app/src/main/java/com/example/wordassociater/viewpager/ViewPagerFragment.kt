package com.example.wordassociater.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wordassociater.StartFragment
import com.example.wordassociater.character.CharacterListFragment
import com.example.wordassociater.databinding.FragmentViewPagerBinding
import com.example.wordassociater.words.WordsListFragment

class ViewPagerFragment: Fragment() {
    lateinit var b: FragmentViewPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentViewPagerBinding.inflate(inflater)
        val fragmentList = arrayListOf<Fragment>(
            StartFragment(),
            CharacterListFragment(),
            WordsListFragment() )

        val adapter = ViewPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)
        b.viewPager.adapter = adapter
        return b.root
    }

}