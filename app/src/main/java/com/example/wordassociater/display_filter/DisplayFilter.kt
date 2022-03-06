package com.example.wordassociater.display_filter

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.R
import com.example.wordassociater.firestore.FireFilter


class DisplayFilter(context: Context, attributeSet: AttributeSet): androidx.appcompat.widget.AppCompatImageView(context, attributeSet) {
    private val filterLiveOptions = MutableLiveData<List<FilterOption>>()

    init {
        setImageResource(R.drawable.filter_icon)
        setClickListener()
        filterLiveOptions.value = FilterOption.options
    }



    companion object FilterSettings{
        val barColorDark = MutableLiveData(true)
        val itemColorDark = MutableLiveData(true)
        val wordsShown = MutableLiveData(true)
        val characterShown = MutableLiveData(true)
        val dateShown = MutableLiveData(true)
        val linesShown = MutableLiveData(true)
        val dramaShown = MutableLiveData(true)
        val layerShown = MutableLiveData(true)
        val storyLineShown = MutableLiveData(true)
        val titleShown = MutableLiveData(true)
        val contentShown = MutableLiveData(true)
        val eventsShown = MutableLiveData(true)
        val itemsShown = MutableLiveData(true)
        val locationsShown = MutableLiveData(true)


        fun observeTitleShown(context: Context, view: View) {
            titleShown.observe(context as LifecycleOwner) {
                view.visibility = if(it) View.VISIBLE else View.INVISIBLE
            }
        }

        fun observeItemColorDark(context: Context, view: View, textViewList: List<TextView>) {
            itemColorDark.observe(context as LifecycleOwner) {
                view.setBackgroundColor(if(it) view.context.resources.getColor(R.color.snippets) else view.context.resources.getColor(R.color.white))
                for(view in textViewList) {
                    view.setTextColor(if(it) view.context.resources.getColor(R.color.white) else view.context.resources.getColor(R.color.black))
                }
            }
        }

        fun observeContentShown(context: Context, view: View) {
            contentShown.observe(context as LifecycleOwner) {
                view.visibility = if(it) View.VISIBLE else View.GONE
            }
        }

        fun observeLayerShown(context: Context, view: View) {
            layerShown.observe(context as LifecycleOwner) {
                view.visibility = if(it) View.VISIBLE else View.GONE
            }
        }
        fun observeStoryLineShown(context: Context, view: View) {
            storyLineShown.observe(context as LifecycleOwner) {
                view.visibility = if(it) View.VISIBLE else View.GONE
            }
        }

        fun observeWordsShown(context: Context, wordsRecycler: RecyclerView) {
            wordsShown.observe(context as LifecycleOwner) {
                 wordsRecycler.visibility = if(it) View.VISIBLE else View.GONE
            }
        }

        fun observeCharacterShown(context: Context, characterRecycler: RecyclerView) {
            characterShown.observe(context as LifecycleOwner) {
                characterRecycler.visibility = if(it) View.VISIBLE else View.GONE
            }
        }

        fun observeEventsShown(context: Context, eventRecycler: RecyclerView) {
            eventsShown.observe(context as LifecycleOwner) {
                eventRecycler.visibility = if(it) View.VISIBLE else View.GONE
            }
        }

        fun observeItemsShown(context: Context, itemRecycler: RecyclerView) {
            itemsShown.observe(context as LifecycleOwner) {
                itemRecycler.visibility = if(it) View.VISIBLE else View.GONE
            }
        }

        fun observeLocationsShown(context: Context, locationRecycler: RecyclerView) {
            locationsShown.observe(context as LifecycleOwner) {
                locationRecycler.visibility = if(it) View.VISIBLE else View.GONE
            }
        }

        fun observeDateShown(context: Context, date: View) {
            dateShown.observe(context as LifecycleOwner) {
                date.visibility = if(it) View.VISIBLE else View.INVISIBLE
            }
        }

        fun observeBarColorDark(context: Context, background: View) {
            barColorDark.observe(context as LifecycleOwner) {
                background.setBackgroundColor(if(it) context.resources.getColor(R.color.snippets) else context.resources.getColor(R.color.white))
            }
        }

        fun observeLinesShown(context: Context, lines: List<View>) {
            linesShown.observe(context as LifecycleOwner) {
                for(line in lines) {
                    line.visibility = if(it) View.VISIBLE else View.INVISIBLE
                }
            }
        }


    }

