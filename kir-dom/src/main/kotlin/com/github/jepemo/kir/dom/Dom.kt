package com.github.jepemo.kir.dom

import kotlinx.html.*
import kotlinx.html.consumers.delayed
import kotlinx.html.consumers.filter
import kotlinx.html.consumers.onFinalizeMap
import kotlinx.html.dom.append
import kotlinx.html.dom.document
import kotlinx.html.dom.serialize
import kotlinx.html.stream.HTMLStreamBuilder
import kotlinx.html.stream.createHTML
import org.w3c.dom.Element


object Dom {
    fun  render() : TagConsumer<String> {
        val AVERAGE_PAGE_SIZE = 32768
        val prettyPrint = true
//        HTMLStreamBuilder(consumer, true).delayed()
//        return (document {
//            append.filter { if (it.tagName == "div") SKIP else PASS  }.html {
//                body {
//                    div {
//                        a { +"link1" }
//                    }
//                    a { +"link2" }
//                }
//            }
//        }.serialize())

//        return consumer.finalize()

//        return createHTML(prettyPrint)

//        val html = HTML()
//        html.block()
//        return html.toString()

//        return block.onFinalizeMap { sb, _ -> sb.toString() }.finalize()
//        return HTMLStreamBuilder(StringBuilder(AVERAGE_PAGE_SIZE), prettyPrint).onFinalizeMap { sb, _ -> sb.toString() }.delayed().finalize()

//        return consumer.onFinalizeMap { sb, _ -> sb.toString() }.
        return HTMLStreamBuilder(StringBuilder(AVERAGE_PAGE_SIZE), prettyPrint).onFinalizeMap { sb, _ -> sb.toString() }.delayed()
    }
}