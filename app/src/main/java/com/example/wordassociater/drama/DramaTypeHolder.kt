package com.example.wordassociater.dramaturgy

import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderDramaTypeBinding
import com.example.wordassociater.fire_classes.Drama

class DramaTypeHolder(val b: HolderDramaTypeBinding, val takeDramaTypeFunc: (dramaType: Drama.Type) -> Unit): RecyclerView.ViewHolder(b.root) {
    lateinit var dramaType: Drama.Type
    fun onBind(type: Drama.Type) {
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

    private fun getImage(type: Drama.Type): Int {
        return when(type) {
            Drama.Type.Conflict -> R.drawable.icon_conflict
            Drama.Type.Twist -> R.drawable.icon_twist
            Drama.Type.Plan -> R.drawable.icon_plan
            Drama.Type.Motivation -> R.drawable.icon_motivation
            Drama.Type.Goal -> R.drawable.icon_goal
            Drama.Type.Problem -> R.drawable.icon_problem
            Drama.Type.Solution -> R.drawable.icon_solution
            Drama.Type.Hurdle -> R.drawable.icon_hurdle
            Drama.Type.None -> R.drawable.icon_dramaturgy
        }
    }
}