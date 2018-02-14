package com.github.jepemo.kir.sampleapp.view

import com.github.jepemo.kir.dom.Dom
import com.github.jepemo.kir.web.http.HttpResponse
import com.github.jepemo.kir.web.http.View
import kotlinx.html.body
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.title


class Home : View() {
    override fun get() : HttpResponse {
        return HttpResponse.Ok(Dom.html {
            head {
                title { +" Sample web app" }
            }
            body {
                h1 { +"Home" }
            }
        }.toString())
    }
}
