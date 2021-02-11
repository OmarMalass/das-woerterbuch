package com.dwb.vokabularium.data.parser

import com.dwb.vokabularium.data.page.LingueePage
import com.dwb.vokabularium.data.scrap.*
import com.dwb.vokabularium.data.utils.formatText
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class LingueeParser : PageParser<LingueePage>() {

    override fun parse(document: Document): LingueeScrap {
        val exact = parseExact(document)
        return LingueeScrap(exact)
    }


    private fun parseExact(document: Document): Exact {
        val exact = document.select("div.isForeignTerm, div.isMainTerm > div.exact").first()
        val lemmas = getLemmas(exact)
        return Exact(lemmas)
    }

    private fun getLemmas(exact: Element): List<Lemma> {

        val meaningElements = exact.getElementsByClass("lemma featured")
        return meaningElements.map { e -> parseLemma(e) }.toList()
    }

    private fun parseLemma(lemma: Element): Lemma {
        val description = getLemmaDescription(lemma)
        val translations = getTranslations(lemma)
        return Lemma(description, translations)
    }

    private fun getLemmaDescription(lemma: Element): LemmaDescription {

        val description = lemma.select("h2.line.lemma_desc").first()
        val term = getLemmaTerm(description)
        val pos = getLemmaPOS(description)
        return LemmaDescription(term, pos)
    }

    private fun getLemmaTerm(description: Element): String {

        val nameElement = description.select("span.tag_lemma > a.dictLink")
        return nameElement.text()
    }

    private fun getLemmaPOS(description: Element): String {
        val posElement = description.select("span.tag_lemma > span.tag_wordtype")
        return posElement.text()
    }

    private fun getTranslations(lemma: Element): List<Translation> {
        val translationLines =
            lemma.select("div.lemma_content > div.meaninggroup  > div.translation_lines > div.translation")
        return translationLines.map { e -> parseTranslation(e) }.toList()
    }

    private fun parseTranslation(translation: Element): Translation {
        val description = getTranslationDescription(translation)
        val examples = getTranslationExamples(translation)
        return Translation(description, examples)
    }


    private fun getTranslationDescription(translation: Element): TranslationDescription {
        val descriptionElement = translation.select("h3.translation_desc > span.tag_trans").first()

        val meaning = getTranslationMeaning(descriptionElement)
        val pos = getTranslationPOS(descriptionElement)

        return TranslationDescription(meaning, pos)
    }

    private fun getTranslationMeaning(translation: Element): String {
        val meaningElement = translation.select("a.dictLink")

        // Remove redundant children
        meaningElement.select("span.placeholder")?.remove()

        val meaning = formatText(meaningElement.text())
        return meaning
    }

    private fun getTranslationPOS(element: Element): String {
        val posElement = element.select("span.tag_type")
        val posText = posElement?.attr("title") ?: ""
        val pos = formatText(posText)
        return pos
    }

    private fun getTranslationExamples(translation: Element): List<Example> {
        val examples = translation.select("div.example_lines > div.example")
        return examples.map { e -> parseTranslationExample(e) }.toList()
    }

    private fun parseTranslationExample(exampleElement: Element): Example {
        val sourceElement = exampleElement.select("span.tag_e > span.tag_s").first()
        val sourceText = sourceElement.text()

        val targetElement = exampleElement.select("span.tag_e > span.tag_t").first()
        val targetText = targetElement.text()

        return Example(sourceText, targetText)
    }
}