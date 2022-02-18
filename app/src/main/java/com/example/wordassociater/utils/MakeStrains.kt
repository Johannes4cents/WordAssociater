package com.example.wordassociater.utils

import com.example.wordassociater.fire_classes.Strain
import com.example.wordassociater.firestore.FireStrains

object MakeStrains {
    var contentList = mutableListOf(
            "Ein Hochansteckender Virus tötet zu schnell um sich wirklich ausbreiten zu können",
            "Typ ist sauer über die vielen schlechten Memes im Internet. Er schreibt ein Tutorial wie man gute Memes macht",
            "Freundin ist enttäuscht weil ihr Freund zu lange braucht ein Ikea Regal zusammenzubauen",
            "Schüler erpressen Lehrer nachdem sie sein Tagebuch klauen",
            "Jugendlicher bekommt seinen Youtube Kanal gesperrt, weil er Videos hochlädt wie er mit seinen Freunden Obdachlose verarscht",
            "Lehrer an einer Problemschule gibt einfach auf. Er erscheint zwar jeden Tag in der Schule, macht aber nichts im Unterricht und sammelt einfach sein Gehalt ein während er darauf wartet gefeuert zu werden",
            "CharaktereGovernor und TikTok Konkurrentin machenButton machen für App NotizenApp: versteckte gimmicks in App bauenConnect strains einbauen",
            "Assistant bucht Hotelzimmer ausversehen in der falschen Nachbarschaft. Das Hotel befindet sich im Ghetto",
            "Mörder vergräbt im Wald eine Leiche und entdeckt zufälligerweise beim graben des Loches etwas Wertvolles, was jemand anders an dieser Stelle versteckt hat",
            "Person reagiert sehr schlecht auf das ausnutzen des Gehirns bei VR Brillen benutzung",
            "Christopher spielt einer sehr alten Dame im Altenheim ein gefaktes Video auf seinem Handy vor um sie mit irgendwas zu erpressen",
            "High school Mädchen Recherchieren , wie sie Effektiv ihr Opfer mobben und verrückt machen können",
            "Typ macht sich lustig über einen komplizierten Podcast über teure Uhren und die komplexe Technik darin",
            "Wächter hört während dem Duschen der Insassen wie gut einer von ihnen singt. Infolge schützt er ihn vor den Übergriffen der anderen Häftlinge, lässt ihm Gesangsunterricht zukommen und tut alles dafür , dass seine Haftstrafe reduziert wird. Er will ihn produzieren und damit reich werden",
            "Junge Frau sticht Löcher in die Kondome ihrer religiösen Mitbewohnerin \"Freundin\", damit diese Schwanger wird und langfristig deswegen auszieht",
            "Bei seiner Verurteilung sieht Christopher wie der Richter eine teure Uhr trägt. Genau die Uhr die sein Rivale schwarz verkäuft.Er denkt der Richter wurde bestochen, doch dem ist nicht so",
            "Die Mafia fordert immer mehr Schutzgeld von einem Ladenbesitzer. Dieser dreht durch und schlägt die beiden Mafia Boten zusammen",
            "Frau legt ein Tagebuch an und begibt sich absichtlich ständig in gefährliche Situationen um ihr Tagebuch spannend zu gestalten, damit sie es später wie das Tagebuch der Anne Frank verkaufen kann",
            "Sein Produkt/Unternehmen irgendwas mit 420 oder 69 mit drinnen nennen anstatt eine Zahl die sinn macht",
            "Sam reagiert allergisch auf das Material eines Pullovers der ihr geschenkt wurde",
            "Tätowierer wird gezwungen das wegmachen eines hässlichen Tatoos zu bezahlen",
            "Partei gibt viel Geld aus für sehr nervige Popup adds im Internet. die Leute hassen es",
            "Sportler verspricht seinem kranken Sohn das er Gold holt. Er gewinnt. Neben ihm am weinen sein unterlegener Konkurrent, er hat seinem Sohn der kurz vorher an Krebs gestorben ist, dass gleiche versprochen",
            "Hässlicher Typ liest sich in Identity Theft ein um die Identität eines Gutaussehenden zu stehlen"

    )

    fun go() {
        var strainId: Long = 55
        for(string in contentList) {
            val strain = Strain(
                    content = string,
                    id = strainId
            )
            strainId++
            FireStrains.add(strain, null)
        }
    }
}