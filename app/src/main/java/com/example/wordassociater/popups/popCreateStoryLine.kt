package com.example.wordassociater.popups

import android.view.LayoutInflater
import android.view.View
import com.example.wordassociater.R
import com.example.wordassociater.databinding.PopupStorylinePickerBinding
import com.example.wordassociater.fire_classes.StoryLine
import com.example.wordassociater.firestore.FireStats
import com.example.wordassociater.firestore.FireStoryLines
import com.example.wordassociater.utils.Helper

fun popCreateStoryLine(from: View) {
    val b = PopupStorylinePickerBinding.inflate(LayoutInflater.from(from.context), null, false)
    val pop = Helper.getPopUp(b.root, from, null, null)
    val viewList = listOf(b.bones, b.computer, b.eye, b.fire, b.friend, b.hospital, b.knife, b.letter, b.money, b.heart, b.planet, b.science)
    var pickedIcon: View? = null
    fun checkIfSelected() {
        for(v in viewList) {
            v.setBackgroundColor(if(v == pickedIcon) from.resources.getColor( R.color.lightYellow) else from.resources.getColor(R.color.popGrey))
        }
    }

    b.nameInput.setCenterGravity()

    val storyLine = StoryLine(id= FireStats.getStoryLineId())
    b.nameInput.showInputField()

    b.nameInput.setContentFunc {
        storyLine.name = it
    }

    b.btnBack.setOnClickListener {
        pop.dismiss()
    }

    b.bones.setOnClickListener {
        storyLine.icon = StoryLine.Icon.Bones
        pickedIcon = it
        checkIfSelected()
    }

    b.computer.setOnClickListener {
        storyLine.icon = StoryLine.Icon.Computer
        pickedIcon = it
        checkIfSelected()
    }

    b.eye.setOnClickListener {
        storyLine.icon = StoryLine.Icon.Eye
        pickedIcon = it
        checkIfSelected()
    }

    b.fire.setOnClickListener {
        storyLine.icon = StoryLine.Icon.Fire
        pickedIcon = it
        checkIfSelected()
    }

    b.friend.setOnClickListener {
        storyLine.icon = StoryLine.Icon.Friends
        pickedIcon = it
        checkIfSelected()
    }

    b.hospital.setOnClickListener {
        storyLine.icon = StoryLine.Icon.Hospital
        pickedIcon = it
        checkIfSelected()
    }

    b.knife.setOnClickListener {
        storyLine.icon = StoryLine.Icon.Knife
        pickedIcon = it
        checkIfSelected()
    }

    b.letter.setOnClickListener {
        storyLine.icon = StoryLine.Icon.Letter
        pickedIcon = it
        checkIfSelected()
    }

    b.money.setOnClickListener {
        storyLine.icon = StoryLine.Icon.Money
        pickedIcon = it
        checkIfSelected()
    }

    b.heart.setOnClickListener {
        storyLine.icon = StoryLine.Icon.Heart
        pickedIcon = it
        checkIfSelected()
    }

    b.planet.setOnClickListener {
        storyLine.icon = StoryLine.Icon.Planet
        pickedIcon = it
        checkIfSelected()
    }

    b.science.setOnClickListener {
        storyLine.icon = StoryLine.Icon.Science
        pickedIcon = it
        checkIfSelected()
    }

    b.btnSave.setOnClickListener {
        when {
            pickedIcon == null -> {
                Helper.toast("please select an Icon", from.context)
            }
            b.nameInput.content == "" -> {
                Helper.toast("please enter a name for the Storyline", from.context)
            }
            else -> {
                FireStoryLines.add(storyLine, from.context)
                pop.dismiss()
            }
        }
    }


}