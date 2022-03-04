package com.example.wordassociater.wordcat

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.viewpager.ViewPagerMainFragment
import com.example.wordassociater.fire_classes.WordCat


class WordCatRecycler(context: Context, attributeSet: AttributeSet): RecyclerView(context, attributeSet) {
    private lateinit var wordCatAdapter: WordCatAdapter
    private var headerActive = true
    private lateinit var type: WordCatAdapter.Type

    fun setupRecycler(
        type: WordCatAdapter.Type,
        onWordCatSelected: (wordCat: WordCat) -> Unit,
        liveList: MutableLiveData<List<WordCat>>,
        onHeaderClicked: (() -> Unit)? = null) {
        this.type = type
        wordCatAdapter = WordCatAdapter(type, onWordCatSelected, onHeaderClicked)
        adapter = wordCatAdapter
        setObserver(liveList)
        configureOnTouchListener()
        isNestedScrollingEnabled = true
    }

    fun setHeader(headerActive: Boolean) {
        this.headerActive = headerActive
    }

    private fun configureOnTouchListener() {
        this.addOnItemTouchListener(object : OnItemTouchListener {
            var lastX = 0
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                when (e.action) {
                    MotionEvent.ACTION_DOWN -> lastX = e.x.toInt()
                    MotionEvent.ACTION_MOVE -> {
                        ViewPagerMainFragment.viewPager.isUserInputEnabled = false
                    }
                    MotionEvent.ACTION_UP -> {
                        lastX = 0
                        ViewPagerMainFragment.viewPager.isUserInputEnabled = true
                    }
                }
                return false
            }
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        })
    }


    private fun setObserver(liveList: MutableLiveData<List<WordCat>>) {
        liveList.observe(context as LifecycleOwner) {
            val sortedList = if(type == WordCatAdapter.Type.BTN) {
                it?.filter { wc -> wc.active }?.sortedBy { wc -> wc.name }
                    ?.sortedBy { wc -> wc.id }?.reversed() }
                else {
                    it?.sortedBy { wc -> wc.name }
                        ?.sortedBy { wc -> wc.id }?.reversed()
                }
            val header = WordCat(0, "Manage")
            header.isHeader = true
            if(it != null) {
                if(headerActive) wordCatAdapter.submitList(sortedList!! + listOf(header))
                else wordCatAdapter.submitList(sortedList)

            }
            else {
                if(headerActive) wordCatAdapter.submitList(listOf(header))
            }
            wordCatAdapter.notifyDataSetChanged()
        }
    }

}