package com.example.wordassociater.snippet_parts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.wordassociater.R
import com.example.wordassociater.character.CharacterListFragment
import com.example.wordassociater.databinding.FragmentViewPagerSnippetPartsBinding
import com.example.wordassociater.events.EventsListFragment
import com.example.wordassociater.items.ItemListFragment
import com.example.wordassociater.locations.LocationsListFragment
import com.example.wordassociater.utils.Page
import com.example.wordassociater.viewpager.ViewPagerAdapter

class ViewPagerSnippetPartsFragment: Fragment() {
    lateinit var b: FragmentViewPagerSnippetPartsBinding
    enum class SnippetPartsPage { Character, Items, Events, Locations }



    companion object {
        lateinit var viewPager: ViewPager2
        var comingFrom = Page.Start

        fun goTopage(page: SnippetPartsPage) {
            viewPager.setCurrentItem(
                    when(page) {
                        SnippetPartsPage.Character -> 0
                        SnippetPartsPage.Locations -> 1
                        SnippetPartsPage.Items -> 2
                        SnippetPartsPage.Events -> 3
                    }, false
            )
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        b = FragmentViewPagerSnippetPartsBinding.inflate(inflater)
        val fragmentList = arrayListOf<Fragment>(
                CharacterListFragment(),
                LocationsListFragment(),
                ItemListFragment(),
                EventsListFragment()
)

        val adapter = ViewPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)
        viewPager = b.viewPager
        viewPager.isNestedScrollingEnabled = true
        ViewCompat.setNestedScrollingEnabled(viewPager, true)
        b.viewPager.adapter = adapter
        setTopBar()
        return b.root
    }

    private fun setTopBar() {
        // set Icons
        b.topBar.setRightBtnIconAndVisibility(R.drawable.back_icon, true)
        b.topBar.setLeftBtnIconAndVisibility(R.drawable.icon_word, false)
        b.topBar.setBtn5IconAndVisibility(R.drawable.icon_word, false)
        b.topBar.setBtn1IconAndVisibility(R.drawable.icon_item, true)
        b.topBar.setBtn2IconAndVisibility(R.drawable.event_icon, true)
        b.topBar.setBtn3IconAndVisibility(R.drawable.icon_location, true)
        b.topBar.setBtn3IconAndVisibility(R.drawable.icon_character, true)

        // set OnClick
        b.topBar.setBtn1 {
            goTopage(SnippetPartsPage.Items)
        }

        b.topBar.setBtn2 {
            goTopage(SnippetPartsPage.Events)
        }

        b.topBar.setBtn3 {
            goTopage(SnippetPartsPage.Locations)
        }

        b.topBar.setBtn4 {
            goTopage(SnippetPartsPage.Character)
        }


    }

}