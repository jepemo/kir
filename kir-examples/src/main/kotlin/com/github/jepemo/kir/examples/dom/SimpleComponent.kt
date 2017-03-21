package com.github.jepemo.kir.examples.dom

import com.github.jepemo.kir.dom.Dom
import kotlinx.html.h1
import org.junit.Assert
import org.junit.Test


class SimpleComponent {
    @Test
    fun simpleHello () {
        val hello = Dom.render {
            h1 {
                +"Hello World"
            }
        }

        Assert.assertEquals(hello.trim(), "<div><h1>Hello World</h1></div>")
    }
}
