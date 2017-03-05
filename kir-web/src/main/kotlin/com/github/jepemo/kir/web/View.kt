package com.github.jepemo.kir.web

import io.vertx.ext.web.RoutingContext

abstract class View() {
    var cxt: RoutingContext? = null

    open fun get() : HttpResponse {
        return HttpResponse.Ok("")
    }
    open fun post() : HttpResponse {
        return HttpResponse.Ok("")
    }
}
