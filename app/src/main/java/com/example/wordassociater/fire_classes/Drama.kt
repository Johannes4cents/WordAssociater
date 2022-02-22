package com.example.wordassociater.fire_classes

import com.example.wordassociater.R
import com.example.wordassociater.utils.StoryPart
import com.google.firebase.firestore.Exclude

class Drama(
        override var id: Long = 0,
        override var content: String = "",
        override var wordList: MutableList<String> = mutableListOf(),
        override var characterList: MutableList<Long> = mutableListOf(),
        var type: Type = Type.Conflict
): StoryPart(wordList, content, id, characterList) {
    enum class Type {Conflict, Twist, Plan, Motivation, Goal, Problem, Solution, Hurdle, None}

    @Exclude
    fun getImage(): Int {
        return when(type) {
            Type.Conflict -> R.drawable.icon_conflict
            Type.Twist -> R.drawable.icon_twist
            Type.Plan -> R.drawable.icon_plan
            Type.Motivation -> R.drawable.icon_motivation
            Type.Goal -> R.drawable.icon_goal
            Type.Problem -> R.drawable.icon_problem
            Type.Solution -> R.drawable.icon_solution
            Type.Hurdle -> R.drawable.icon_hurdle
            Type.None -> R.drawable.icon_dramaturgy
        }
    }

}