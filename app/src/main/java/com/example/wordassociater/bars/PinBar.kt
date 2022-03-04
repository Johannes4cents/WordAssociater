package com.example.wordassociater.bars

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.wordassociater.R
import com.example.wordassociater.viewpager.ViewPagerMainFragment
import com.example.wordassociater.character.CharacterRecycler
import com.example.wordassociater.databinding.BarPinBinding
import com.example.wordassociater.databinding.HolderSnippetBinding
import com.example.wordassociater.display_filter.DisplayFilter
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.fire_classes.Snippet
import com.example.wordassociater.fire_classes.StoryLine
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.snippets.EditSnippetFragment
import com.example.wordassociater.utils.Page
import com.example.wordassociater.words.WordRecycler

class PinBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    enum class Mode { Connect, Path, None}
    val b = BarPinBinding.inflate(LayoutInflater.from(context), this, true)
    private val selectedMode = MutableLiveData<Mode>()
    lateinit var snippetBinding: HolderSnippetBinding
    lateinit var navController: NavController
    var navDestination: Int = 0
    val attachedSnippet = MutableLiveData<Snippet?>()

    init {
        hideButtonsOnUnpin()
        setObserver()
        setClickListener()
        checkFilterOnStart()
    }

    fun takeBindingAndNavController(snippetBinding: HolderSnippetBinding, navController: NavController, destination: Int) {
        this.navController = navController
        navDestination = destination
        this.snippetBinding = snippetBinding
    }

    fun attachSnippet(snippet: Snippet) {
        when (attachedSnippet.value) {
            null -> attachedSnippet.value = snippet
            snippet -> attachedSnippet.value = null
            else -> handleSnippetSelected(snippet)
        }
    }

    private fun handleSnippetSelected(snippet: Snippet) {
        when(selectedMode.value) {
            Mode.Connect -> {

            }
            Mode.Path -> {

            }
            Mode.None -> {

            }
            null -> {

            }
        }
    }

    private fun connectSnippets(snippet: Snippet) {

    }

    private fun checkFilterOnStart() {
        if(::snippetBinding.isInitialized) {
            snippetBinding.root.setBackgroundColor(if(DisplayFilter.barColorDark.value!!) context.resources.getColor(R.color.snippets) else context.resources.getColor(R.color.snippets))
        }
    }

    private fun setObserver() {
        DisplayFilter.barColorDark.observe(context as LifecycleOwner) {
            b.root.setBackgroundColor(if(it) b.root.context.resources.getColor(R.color.snippetsLite) else b.root.context.resources.getColor(R.color.white))
        }

        attachedSnippet.observe(context as LifecycleOwner) {
            if(it != null) {
                b.btnPin.setImageResource(R.drawable.icon_pin_selected)
                showButtonsOnPin()
                setSnippetBinding(it)
            }
            else {
                b.btnPin.setImageResource(R.drawable.icon_pin_unselected)
                hideButtonsOnUnpin()
                hideSnippetBinding()
            }
        }

        DisplayFilter.barColorDark.observe(context as LifecycleOwner) {
            if(::snippetBinding.isInitialized) {
                snippetBinding.root.setBackgroundColor(if(it) context.resources.getColor(R.color.snippets) else context.resources.getColor(R.color.snippets))
            }
        }
    }

    private fun setClickListener() {
        b.btnConnect.setOnClickListener {
            selectedMode.value = when(selectedMode.value) {
                Mode.Connect -> Mode.None
                Mode.Path -> Mode.Connect
                Mode.None -> Mode.Connect
                null -> Mode.Connect
            }
        }



        b.btnPath.setOnClickListener {
            selectedMode.value = when(selectedMode.value) {
                Mode.Connect -> Mode.Path
                Mode.Path -> Mode.None
                Mode.None -> Mode.Path
                null -> Mode.Connect
            }
        }

        b.btnRight.setOnClickListener {
            if(attachedSnippet.value != null) {
                goToEditSnippet()
            }
        }

        b.btnPin.setOnClickListener {
            attachedSnippet.value = null
        }

        b.btnLeft.setOnClickListener {
            ViewPagerMainFragment.comingFrom = Page.Start
            navController.navigate(R.id.action_snippetFragment_to_startFragment)
        }

    }

    private fun goToEditSnippet() {
        EditSnippetFragment.oldSnippet = attachedSnippet.value!!.copyMe()
        EditSnippetFragment.snippet = attachedSnippet.value!!
        navController.navigate(navDestination)
    }


    private fun setSnippetBinding(snippet: Snippet) {
        val wordLiveList = MutableLiveData<List<Word>>()
        val liveStoryList = MutableLiveData<List<StoryLine>>()
        val liveCharacters = MutableLiveData<List<Character>>()

        snippetBinding.root.visibility = View.VISIBLE

        // set date & header & content
        snippetBinding.dateHolder.setDateHolder(snippet.date, snippet)
        snippetBinding.headerText.text = snippet.header
        snippetBinding.contentPreview.text = snippet.content

        // set word Recycler
        snippetBinding.wordsRecycler.initRecycler(WordRecycler.Mode.Preview, null, null)
        snippetBinding.wordsRecycler.setLiveList(attachedSnippet.value!!.getFullWordsList())
        snippetBinding.wordsRecycler.visibility = View.VISIBLE
        wordLiveList.value = snippet.getWords()

        // set char recycler
        snippetBinding.characterRecycler.initRecycler(CharacterRecycler.Mode.Preview, null, null)
        snippetBinding.characterRecycler.setCharacterLiveData(attachedSnippet.value!!.getFullCharacterList())
        snippetBinding.characterRecycler.visibility = View.VISIBLE

        // set storyline recycler
        snippetBinding.storyLineRecycler.initRecycler(liveStoryList)
        liveStoryList.value = snippet.getStoryLines()

        //observe darkColorBar
        DisplayFilter.observeBarColorDark(snippetBinding.root.context, snippetBinding.root)

        // get color on Startup
        val darkBar = DisplayFilter.barColorDark.value!!
        snippetBinding.root.setBackgroundColor(if(darkBar) context.resources.getColor(R.color.snippets) else context.resources.getColor(R.color.white))

        snippetBinding.lineBig.setBackgroundColor(snippetBinding.root.context.resources.getColor(R.color.black))

        snippetBinding.root.setOnClickListener {
            attachedSnippet.value!!.pinned = false
            attachedSnippet.value = null
        }
    }

    private fun hideSnippetBinding() {
        snippetBinding.root.visibility = View.GONE
    }

    private fun hideButtonsOnUnpin() {
        b.btnConnect.visibility = View.INVISIBLE
        b.btnPath.visibility = View.INVISIBLE
        b.btnRight.visibility = View.INVISIBLE
    }

    private fun showButtonsOnPin() {
        b.btnConnect.visibility = View.VISIBLE
        b.btnPath.visibility = View.VISIBLE
        b.btnRight.visibility = View.VISIBLE
    }

}