package com.dwb.vokabularium.data.parser

import com.dwb.vokabularium.data.page.Page
import com.dwb.vokabularium.data.scrap.Scrap
import org.jsoup.nodes.Document


abstract class PageParser<T : Page> {

    abstract fun parse(document: Document): Scrap<T>
}