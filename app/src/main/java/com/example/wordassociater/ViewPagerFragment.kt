package com.example.wordassociater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.wordassociater.character.CharacterListFragment
import com.example.wordassociater.databinding.FragmentViewPagerBinding
import com.example.wordassociater.utils.Page
import com.example.wordassociater.viewpager.ViewPagerAdapter
import com.example.wordassociater.words.WordsListFragment

class ViewPagerFragment: Fragment() {
    lateinit var b: FragmentViewPagerBinding

    companion object {
        lateinit var viewPager: ViewPager2
        var comingFrom = Page.Start

        fun goTopage(page: Page) {
            viewPager.setCurrentItem(when(page) {
                Page.Chars -> 0
                Page.Start -> 1
                Page.Words -> 2
            }, false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentViewPagerBinding.inflate(inflater)
        val fragmentList = arrayListOf<Fragment>(
            CharacterListFragment(),
            StartFragment(),
            WordsListFragment() )

        val adapter = ViewPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)
        viewPager = b.viewPager
        b.viewPager.adapter = adapter
        goTopage(comingFrom)
        return b.root
    }

}