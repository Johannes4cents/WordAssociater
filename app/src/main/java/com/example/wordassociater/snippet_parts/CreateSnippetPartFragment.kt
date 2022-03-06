package com.example.wordassociater.snippet_parts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.R
import com.example.wordassociater.databinding.FragmentCreateSnippetPartBinding
import com.example.wordassociater.fire_classes.*
import com.example.wordassociater.firestore.*
import com.example.wordassociater.live_recycler.LiveRecycler
import com.example.wordassociater.popups.popLiveClass
import com.example.wordassociater.popups.popSearchWord
import com.example.wordassociater.popups.popSelectStoryLine
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.utils.Image
import com.example.wordassociater.utils.LiveClass
import com.example.wordassociater.utils.Page
import com.example.wordassociater.viewpager.ViewPagerMainFragment
import com.example.wordassociater.words.WordRecycler

class CreateSnippetPartFragment: Fragment() {
    lateinit var b: FragmentCreateSnippetPartBinding
    private var imageSelected = false
    private var namePicked = false

    companion object {
        lateinit var newSnippetPart : SnippetPart

        fun createSnippetAs(type: SnippetPart.Type) {
            newSnippetPart = when(type) {
                SnippetPart.Type.Location -> Location()
                SnippetPart.Type.Event -> Event()
                SnippetPart.Type.Item -> Item()
                SnippetPart.Type.Character -> Character()
            }
            newSnippetPart.id = FireStats.getSnippetPartId()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentCreateSnippetPartBinding.inflate(inflater)
        setTopBar()
        setRecycler()
        setInputFields()
        setImportanceBar()
        setDescriptionDescription()
        return b.root
    }

    private fun setInputFields() {
        b.descriptionInput.setInputHint("description")
        b.descriptionInput.setCenterGravity()
        b.descriptionInput.setBgToLite()
        b.descriptionInput.disableNuwInput()
        b.descriptionInput.setTextColorToWhite()
        b.descriptionInput.setHintInTextField("Description")

        b.nameInput.setInputHint("Name")
        b.nameInput.hideOnEnter()
        b.nameInput.setCenterGravity()
        b.nameInput.setBgToLite()
        b.nameInput.setSingleLine()
        b.nameInput.disableNuwInput()
        b.nameInput.setTextColorToWhite()
        b.nameInput.setHintInTextField("Name")

        b.descriptionInput.setContentFunc {
            newSnippetPart.description = it
        }

        b.nameInput.setOnEnterFunc {
            newSnippetPart.name = it
            namePicked = true
        }
    }

    private fun setImportanceBar() {
        b.importanceBar.setSideFunc {
            newSnippetPart.importance = SnippetPart.Importance.Side
        }

        b.importanceBar.setMentionedFunc {
            newSnippetPart.importance = SnippetPart.Importance.Mentioned
        }

        b.importanceBar.setMainFunc {
            newSnippetPart.importance = SnippetPart.Importance.Main
        }
    }



    private fun setTopBar() {
        b.topBar.setBtn1IconAndVisibility(R.drawable.icon_word, false)
        b.topBar.setBtn3IconAndVisibility(R.drawable.icon_word, false)
        b.topBar.setBtn4IconAndVisibility(R.drawable.icon_word, false)

        b.topBar.setLeftBtn {
            ViewPagerMainFragment.comingFrom = Page.SnippetParts
            findNavController().navigate(R.id.action_createSnippetPartFragment_to_ViewPagerFragment)
        }

        b.topBar.setRightButton {
            if(!namePicked) {
                Helper.toast("Please pick a Name", requireContext())
            }
            else if(!imageSelected) {
                Helper.toast("Please select an Image",requireContext())
            }
            else {
                when(newSnippetPart) {
                    is Character -> {
                        FireChars.add(newSnippetPart as Character, requireContext())
                    }
                    is Event -> {
                        FireEvents.add(newSnippetPart as Event, requireContext())
                    }
                    is Location -> {
                        FireLocations.add(newSnippetPart as Location, requireContext())
                    }
                    is Item -> {
                        FireItems.add(newSnippetPart as Item, requireContext())
                    }
                }

                newSnippetPart.createWord()
                ViewPagerMainFragment.comingFrom = Page.SnippetParts
                findNavController().navigate(R.id.action_createSnippetPartFragment_to_ViewPagerFragment)
            }
        }

        b.topBar.setBtn2 {
            newSnippetPart.getFullWordsList()
            popSearchWord(b.topBar.btn3, ::onWordSelected, newSnippetPart.liveWordsSearch, ::onHeaderClicked)
        }

        b.topBar.setBtn5 {
            newSnippetPart.getFullStoryLineList()
            popLiveClass(LiveRecycler.Type.StoryLine, b.topBar.btn5, newSnippetPart.liveStoryLines, ::onStoryLineSelected)
        }

    }


    private fun onHeaderClicked(wordText: String) {
        Log.i("searchHeader", "onHeaderClicked")
    }

    private fun onStoryLineSelected(storyLine: LiveClass) {
        newSnippetPart.takeStoryLine(storyLine as StoryLine)
    }

    private fun setDescriptionDescription() {
        b.descriptionText.text = when(newSnippetPart) {
            is Character -> "Describe The Character (optional)"
            is Item -> "Describe the Item (optional)"
            is Location -> "Describe the Location (optional)"
            is Event -> "Describe the Event (optional"
            else -> ""
        }
    }

    private fun setRecycler() {
        b.imageRecycler.initRecycler(getImageType(newSnippetPart), Image.imageList, ::onImageSelected)
        b.wordPreviewRecycler.initRecycler(LiveRecycler.Mode.Preview, LiveRecycler.Type.Word, null, newSnippetPart.liveWords)
        b.storyLineRecycler.initRecycler(LiveRecycler.Mode.Preview, LiveRecycler.Type.StoryLine, null, newSnippetPart.liveStoryLines)
        newSnippetPart.getFullStoryLineList()
    }

    private fun onImageSelected(image: LiveClass) {
        image as Image
        newSnippetPart.image = image.id
        imageSelected = true
    }

    private fun onWordSelected(word: Word) {
        newSnippetPart.takeWord(word)
    }

    private fun getImageType(snippetPart: SnippetPart): Image.Type {
        return when(snippetPart) {
            is Location -> Image.Type.Location
            is Event -> Image.Type.Event
            is Item -> Image.Type.Item
            is Character -> Image.Type.Character
            else -> Image.Type.Character
        }
    }
}