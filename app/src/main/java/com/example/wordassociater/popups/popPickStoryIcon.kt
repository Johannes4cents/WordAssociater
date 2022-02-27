package com.example.wordassociater.popups

import android.view.LayoutInflater
import android.view.View
import com.example.wordassociater.databinding.PopupStorylinePickerBinding
import com.example.wordassociater.fire_classes.StoryLine
import com.example.wordassociater.utils.Helper

fun popPickStoryIcon(from: View, onIconPicked: (storyLineIcon: StoryLine.Icon) -> Unit) {
    val b = PopupStorylinePickerBinding.inflate(LayoutInflater.from(from.context), null, false)
    val pop = Helper.getPopUp(b.root, from, 800, 800)

    b.nameInput.visibility = View.GONE
    b.bottonBtns.visibility = View.GONE

    b.bones.setOnClickListener {
        onIconPicked(StoryLine.Icon.Bones)
    }

    b.computer.setOnClickListener {
        onIconPicked(StoryLine.Icon.Computer)
    }

    b.eye.setOnClickListener {
        onIconPicked(StoryLine.Icon.Eye)
    }

    b.fire.setOnClickListener {
        onIconPicked(StoryLine.Icon.Fire)
    }

    b.friend.setOnClickListener {
        onIconPicked(StoryLine.Icon.Friends)
    }

    b.hospital.setOnClickListener {
        onIconPicked(StoryLine.Icon.Hospital)
    }

    b.knife.setOnClickListener {
        onIconPicked(StoryLine.Icon.Knife)
    }

    b.letter.setOnClickListener {
        onIconPicked(StoryLine.Icon.Letter)
    }

    b.money.setOnClickListener {
        onIconPicked(StoryLine.Icon.Money)
    }

    b.heart.setOnClickListener {
        onIconPicked(StoryLine.Icon.Heart)
    }

    b.planet.setOnClickListener {
        onIconPicked(StoryLine.Icon.Planet)
    }

    b.science.setOnClickListener {
        onIconPicked(StoryLine.Icon.Science)
    }
}