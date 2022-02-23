package com.example.wordassociater.popups

fun <T>itemClickedFunc(item: T, func : (item: T) -> Unit) {
    func(item)
}