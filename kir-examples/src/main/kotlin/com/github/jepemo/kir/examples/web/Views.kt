package com.github.jepemo.kir.examples.web

import com.github.jepemo.kir.web.App
import com.github.jepemo.kir.web.HttpResponse
import com.github.jepemo.kir.web.HttpResponse.*
import com.github.jepemo.kir.web.View

class HelloView : View() {
    override fun get() : HttpResponse {
        return Ok("Hello world!!")
    }
}


fun main(args : Array<String>) {
    App.setupConsoleLog()
    App.addRoute("/view", HelloView())
    App.start()
}
