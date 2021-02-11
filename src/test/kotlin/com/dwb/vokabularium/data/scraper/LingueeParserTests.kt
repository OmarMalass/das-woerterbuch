package com.dwb.vokabularium.data.scraper

import com.dwb.vokabularium.data.parser.LingueeParser
import com.dwb.vokabularium.data.scrap.Exact
import com.dwb.vokabularium.data.scrap.LingueeScrap
import org.jsoup.Jsoup
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LingueeParserTests {

    private val parser: LingueeParser = LingueeParser()
    private val enRes: LingueeScrap
    private val deRes: LingueeScrap

    init {
        val enDoc = Jsoup.connect("https://www.linguee.de/deutsch-englisch/search?source=auto&query=run").get()
        enRes = parser.parse(enDoc)

        val deDoc = Jsoup.connect("https://www.linguee.de/deutsch-englisch/search?source=deutsch&query=angriff").get()
        deRes = parser.parse(deDoc)
    }


    @Test
    internal fun linguee_EN_to_DE_Translations() {

        val translations = setOf(
            "laufen",
            "führen",
            "betreiben",
            "fahren",
            "verlaufen",
            "rennen",
            "durchführen",
            "leiten",
            "fließen",
            "strömen",
            "Run",
            "Lauf",
            "gelaufen"
        )
        val exact: Exact = enRes.exact
        for (lemma in exact.lemmas) {
            for (translation in lemma.translations) {
                assert(translation.description.term in translations)
            }

        }
    }

    @Test
    internal fun linguee_DE_to_EN_Translations() {
        println(deRes)

        val translations = setOf(
            Pair("attack", "Substantiv"),
            Pair("assault", "Substantiv"),
            Pair("tackle", "Substantiv"),
            Pair("offensive", "Substantiv"),
            Pair("offense", "Substantiv"),
            Pair("raid", "Substantiv"),
            Pair("onslaught", "Substantiv"),
            Pair("attack", "Verb"),
            Pair("engage", "Verb"),
            Pair("assail", "Verb"),
            Pair("raid", "Verb"),
        )
        val exact: Exact = deRes.exact
        for (lemma in exact.lemmas) {
            for (translation in lemma.translations) {
                val result = Pair(translation.description.term, translation.description.pos)
                assert(result in translations)
            }
        }
    }
}

