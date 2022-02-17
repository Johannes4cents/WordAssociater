package com.example.wordassociater.utils

import android.content.Context
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.firestore.FireChars

object CharacterUploader {
    fun uploadCharacter(context: Context?) {
        val characterList = listOf<Character>(
            Character("Haley Narvarez"),
            Character("Devin Reeves", "https://imgur.com/6ZzDKeN.png"),
            Character("Samantha Bernstein"),
            Character("Robert Liu"),
            Character("Michelle Gary", "https://imgur.com/ivMBEXx.png"),
            Character("Kimberly Narvarez", "https://imgur.com/ESAlA6o.png"),
            Character("Joseph Frasier", "https://imgur.com/JMrUrxo.png"),
            Character("Emanuel Crombie", "https://imgur.com/hXyatNW.png"),
            Character("Frank Gary", "https://imgur.com/JEjETRb.png"),
            Character("Evi Phillips", "https://imgur.com/B791i0K.png"),
            Character("Christopher Trevisani", "https://imgur.com/jopBlFP.png")
        )

        for(character in characterList) {
            FireChars.add(character, context)
        }
    }

    fun secondUpload(context: Context?) {
        val characterList = listOf<Character>(
            Character("Crombie A.I", "https://imgur.com/AxByw5n.png"),
            Character("DNAIR", "https://imgur.com/rqD57IU.png")
        )

        for(character in characterList) {
            FireChars.add(character, context)
        }
    }
}