package com.example.wordassociater.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.wordassociater.Frags
import com.example.wordassociater.Main
import com.example.wordassociater.StartFragment
import com.example.wordassociater.databinding.FragmentViewPagerBinding
import com.example.wordassociater.notes.NotesFragment
import com.example.wordassociater.nuw.NuwsListFragment
import com.example.wordassociater.snippet_parts.ViewPagerSnippetPartsFragment
import com.example.wordassociater.utils.Page
import com.example.wordassociater.words.WordsListFragment

class ViewPagerMainFragment: Fragment() {
    lateinit var b: FragmentViewPagerBinding



    companion object {
        lateinit var viewPager: ViewPager2
        var comingFrom = Page.Start

        fun goTopage(page: Page) {
            viewPager.setCurrentItem(
                when (page) {
                    Page.Notes -> {
                        Main.inFragment = Frags.NOTES
                        0
                    }
                    Page.SnippetParts -> {
                        Main.inFragment = Frags.CHARACTERLIST
                        1
                    }
                    Page.Start -> {
                        Main.inFragment = Frags.START
                        2
                    }
                    Page.Words -> {
                        Main.inFragment = Frags.WORDLIST
                        3
                    }
                    Page.Nuws -> {
                        Main.inFragment = Frags.NUWSLIST
                        4
                    }

                }, false
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentViewPagerBinding.inflate(inflater)
        val fragmentList = arrayListOf<Fragment>(
                NotesFragment(),
                ViewPagerSnippetPartsFragment(),
                StartFragment(),
                WordsListFragment(),
                NuwsListFragment())

        val adapter = ViewPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)
        viewPager = b.viewPager
        viewPager.isNestedScrollingEnabled = true
        ViewCompat.setNestedScrollingEnabled(viewPager, true)
        b.viewPager.adapter = adapter
        goTopage(comingFrom)
        return b.root
    }

}