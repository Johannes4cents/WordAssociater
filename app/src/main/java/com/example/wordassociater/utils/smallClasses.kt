package com.example.wordassociater.utils

import com.example.wordassociater.R
import com.example.wordassociater.fire_classes.Word
import com.example.wordassociater.firestore.FireLists


class CommonWord(val text: String = "",val language: Language = Language.German, val type: Type = Type.Very ) {
    enum class Type { Very, Somewhat, Uncommon}

    companion object {
        fun getTypeImage(type: Type): Int {
            return when(type) {
                Type.Very -> R.drawable.common_type_very
                Type.Somewhat -> R.drawable.common_type_somewhat
                Type.Uncommon -> R.drawable.icon_word
            }
        }
    }
}


class FamCheck(val stem: String = "", val newFam: String = "") {

    companion object {
        fun addFamCheck(stem: String, newFam: String) {
            val famCheck = FamCheck(stem, newFam)
            FireLists.addNewFamCheck(famCheck)
        }
    }

}

data class Image(override var id: Long, val imageName: Name, var src: Int, val type: Type): LiveClass {
    enum class Name {
        Laptop, YoungGirl, YoungWoman, OldWoman, OldMan, YoungMan,
        YoungBoy, Fire, Friends, Heart, Hospital, Bones, Computer,
        Eye, Money, Planet, Science, Letter, Knife, House, Car, Forrest, City, Lab, Island, Street, Factory, Apartment ,
        Airplane, Crown, Explosion, Food, Handshake, Party, Pistol, Shield, Spy, Conflict, Twist, Plan, Motivation, Problem, Solution,
        Clothes, Toy, Drugs, Key, Present, Phone }
    enum class Type { Event, Location, Character, Item, StoryLine }

    override var sortingOrder: Int = 0

    override var name: String = ""

    override var isAHeader: Boolean = false

    override var selected: Boolean = false

    override var image: Long = 0L

    fun getDrawable(): Int {
        return when(imageName) {
            Name.Laptop -> R.drawable.item_laptop
            Name.YoungGirl -> R.drawable.character_young_girl
            Name.YoungWoman -> R.drawable.character_young_woman
            Name.OldWoman -> R.drawable.character_old_woman
            Name.OldMan -> R.drawable.character_old_man
            Name.YoungMan -> R.drawable.character_young_man
            Name.YoungBoy -> R.drawable.character_young_boy
            Name.Fire -> R.drawable.storyline_fire
            Name.Friends -> R.drawable.storyline_friends
            Name.Heart -> R.drawable.storyline_heart
            Name.Hospital -> R.drawable.storyline_hospital
            Name.Bones -> R.drawable.storyline_bones
            Name.Computer -> R.drawable.storyline_computer
            Name.Eye -> R.drawable.storyline_eye
            Name.Money -> R.drawable.storyline_money
            Name.Planet -> R.drawable.storyline_planet
            Name.Science -> R.drawable.storyline_science
            Name.Letter -> R.drawable.storyline_letter
            Name.Knife -> R.drawable.storyline_knife
            Name.House -> R.drawable.location_house
            Name.Car -> R.drawable.location_car
            Name.Forrest -> R.drawable.location_forrest
            Name.City -> R.drawable.location_city
            Name.Lab -> R.drawable.location_lab
            Name.Island -> R.drawable.location_island
            Name.Street -> R.drawable.location_street
            Name.Factory -> R.drawable.location_factory
            Name.Apartment -> R.drawable.location_apartment
            Name.Airplane -> R.drawable.event_airplane
            Name.Crown -> R.drawable.event_crown
            Name.Explosion -> R.drawable.event_explosion
            Name.Food -> R.drawable.event_food
            Name.Handshake -> R.drawable.event_handshake
            Name.Party -> R.drawable.event_party
            Name.Pistol -> R.drawable.event_pistol
            Name.Shield -> R.drawable.event_shield
            Name.Spy -> R.drawable.event_spy
            Name.Conflict -> R.drawable.storryline_conflict
            Name.Twist -> R.drawable.storyline_twist
            Name.Plan -> R.drawable.storyline_plan
            Name.Motivation -> R.drawable.storyline_motivation
            Name.Problem -> R.drawable.storyline_problem
            Name.Solution -> R.drawable.storyline_solution
            Name.Clothes -> R.drawable.item_clothes
            Name.Toy -> R.drawable.item_toy
            Name.Drugs -> R.drawable.item_drugs
            Name.Key -> R.drawable.item_key
            Name.Present -> R.drawable.item_present
            Name.Phone -> R.drawable.item_cellphone
        }
    }

