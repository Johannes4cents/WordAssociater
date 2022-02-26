package com.example.wordassociater.drama

import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderDramaTypeBinding
import com.example.wordassociater.utils.Drama

class DramaTypeHolder(val b: HolderDramaTypeBinding, val takeDramaTypeFunc: (dramaType: Drama) -> Unit): RecyclerView.ViewHolder(b.root) {
    lateinit var dramaType: Drama
    fun onBind(type: Drama) {
        this.dramaType = type
        setIcon()
        setName()
        setClickListener()
    }

    private fun setIcon() {
        b.iconDramaturgy.setImageResource(getImage(dramaType))
    }

    private fun setName() {
        b.typeName.text = dramaType.name
    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            takeDramaTypeFunc(dramaType)
        }
    }

    private fun getImage(type: Drama): Int {
        return when(type) {
            Drama.Conflict -> R.drawable.icon_conflict
            Drama.Twist -> R.drawable.icon_twist
            Drama.Plan -> R.drawable.icon_plan
            Drama.Motivation -> R.drawable.icon_motivation
            Drama.Goal -> R.drawable.icon_goal
            Drama.Problem -> R.drawable.icon_problem
            Drama.Solution -> R.drawable.icon_solution
            Drama.Hurdle -> R.drawable.icon_hurdle
            Drama.None -> R.drawable.icon_dramaturgy
            Drama.Comedy -> R.drawable.icon_comedy
        }
    }
}