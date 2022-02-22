package com.example.wordassociater.lists

import com.example.wordassociater.fire_classes.Drama

object DramaLists {
    val conflictList = mutableListOf<Drama>()
    val twistList = mutableListOf<Drama>()
    val planList = mutableListOf<Drama>()
    val motivationList = mutableListOf<Drama>()
    val goalList = mutableListOf<Drama>()
    val problemList = mutableListOf<Drama>()
    val solutionList = mutableListOf<Drama>()
    val hurdleList = mutableListOf<Drama>()

    fun getList(type: Drama.Type): MutableList<Drama> {
        return when(type) {
            Drama.Type.Conflict -> conflictList
            Drama.Type.Twist -> twistList
            Drama.Type.Plan -> planList
            Drama.Type.Motivation -> motivationList
            Drama.Type.Goal -> goalList
            Drama.Type.Problem -> problemList
            Drama.Type.Solution -> solutionList
            Drama.Type.Hurdle -> hurdleList
            Drama.Type.None -> conflictList
        }
    }
}