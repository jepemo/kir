package com.github.jepemo.kir.sampleapp.view

import com.github.jepemo.kir.web.HttpResponse
import com.github.jepemo.kir.web.View


class Home : View() {
    override fun get() : HttpResponse {
        return HttpResponse.Ok("Home Page")
    }
}
