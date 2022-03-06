package com.example.wordassociater.snippet_parts

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.R
import com.example.wordassociater.databinding.BarDetailedNavigatorBinding

class DetailedNavigatorBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    private val detailsSelected = MutableLiveData<Boolean>(false)
    val b = BarDetailedNavigatorBinding.inflate(LayoutInflater.from(context), this, true)


    fun initDetails(onDetailsClicked: () -> Unit, onTimeLineClicked: () -> Unit) {

        b.btnDescription.setOnClickListener {
            detailsSelected.value = true
            onDetailsClicked()
        }

        b.btnTimeLine.setOnClickListener {
            detailsSelected.value = false
            onTimeLineClicked()
        }
        onTimeLineClicked()
        setObserver()
    }

    fun setBtnLeft(clickFunc: () -> Unit, image: Int = R.drawable.back_icon, visibility: Boolean = true) {
        b.btnLeft.setImageResource(image)
        b.btnLeft.setOnClickListener { clickFunc() }
        b.btnLeft.visibility = if(visibility) View.VISIBLE else View.INVISIBLE
    }

    fun removeLeftBtn() {
        b.btnLeft.visibility = View.GONE
    }

    fun setBtnRight(clickFunc: () -> Unit, image: Int = R.drawable.btn_save, visibility: Boolean = true) {
        b.btnRight.setImageResource(image)
        b.btnRight.setOnClickListener { clickFunc() }
        b.btnRight.visibility = if(visibility) View.VISIBLE else View.INVISIBLE
    }

    private fun setObserver() {
        detailsSelected.observe(context as LifecycleOwner) {
            b.btnDescription.setImageResource(if(it) R.drawable.icon_description else R.drawable.icon_description_unselected)
            b.btnTimeLine.setImageResource(if(it) R.drawable.icon_timeline_unselected else R.drawable.icon_timeline)
        }
    }

}