    companion object {
        val imageList = listOf<Image>(
                Image(1, Name.Laptop, 0, Type.Item), Image(20, Name.YoungGirl, 0, Type.Character),Image(39, Name.YoungWoman, 0, Type.Character),
                Image(2, Name.OldWoman, 0, Type.Character),Image(21, Name.OldMan, 0, Type.Character),
                Image(3, Name.YoungMan, 0, Type.Character),Image(22, Name.YoungBoy, 0, Type.Character),Image(40, Name. Fire, 0, Type.StoryLine),
                Image(4, Name.Friends, 0, Type.StoryLine),Image(23, Name.Heart, 0, Type.StoryLine),
                Image(5, Name.Hospital, 0, Type.StoryLine),Image(24, Name.Bones, 0, Type.StoryLine),Image(41, Name.Computer, 0, Type.StoryLine),
                Image(6, Name.Eye, 0, Type.StoryLine),Image(25, Name.Money, 0, Type.StoryLine),Image(50, Name.Money, 0, Type.Item),
                Image(51, Name.Hospital, 0, Type.Location), Image(52, Name.Knife, 0, Type.Item),
                Image(7, Name.Planet, 0, Type.StoryLine),Image(26, Name.Science, 0, Type.StoryLine),Image(42, Name.Letter, 0, Type. StoryLine),
                Image(8, Name.Knife, 0, Type.StoryLine),Image(27, Name.Airplane, 0, Type.Location),
                Image(9, Name.House, 0, Type.Location),Image(28, Name.Crown, 0, Type.Event),Image(43, Name.Conflict, 0, Type.StoryLine),
                Image(53, Name.Crown, 0, Type.Item),Image(54, Name.Conflict, 0, Type.Event),
                Image(10, Name.Car, 0, Type.Location),Image(29, Name.Explosion, 0, Type.Event), Image(55, Name.Food, 0, Type.Item),
                Image(11, Name.Forrest, 0, Type.Location),Image(30, Name.Food, 0, Type.Event),Image(44, Name.Twist, 0, Type.StoryLine),
                Image(12, Name.City, 0, Type.Location),Image(31, Name.Handshake, 0, Type.Event),
                Image(13, Name.Lab, 0, Type.Location),Image(32, Name.Party, 0, Type.Event),Image(45, Name.Plan, 0, Type.StoryLine),
                Image(14, Name.Island, 0, Type.Location),Image(33, Name.Pistol, 0, Type.Event), Image(56, Name.Pistol, 0, Type.Item),
                Image(15, Name.Street, 0, Type.Location),Image(34, Name.Shield, 0, Type.Event),Image(46, Name.Motivation, 0, Type.StoryLine),
                Image(16, Name.Factory, 0, Type.Location),Image(35, Name.Spy, 0, Type.StoryLine), Image(34, Name.Shield, 0, Type.StoryLine),
                Image(17, Name.Apartment, 0, Type.Location),Image(36, Name.Key, 0, Type.Item),Image(47, Name.Problem, 0, Type.StoryLine),
                Image(18, Name.Solution, 0, Type.StoryLine),Image(37, Name.Drugs, 0, Type.Item),Image(48, Name.Present, 0, Type.Item),
                Image(19, Name.Clothes, 0, Type.Item),Image(38, Name.Toy, 0, Type.Item),Image(49, Name.Phone, 0, Type.Item),
                Image(35, Name.Spy, 0, Type.Character),
        )

        fun getDrawable(id: Long): Image {
            return imageList.find { i -> i.id == id }!!
        }

        fun getImageByName(name: Image.Name): Image {
            return imageList.find { i  -> i.imageName == name }!!
        }

    }
}

interface LiveClass {
    var sortingOrder: Int
    var name: String
    var id: Long
    var isAHeader: Boolean
    var selected : Boolean
    var image: Long
}

enum class Language {German, English}

enum class Drama {Conflict, Twist, Plan, Motivation, Goal, Problem, Solution, Hurdle, None, Comedy}

enum class AdapterType { List, Popup, Preview }

enum class Page {Notes, SnippetParts, Start, Words, Nuws }

data class ConnectedWord(val word: Word, var amount: Int) {

    companion object {
        fun addWord(word: Word, list: MutableList<ConnectedWord>): MutableList<ConnectedWord> {
            val newList = mutableListOf<ConnectedWord>()
            if(list.isNotEmpty()) {
                for(connectedWord in list) {
                    if(connectedWord.word.id == word.id) {
                        connectedWord.amount += 1
                        newList.add(connectedWord)
                    }
                    else {
                        newList.add(ConnectedWord(word, 1))
                    }
                }
            }
            else {
                newList.add(ConnectedWord(word, 1))
            }
            return newList
        }
    }
}

