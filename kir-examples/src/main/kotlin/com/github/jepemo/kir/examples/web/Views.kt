package com.github.jepemo.kir.examples.web

import com.github.jepemo.kir.dom.Dom
import com.github.jepemo.kir.web.http.App
import com.github.jepemo.kir.web.http.HttpResponse
import com.github.jepemo.kir.web.http.HttpResponse.*
import com.github.jepemo.kir.web.http.Route
import com.github.jepemo.kir.web.http.View
import kotlinx.html.*

class HelloView : View() {
    override fun get() : HttpResponse {
        return Ok("Hello world!!")
    }
}

@Route("/headers")
fun headers () = Dom.html {
    head {

    }

    body {
        h1 { +"Header 1" }
        h2 { +"Header 2" }
        h3 { +"Header 3" }
        h4 { +"Header 4" }
        h5 { +"Header 5" }
    }
}


fun main(args : Array<String>) {
    App.setupConsoleLog()
    App.addRoute("/view", HelloView())
    App.start()
}
