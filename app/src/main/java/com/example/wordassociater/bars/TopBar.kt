package com.example.wordassociater.bars

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.R
import com.example.wordassociater.databinding.BarTopBarBinding
import com.example.wordassociater.display_filter.DisplayFilter

class TopBar(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet) {
    val b = BarTopBarBinding.inflate(LayoutInflater.from(context), this, true)
    val btnLeft = b.btnLeft
    val btnRight = b.btnRight
    val btn1 = b.btn1
    val btn2 = b.btn2
    val btn3 = b.btn3
    val btn4 = b.btn4
    val btn5 = b.btn5
    private var selectedView: MutableLiveData<View> = MutableLiveData<View>()
    private var showIconSelected = false

    init {
        setObserver()
    }

    fun showIconSelection() {
        showIconSelected = true
    }

    private fun handleALlButtonsGone() {
        var allGone = true
        val buttonList = mutableListOf(b.btn1, b.btn2, b.btn3, b.btn4, b.btn5)
        for(v in buttonList) {
            if(v.visibility == View.VISIBLE) {
                allGone = false
                break
            }
        }
        if(allGone) {
            b.btnStandIn.visibility = View.VISIBLE
        }
    }

    fun setLeftBtn(func: () -> Unit) {
        b.btnLeft.setOnClickListener {
            selectedView.value = b.btnLeft
            func()
        }
    }

    fun setLeftBtnIconAndVisibility(icon: Int, visible: Boolean) {
        b.btnLeft.setImageResource(icon)
        b.btnLeft.visibility = if(visible) View.VISIBLE else View.INVISIBLE
        handleALlButtonsGone()
    }

    fun setBtn1(func: () -> Unit) {
        b.btn1.setOnClickListener {
            selectedView.value = b.btn1
            func()
        }
    }

    fun setBtn1IconAndVisibility(icon: Int, visible: Boolean) {
        b.btn1.setImageResource(icon)
        b.btn1.visibility = if(visible) View.VISIBLE else View.GONE
        handleALlButtonsGone()
    }

    fun setRightBtnGone() {
        b.btnRight.visibility = View.GONE
    }

    fun setLeftBtnGone() {
        b.btnLeft.visibility = View.GONE
    }

    fun setBtn2(func: () -> Unit) {
        b.btn2.setOnClickListener {
            selectedView.value = b.btn2
            func()
        }
    }

    fun setBtn2IconAndVisibility(icon: Int, visible: Boolean) {
        b.btn2.setImageResource(icon)
        b.btn2.visibility = if(visible) View.VISIBLE else View.GONE
        handleALlButtonsGone()
    }


    fun setBtn3(func: () -> Unit) {
        b.btn3.setOnClickListener {
            selectedView.value = b.btn3
            func()
        }
    }

    fun setBtn3IconAndVisibility(icon: Int, visible: Boolean) {
        b.btn3.setImageResource(icon)
        b.btn3.visibility = if(visible) View.VISIBLE else View.GONE
        handleALlButtonsGone()
    }

    fun setBtn4(func: () -> Unit) {
        b.btn4.setOnClickListener {
            selectedView.value = b.btn4
            func()
        }
    }

    fun setBtn4IconAndVisibility(icon: Int, visible: Boolean) {
        b.btn4.setImageResource(icon)
        b.btn4.visibility = if(visible) View.VISIBLE else View.GONE
        handleALlButtonsGone()
    }

    fun setBtn5(func: () -> Unit) {
        b.btn5.setOnClickListener {
            selectedView.value = b.btn5
            func()
        }
    }

    fun setBtn5IconAndVisibility(icon: Int, visible: Boolean) {
        b.btn5.setImageResource(icon)
        b.btn5.visibility = if(visible) View.VISIBLE else View.GONE
        handleALlButtonsGone()
    }

    fun setRightButton(func: () -> Unit) {
        b.btnRight.setOnClickListener {
            selectedView.value = b.btnRight
            func()
        }
    }

    fun setRightBtnIconAndVisibility(icon: Int, visible: Boolean) {
        b.btnRight.setImageResource(icon)
        b.btnRight.visibility = if(visible) View.VISIBLE else View.INVISIBLE
    }

    private fun setObserver() {
        selectedView.observe(context as LifecycleOwner) {
            val viewList = listOf(b.btnLeft,btn1, btn2, btn3, btn4, b.btnRight)
            if(showIconSelected) {
                for(v in viewList) {
                    v.setBackgroundColor(if(v == selectedView) b.root.resources.getColor(R.color.lightYellow) else b.root.resources.getColor(R.color.white))
                }
            }
        }

        DisplayFilter.barColorDark.observe(context as LifecycleOwner) {
            b.root.setBackgroundColor(if(it) b.root.context.resources.getColor(R.color.snippetsLite) else b.root.context.resources.getColor(R.color.white))
        }
    }
}