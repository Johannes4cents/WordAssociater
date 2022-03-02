package com.example.wordassociater.display_filter

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.R


class DisplayFilter(context: Context, attributeSet: AttributeSet): androidx.appcompat.widget.AppCompatImageView(context, attributeSet) {
    private val filterLiveOptions = MutableLiveData<List<FilterOption>>()

    init {
        setImageResource(R.drawable.filter_icon)
        setClickListener()
        filterLiveOptions.value = FilterOption.options
    }

    companion object {
        val barColorDark = MutableLiveData<Boolean>(true)
        val wordsShown = MutableLiveData<Boolean>(true)
        val characterShown = MutableLiveData<Boolean>(true)
        val dateShown = MutableLiveData<Boolean>(true)
        val linesShown = MutableLiveData<Boolean>(true)
        val deleteShown = MutableLiveData(true)
        val connectShown = MutableLiveData(true)
        val dramaShown = MutableLiveData(true)
        val layerShown = MutableLiveData(true)
        val storyLineShown = MutableLiveData(true)
        val titleShown = MutableLiveData(true)
        val contentShown = MutableLiveData(true)

        fun observeTitleShown(context: Context, view: View) {
            titleShown.observe(context as LifecycleOwner) {
                view.visibility = if(it) View.VISIBLE else View.INVISIBLE
            }
        }

        fun observeContentShown(context: Context, view: View) {
            contentShown.observe(context as LifecycleOwner) {
                view.visibility = if(it) View.VISIBLE else View.GONE
            }
        }

        fun observeConnectShown(context: Context, view: View) {
            connectShown.observe(context as LifecycleOwner) {
                view.visibility = if(it) View.VISIBLE else View.GONE
            }
        }
        fun observeDramaShown(context: Context, view: View) {
           dramaShown.observe(context as LifecycleOwner) {
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

        fun observeDeleteShown(context: Context, view: View) {
            deleteShown.observe(context as LifecycleOwner) {
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
        }
    }

    private fun onOptionSelected(option: FilterOption) {
        when(option.type) {
            FilterOption.Type.WordsList -> {
                wordsShown.value = !wordsShown.value!!
            }
            FilterOption.Type.CharacterList -> characterShown.value = !characterShown.value!!
            FilterOption.Type.Date -> {
                dateShown.value = !dateShown.value!!
            }
            FilterOption.Type.Divider -> linesShown.value = !linesShown.value!!
            FilterOption.Type.BarColorDark -> barColorDark.value = !barColorDark.value!!
            FilterOption.Type.Delete -> deleteShown.value = !deleteShown.value!!
            FilterOption.Type.Layer -> layerShown.value = !layerShown.value!!
            FilterOption.Type.Connect -> connectShown.value = !connectShown.value!!
            FilterOption.Type.StoryLine -> storyLineShown.value = !storyLineShown.value!!
            FilterOption.Type.DramaIcon -> dramaShown.value = !dramaShown.value!!
            FilterOption.Type.Title -> titleShown.value = !titleShown.value!!
            FilterOption.Type.Content -> contentShown.value = !contentShown.value!!
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