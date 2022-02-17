package com.example.wordassociater.utils

import com.example.wordassociater.fire_classes.Word

object WordsToFireStoreUploader {
    private val adjectivesList = mutableListOf<String>(
        "überraschend","Instabil","verängstigt","Reich","unsicher/vielleicht","Langsam Faul","Wütend","Warm",
        "misslungen","Schnell Kurz Klein","permanent","Falsch","Unglücklich","hässlich","Stressig","Aufregend","Fordernd",
        "Perfekt","Impulsiv","Kompliziert","Mehrdeutig","Frisch","vorsichtig","fake","trügerisch","Erfolgreich","Karg","Süß",
        "zerbrechlich","Haftend klebrig anhänglich","schmutzig unordentlich","Kostenlos Umsonst Frei","lustig Albern",
        "Freundlich Ansprechend","verwirrend","Inspirierend","einschüchternd","Farblos","Rein Pur Klar Durchsichtig","verrückt",
        "nervig","Teuer","dumm","Naiv","Sinnlos","Sinnvoll","Schön", "Beliebt", "Spaßig")

    private val personsList = mutableListOf(
        "Zeuge","President","Kinder","Behinderte","Lehrer","Roommates","Vater", "Mutter", "Geschwister", "Eltern",
        "Obdachlose","Arbeitskollegen","Schwarzer Preacher im Rollstuhl","Schwarze Politikerin","Social Media Person Influencerin",
        "Überlebens künstler","Programmierer","Egozentrischer Wissenschaftler","High Priced Escort","Mafia Sohn","High school girls",
        "Fahrlehrer","Journalist","Assistant","A.I","Fremder","Agent"
    )

    private val placesList = mutableListOf(
        "Straße", "Einkaufszentrum", "Kreuzfahrt", "Höhlensystem","Altenheim","Nachrichtensender","sweatshop", "Taxi")

    private val objectList = mutableListOf(
        "Schach","Blut Diamanten","Teure Uhr","Virus","Glücksspiel","Sucht","Online gambling","horror stories",
        "Stammbaum","Magic: the gathering","Werbung","Podcasts","Bandenkrieg","Straßenkampf","Bewährung","Raumfahrt",
        "Gefängnis","Alien Sichtung","Klamotten","Fitness / Yoga","Freiheit","Bühne","Erbe","Sound effekte","Karneval","Spiel","Stadion",
        "Social Media","Gewinnspiel","Virtual Reality","Pop-ups",
        "Tinder","Meme","Überfluss","Allergien","Augmented Reality","Kunst","Hypothese","Unterricht","Urlaub",
        "Ghetto","Tatoos","(Verkaufs) Automat","Hurricane","Mutationen","Internet","Verhütung","Zweck","Uhr / Uhrzeit/ Standuhr / Armbanduhr",
        "Militär","Drogen","Armut","Glory Hole / Normales Loch","Bewerbung","Gebrochenes Herz","Film","Archäologie","Computer","König / Krönung",
        "Partei","Geschichte","Vorschau","Tagebuch","Support", "Streich",
        "Tutorials","Kredit Karte","NFTs","Missverständnis","Verschwörungstheorie","Video","A.I","Museum","Spam","Rabatt","Ozean","Meta Ebene"
    )

    private val actionsList = mutableListOf(
        "Sport machen","Einen Deal machen","Aus Wunde Bluten","Camping","Gänsehaut bekommen","Belästigen / Stören","Shop Lifting",
        "Fliegen","Die falschen schlüsse ziehen","Changing identity","erwischt werden","Obdachlos sein","Identity Theft","verspätung haben",
        "entladen","Schnorcheln","lächerlich machen","Beschweren","Zusammenschlagen","Tätowieren","Zerstören","etwas akzeptieren",
        "Seiten wechseln","Going to the Superbowl","Pretending to be rich","Protestieren","Erschrecken","Anleitung nicht verstehen",
        "Digging a grave","Ausbrechen","Abkürzung nehmen","Drug trafficking","Aufschreiben","Karaoke Singen","Being stuck in traffic",
        "gelangweilt sein","Aufgeben","Gebäude bauen","etwas missverstehen",
        "etwas falsch aussprechen","Flirten","Umziehen","Observieren","Verrückt werden","etwas erlauben","Zerstören", "Krank werden",
        "Impfen","Anfall bekommen","jemanden entäuschen","Tanken","etwas verschenken","Zug entgleist","Selbstmord","Fehler eingestehen",
        "Beschuldigen","Fehler machen","Bewerten","Falsche versprechungen machen","Recherchieren",
        "Erzwingen","Reparieren","verlieben","Fantasieren","Sich Sorgen","Verkleiden","etwas ausprobieren","Zögern","Drohen",
        "Streamen","Betteln","Suchen","Angeben","Erfinden","übertragen","vermehren","Einbrechen/Rauben","Anbeten","Bild machen",
        "nachahmen","Raten","Spenden/Unterstützen","Designen", "gefeuert werden", "Entführen",
        "Feilschen","Schlecht machen","Anziehen/Umziehen/Ausziehen","Lügen / Betrügen","Chatten / Unterhalten","Hassen","Zuschauen","Verteidigen")


//    fun uploadToDB() {
//        uploadAdjectives()
//        uploadActions()
//        uploadObjects()
//        uploadPersons()
//        uploadPlaces()
//    }
//
//    private fun uploadAdjectives() {
//        adjectivesList.forEach {
//            val newWord = Word(it, Word.Type.Adjective)
//            Fire.adjectivesList.add(newWord)
//        }
//    }
//
//    private fun uploadActions() {
//        actionsList.forEach {
//            val newWord = Word(it, Word.Type.Action)
//            Fire.fireActionList.add(newWord)
//        }
//    }
//
//    private fun uploadObjects() {
//        objectList.forEach {
//            val newWord = Word(it, Word.Type.Object)
//            Fire.fireObjectsList.add(newWord)
//        }
//    }
//    private fun uploadPersons() {
//        personsList.forEach {
//            val newWord = Word(it, Word.Type.Person)
//            Fire.personsList.add(newWord)
//        }
//    }
//    private fun uploadPlaces() {
//        placesList.forEach {
//            val newWord = Word(it, Word.Type.Place)
//            Fire.firePlacesList.add(newWord)
//        }
//    }

}

