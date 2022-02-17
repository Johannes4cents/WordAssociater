package com.example.wordassociater.buttons

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.Main
import com.example.wordassociater.databinding.ButtonLayerStrainBinding
import com.example.wordassociater.utils.Helper

class StrainLayerButton(context: Context, attributeSet: AttributeSet): ConstraintLayout(context, attributeSet) {
    val b = ButtonLayerStrainBinding.inflate(LayoutInflater.from(context), this, true)
    val currentLayer = MutableLiveData(1)

    init {
        setObserver()
    }

    private fun setObserver() {
        currentLayer.observe(context as LifecycleOwner) {
            b.layerNumber.text = it.toString()
            requestLayout()
        }
    }

    fun setClickListener() {
        b.root.setOnClickListener {
            if(Main.maxLayers > currentLayer.value!!) {
                currentLayer.value = currentLayer.value!! + 1
            }
            else {
                currentLayer.value = 1
            }
        }
    }


}