package com.github.jepemo.kir.dom

import kotlinx.html.Tag

abstract class Component : Tag {
    abstract fun render (): Unit
}
