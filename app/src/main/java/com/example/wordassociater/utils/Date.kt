package com.example.wordassociater.utils

data class Date(
        var day: Int = 0,
        var month: String = "",
        var year: Int = 0
) {
    var unknown = false
    fun getMonthNumber(): Int {
        return when(month) {
            "Jan" -> 1
            "Feb" -> 2
            "Mar" -> 3
            "Apr" -> 4
            "May" -> 5
            "Jun" -> 6
            "Jul" -> 7
            "Aug" -> 8
            "Sep" -> 9
            "Okt" -> 10
            "Nov" -> 11
            "Dec" -> 12
            else -> 0
        }
    }
}