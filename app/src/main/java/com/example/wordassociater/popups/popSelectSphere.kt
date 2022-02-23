package com.example.wordassociater.popups

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderSphereBinding
import com.example.wordassociater.databinding.PopSelectSphereBinding
import com.example.wordassociater.fire_classes.Sphere
import com.example.wordassociater.utils.Helper

fun popSelectSphere(view: View, sphereList: MutableLiveData<List<Sphere>?>, handleSphereSelected: (sphere: Sphere) -> Unit) {
    val b = PopSelectSphereBinding.inflate(LayoutInflater.from(view.context), null, false)
    val pop = Helper.getPopUp(b.root, view, 600, 400)

    fun setOnClickListener() {
        b.btnBack.setOnClickListener {
            pop.dismiss()
        }

        b.btnSave.setOnClickListener {
            pop.dismiss()
        }
    }

    fun setSphereContent() {
        sphereList.observe(view.context as LifecycleOwner) {
            b.sphereLineal.removeAllViews()
            if (it != null) {
                for(sphere in it) {
                    val sphereHolder = HolderSphereBinding.inflate(LayoutInflater.from(b.root.context), null, false)
                    sphereHolder.checkbox.setImageResource(if(sphere.selected) R.drawable.checked_box else R.drawable.unchecked_box)
                    sphereHolder.sphereName.text = sphere.name
                    sphereHolder.iconSphere.setImageResource(sphere.getColor())
                    sphereHolder.root.setOnClickListener {
                        handleSphereSelected(sphere)
                    }
                    b.sphereLineal.addView(sphereHolder.root)
                }
            }
        }
    }

    setSphereContent()
    setOnClickListener()
}