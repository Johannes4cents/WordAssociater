package com.example.wordassociater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.wordassociater.character.CharacterListFragment
import com.example.wordassociater.databinding.FragmentViewPagerBinding
import com.example.wordassociater.events.EventsListFragment
import com.example.wordassociater.items.ItemListFragment
import com.example.wordassociater.locations.LocationsListFragment
import com.example.wordassociater.notes.NotesFragment
import com.example.wordassociater.nuw.NuwsListFragment
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
                Page.Notes -> 0
                Page.Items -> 1
                Page.Events -> 2
                Page.Locations -> 3
                Page.Chars -> 4
                Page.Start -> 5
                Page.Words -> 6
                Page.Nuws -> 7

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
                NotesFragment(),
                ItemListFragment(),
                EventsListFragment(),
                LocationsListFragment(),
                CharacterListFragment(),
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