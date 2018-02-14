package com.github.jepemo.kir.web.http

abstract class View {
    var cxt: Context? = null

    open fun get() : HttpResponse {
        return HttpResponse.Ok("")
    }
    open fun post() : HttpResponse {
        return HttpResponse.Ok("")
    }
}
