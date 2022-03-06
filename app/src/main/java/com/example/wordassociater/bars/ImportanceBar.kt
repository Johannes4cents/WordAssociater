package com.example.wordassociater.bars

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.wordassociater.R
import com.example.wordassociater.databinding.BarImportanceBinding
import com.example.wordassociater.fire_classes.SnippetPart
import com.example.wordassociater.snippet_parts.CreateSnippetPartFragment

class ImportanceBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = BarImportanceBinding.inflate(LayoutInflater.from(context), this, true)
    private val mentionedImages = listOf(b.mentioned, b.main, b.side)
    private val selectedImportance = MutableLiveData<ImageView>(b.main)

    init {
        setObserver()
    }

    fun setMentionedFunc(func: () -> Unit) {
        b.mentioned.setOnClickListener {
            selectedImportance.value = b.mentioned
            func()
        }
    }

    fun setSideFunc(func: () -> Unit) {
        b.side.setOnClickListener {
            selectedImportance.value = b.side
            func()
        }
    }

    fun setMainFunc(func: () -> Unit) {
        b.main.setOnClickListener {
            selectedImportance.value = b.main
            func()
        }
    }

    fun hideNewButton() {
        b.btnNewPart.visibility = View.GONE
    }

    fun setNewSnippetPartButton(type: SnippetPart.Type, navController: NavController) {
        when(type) {
            SnippetPart.Type.Character -> {
                b.newPartImage.setImageResource(R.drawable.icon_character_unselected)
            }
            SnippetPart.Type.Location -> {
                b.newPartImage.setImageResource(R.drawable.icon_location_unselected)
            }

            SnippetPart.Type.Event -> {
                b.newPartImage.setImageResource(R.drawable.event_icon_unselected)
            }

            SnippetPart.Type.Item -> {
                b.newPartImage.setImageResource(R.drawable.icon_item_unselected)
            }
        }

        b.btnNewPart.setOnClickListener {
            CreateSnippetPartFragment.createSnippetAs(type)
            navController.navigate(R.id.action_ViewPagerFragment_to_createSnippetPartFragment)
        }


    }

    private fun setObserver() {
        selectedImportance.observe(context as LifecycleOwner) {
            b.mentioned.setImageResource(if(it == b.mentioned) R.drawable.importance_mentioned_selected else R.drawable.importance_mentioned)
            b.side.setImageResource(if(it == b.side) R.drawable.importance_side_selected else R.drawable.importance_side)
            b.main.setImageResource(if(it == b.main) R.drawable.importance_main_selected else R.drawable.importance_main)
        }
    }
}