package com.example.wordassociater.events

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.R
import com.example.wordassociater.databinding.PopupEventBinding
import com.example.wordassociater.fire_classes.Event
import com.example.wordassociater.firestore.FireEvents
import com.example.wordassociater.firestore.FireStats
import com.example.wordassociater.firestore.FireStoryLines
import com.example.wordassociater.utils.Date
import com.example.wordassociater.utils.Helper

fun popCreateEvent(from: View, storyLineList : List<Long>) {
    val b = PopupEventBinding.inflate(LayoutInflater.from(from.context), null, false)
    val pop = Helper.getPopUp(b.root, from, null,null, null,null, true)
    val imageList = mutableListOf(b.explosion, b.airplane, b.crown, b.food, b.handshake, b.party,b.shield, b.spy, b.pistol)
    val selectedImage = MutableLiveData<View>()
    var nameEntered = false
    var iconPicked = false
    for(view in imageList) {
        selectedImage.observe(view.context as LifecycleOwner) {
            view.setBackgroundColor(if(it == view) view.context.resources.getColor(R.color.lightYellow) else view.context.resources.getColor(R.color.snippets))
        }
    }

    Log.i("filterProb", "storyLineList is $storyLineList")

    val date = Date()
    val event = Event(
            id = FireStats.getStoryPartId(),
            storyLineList = storyLineList.toMutableList(),
            date = date)

    b.btnDate.setDateHolder(date, event, newEvent = true, from)

    b.eventNameInput.setTextColorToWhite()
    b.eventNameInput.setCenterGravity()
    b.eventNameInput.setSingleLine()
    b.eventNameInput.setToRobotoBold()
    b.eventNameInput.setHintInTextField("Event name?")
    b.eventNameInput.setInputHint("Event name?")
    //b.eventInput.showInputField()

    b.eventDescriptionInput.setTextColorToWhite()
    b.eventDescriptionInput.setHintInTextField("What happens?")

    b.eventDescriptionInput.setOnEnterFunc {
        if(it != "" && it != " ") {
            event.description = it
        }
    }


    b.eventNameInput.setOnEnterFunc {
        if(it != "" && it != " ") {
            nameEntered = true
            event.content = it
        }
    }

    b.airplane.setOnClickListener {
        selectedImage.value = it
        event.image = Event.Image.Airplane
        iconPicked = true
     }

    b.crown.setOnClickListener {
        selectedImage.value = it
        event.image = Event.Image.Crown
        iconPicked = true
    }

    b.explosion.setOnClickListener {
        selectedImage.value = it
        event.image = Event.Image.Explosion
        iconPicked = true
    }

    b.food.setOnClickListener {
        selectedImage.value = it
        event.image = Event.Image.Food
        iconPicked = true
    }

    b.handshake.setOnClickListener {
        selectedImage.value = it
        event.image = Event.Image.Handshake
        iconPicked = true
    }

    b.party.setOnClickListener {
        selectedImage.value = it
        event.image = Event.Image.Party
        iconPicked = true
    }

    b.shield.setOnClickListener {
        selectedImage.value = it
        event.image = Event.Image.Shield
        iconPicked = true
    }

    b.spy.setOnClickListener {
        selectedImage.value = it
        event.image = Event.Image.Spy
        iconPicked = true
    }

    b.pistol.setOnClickListener {
        selectedImage.value = it
        event.image = Event.Image.Pistol
        iconPicked = true
    }

    b.btnBack.setOnClickListener {
        pop.dismiss()
    }

    b.btnSave.setOnClickListener {
        if(!nameEntered) {
            Helper.toast("Give the event a name", b.root.context)
        }
        else if(!iconPicked) {
            Helper.toast("please select an Icon", b.root.context)
        }
        else {
            for(sl in event.getStoryLines()) {
                sl.eventList.add(event.id)
                FireStoryLines.update(sl.id, "eventList", sl.eventList)
            }
            FireEvents.add(event)
            pop.dismiss()
        }
    }

}