package com.example.wordassociater.display_filter

import com.example.wordassociater.R

data class FilterOption(val name:String = "", val type: Type = Type.Title, val forWhat: For = For.Other, val icon: Int = 0) {
    var selected = true
    enum class Type { ItemColorDark ,BarColorDark, WordsList, CharacterList, Date, Divider, Delete, Layer, Connect, StoryLine, DramaIcon, Title, Content }
    enum class For { Bar, Icon, Other, Content }

    companion object {
        val options by lazy { createFilter() }

        private fun createFilter(): List<FilterOption> {
            val filterList = mutableListOf<FilterOption>()
            val filterTitle = FilterOption("Title", Type.Title, For.Content, R.drawable.icon_title)
            filterList.add(filterTitle)
            val filterContent = FilterOption("Content", Type.Content, For.Content, R.drawable.icon_content)
            filterList.add(filterContent)
            val filterWordsList = FilterOption("Connected Words", Type.WordsList, For.Bar, R.drawable.icon_word)
            filterList.add(filterWordsList)
            val filterCharacterList = FilterOption("Character List", Type.CharacterList, For.Bar, R.drawable.icon_character)
            filterList.add(filterCharacterList)
            val filterDivider = FilterOption("Dividing Lines", Type.Divider, For.Bar, R.drawable.icon_line)
            filterList.add(filterDivider)
            val filterDate = FilterOption("Date", Type.Date, For.Icon, R.drawable.date_month_day)
            filterList.add(filterDate)
            val filterBarColorDark = FilterOption("Bars Color : Dark", Type.BarColorDark, For.Other, R.drawable.icon_dialogue)
            filterList.add(filterBarColorDark)
            val filterConnectIcon = FilterOption("Connect Icon", Type.Connect, For.Icon, R.drawable.connect)
            filterList.add(filterConnectIcon)
            val filterLayerIcon = FilterOption("Layer Icon", Type.Layer, For.Icon, R.drawable.button_strain_layer)
            filterList.add(filterLayerIcon)
            val filterDeleteIcon = FilterOption("Delete Icon", Type.Delete, For.Icon, R.drawable.icon_delete)
            filterList.add(filterDeleteIcon)
            val filterStoryLineIcon = FilterOption("Storylines", Type.StoryLine, For.Bar, R.drawable.icon_story)
            filterList.add(filterStoryLineIcon)
            val filterDramaIcon = FilterOption("Drama Icon", Type.DramaIcon, For.Icon, R.drawable.icon_dramaturgy)
            filterList.add(filterDramaIcon)
            val filterItemColor = FilterOption("List Item Color", Type.ItemColorDark , For.Other, R.drawable.icon_list_item)
            filterList.add(filterItemColor)
            return filterList
        }

    }
}