    private fun setClickListener() {
        setOnClickListener {
            popDisplayFilter(this, filterLiveOptions, ::onOptionSelected)
            filterLiveOptions.value = setSelectedOnStart(FilterOption.options)
        }
    }

    private fun setSelectedOnStart(list: List<FilterOption>): List<FilterOption> {
        for (option in list) {
            option.selected =
                    when (option.type) {
                        FilterOption.Type.ItemColorDark -> itemColorDark.value!!
                        FilterOption.Type.BarColorDark -> barColorDark.value!!
                        FilterOption.Type.WordsList -> wordsShown.value!!
                        FilterOption.Type.CharacterList -> characterShown.value!!
                        FilterOption.Type.Date -> dateShown.value!!
                        FilterOption.Type.Divider -> linesShown.value!!
                        FilterOption.Type.Layer -> layerShown.value!!
                        FilterOption.Type.StoryLine -> storyLineShown.value!!
                        FilterOption.Type.Title -> titleShown.value!!
                        FilterOption.Type.Content -> contentShown.value!!
                        FilterOption.Type.LocationList -> locationsShown.value!!
                        FilterOption.Type.EventList -> eventsShown.value!!
                        FilterOption.Type.ItemList -> itemsShown.value!!
                    }
        }
        return list
    }


    private fun onOptionSelected(option: FilterOption) {
        when(option.type) {
            FilterOption.Type.WordsList -> {
                wordsShown.value = !wordsShown.value!!
                FireFilter.update("wordsShown", wordsShown.value!!)
            }
            FilterOption.Type.CharacterList -> {
                characterShown.value = !characterShown.value!!
                FireFilter.update("characterShown", characterShown.value!!)
            }
            FilterOption.Type.Date -> {
                dateShown.value = !dateShown.value!!
                FireFilter.update("dateShown", dateShown.value!!)
            }
            FilterOption.Type.Divider -> {
                linesShown.value = !linesShown.value!!
                FireFilter.update("linesShown", linesShown.value!!)
            }
            FilterOption.Type.BarColorDark -> {
                barColorDark.value = !barColorDark.value!!
                FireFilter.update("barColorDark", barColorDark.value!!)
            }
            FilterOption.Type.Layer -> {
                layerShown.value = !layerShown.value!!
                FireFilter.update("layerShown", layerShown.value!!)
            }
            FilterOption.Type.StoryLine -> {
                storyLineShown.value = !storyLineShown.value!!
                FireFilter.update("storyLineShown", storyLineShown.value!!)
            }
            FilterOption.Type.Title -> {
                titleShown.value = !titleShown.value!!
                FireFilter.update("titleShown", titleShown.value!!)
            }
            FilterOption.Type.Content -> {
                contentShown.value = !contentShown.value!!
                FireFilter.update("contentShown", contentShown.value!!)
            }
            FilterOption.Type.ItemColorDark -> {
                itemColorDark.value = !itemColorDark.value!!
                FireFilter.update("itemColorDark", itemColorDark.value!!)
            }
            FilterOption.Type.LocationList -> {
                locationsShown.value = !locationsShown.value!!
                FireFilter.update("locationsShown", locationsShown.value!!)
            }
            FilterOption.Type.EventList -> {
                eventsShown.value = !eventsShown.value!!
                FireFilter.update("eventsShown", eventsShown.value!!)
            }
            FilterOption.Type.ItemList -> {
                itemsShown.value = !itemsShown.value!!
                FireFilter.update("itemsShown", itemsShown.value!!)
            }
        }
        handleResubmitList(option)
    }

    private fun handleResubmitList(option: FilterOption) {
        val newList = filterLiveOptions.value!!.toMutableList()
        for(o in newList) {
            if(option == o) {
                o.selected = !o.selected
            }
        }
        filterLiveOptions.value = newList
    }

}