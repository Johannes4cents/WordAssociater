package com.example.wordassociater.snippet_parts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.widget.ViewPager2
import com.example.wordassociater.R
import com.example.wordassociater.character.CharacterListFragment
import com.example.wordassociater.databinding.FragmentViewPagerSnippetPartsBinding
import com.example.wordassociater.events.EventsListFragment
import com.example.wordassociater.items.ItemListFragment
import com.example.wordassociater.locations.LocationsListFragment
import com.example.wordassociater.utils.Page
import com.example.wordassociater.viewpager.ViewPagerAdapter
import com.example.wordassociater.viewpager.ViewPagerMainFragment

class ViewPagerSnippetPartsFragment: Fragment() {
    lateinit var b: FragmentViewPagerSnippetPartsBinding
    enum class SnippetPartsPage { Character, Items, Events, Locations }
    private val selectedCat = MutableLiveData<View>()



    companion object {
        lateinit var viewPager: ViewPager2
        var comingFrom = SnippetPartsPage.Character

        fun goTopage(page: SnippetPartsPage) {
            viewPager.currentItem = when(page) {
                SnippetPartsPage.Items -> 0
                SnippetPartsPage.Events -> 1
                SnippetPartsPage.Locations -> 2
                SnippetPartsPage.Character -> 3
            }
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        b = FragmentViewPagerSnippetPartsBinding.inflate(inflater)
        val fragmentList = arrayListOf<Fragment>(
                ItemListFragment(),
                EventsListFragment(),
                LocationsListFragment(),
                CharacterListFragment()
)

        val adapter = ViewPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)
        viewPager = b.viewPager
        viewPager.isNestedScrollingEnabled = true
        ViewCompat.setNestedScrollingEnabled(viewPager, true)
        b.viewPager.adapter = adapter
        setObserver()
        setTopBar()
        goTopage(comingFrom)

        return b.root
    }


    private fun setTopBar() {
        // set Icons
        b.topBar.setLeftBtnIconAndVisibility(R.drawable.icon_word, false)
        b.topBar.setBtn5IconAndVisibility(R.drawable.icon_word, false)
        b.topBar.setBtn1IconAndVisibility(R.drawable.icon_item, true)
        b.topBar.setBtn2IconAndVisibility(R.drawable.event_icon, true)
        b.topBar.setBtn3IconAndVisibility(R.drawable.icon_location, true)
        b.topBar.setBtn4IconAndVisibility(R.drawable.icon_character, true)
        b.topBar.setRightBtnIconAndVisibility(R.drawable.back_icon_mirrored, true)
        b.topBar.btnLeft.visibility = View.GONE

        b.topBar.setRightButton {
            ViewPagerMainFragment.goTopage(Page.Start)
        }

        // set OnClick
        b.topBar.setBtn1 {
            selectedCat.value = b.topBar.btn1
            goTopage(SnippetPartsPage.Items)
        }

        b.topBar.setBtn2 {
            selectedCat.value = b.topBar.btn2
            goTopage(SnippetPartsPage.Events)
        }

        b.topBar.setBtn3 {
            selectedCat.value = b.topBar.btn3
            goTopage(SnippetPartsPage.Locations)
        }

        b.topBar.setBtn4 {
            selectedCat.value = b.topBar.btn4
            goTopage(SnippetPartsPage.Character)
        }

    }

    private fun setObserver() {
        selectedCat.observe(context as LifecycleOwner) {
            b.topBar.btn1.setImageResource(if(it == b.topBar.btn1) R.drawable.icon_item else R.drawable.icon_item_unselected)
            b.topBar.btn2.setImageResource(if(it == b.topBar.btn2) R.drawable.event_icon else R.drawable.event_icon_unselected)
            b.topBar.btn3.setImageResource(if(it == b.topBar.btn3) R.drawable.icon_location else R.drawable.icon_location_unselected)
            b.topBar.btn4.setImageResource(if(it == b.topBar.btn4) R.drawable.icon_character else R.drawable.icon_character_unselected)
        }

        selectedCat.value = b.topBar.btn4
    }

}