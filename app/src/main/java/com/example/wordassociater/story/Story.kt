package com.example.wordassociater.story

import androidx.lifecycle.MutableLiveData

data class Story(val content: String) {
    companion object {
        var storyModeActive = false
    }
}

