package com.dwb.vokabularium.data.scraper

import com.dwb.vokabularium.data.options.SearchOptions
import com.dwb.vokabularium.data.page.Page
import com.dwb.vokabularium.data.parser.PageParser
import com.dwb.vokabularium.data.scrap.Scrap
import org.jsoup.nodes.Document


abstract class Scraper<T : Page> {
    abstract fun getParser(): PageParser<T>

    abstract fun buildSearchURL(options: SearchOptions<T>): Scrap<T>
    abstract fun search(options: SearchOptions<T>): Scrap<T>
    abstract fun searchURL(options: SearchOptions<T>): String

    fun scrap(document: Document): Scrap<T> {
        return getParser().parse(document)
    }
}