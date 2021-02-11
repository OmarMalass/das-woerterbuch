package com.dwb.vokabularium.data.scrap

import com.dwb.vokabularium.data.page.LingueePage

data class LingueeScrap(val exact: Exact) : Scrap<LingueePage>()
data class Exact(val lemmas: List<Lemma>)
data class Lemma(val description: LemmaDescription, val translations: List<Translation>)
data class LemmaDescription(val term: String, val pos: String)
data class Translation(val description: TranslationDescription, val examples: List<Example>)
data class TranslationDescription(val term: String, val pos: String)
data class Example(val source: String, val target: String)
