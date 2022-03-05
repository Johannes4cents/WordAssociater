package com.example.wordassociater.image

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.utils.Image
import com.example.wordassociater.utils.LiveClass

class ImageRecycler(context: Context, attributeSet: AttributeSet): RecyclerView(context, attributeSet) {

    fun initRecycler(type: Image.Type, imageList: List<Image>, onImageSelected: (image: LiveClass) -> Unit) {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val imageAdapter = ImageAdapter(onImageSelected)
        adapter = imageAdapter
        imageAdapter.submitList(
                when(type) {
                    Image.Type.Event -> imageList.filter { i -> i.type == Image.Type.Event }
                    Image.Type.Location -> imageList.filter { i -> i.type == Image.Type.Location }
                    Image.Type.Character -> imageList.filter { i -> i.type == Image.Type.Character }
                    Image.Type.Item -> imageList.filter { i -> i.type == Image.Type.Item }
                    Image.Type.StoryLine -> imageList.filter { i -> i.type == Image.Type.StoryLine }
                }
        )



    }
}