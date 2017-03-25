package com.github.jepemo.kir.dom

import kotlinx.html.div
import kotlinx.html.html
import kotlinx.html.stream.createHTML

class HtmlDom(val content: String)

object Dom {
    fun render(prettyPrint: Boolean = false, block: kotlinx.html.DIV.() -> kotlin.Unit) : String {
        return createHTML(prettyPrint).div {
            block()
        }
    }

    fun html (prettyPrint: Boolean = false, block: kotlinx.html.HTML.() -> kotlin.Unit) : HtmlDom {
        return HtmlDom(createHTML(prettyPrint).html {
            block()
        })
    }

    fun template() {

    }
}