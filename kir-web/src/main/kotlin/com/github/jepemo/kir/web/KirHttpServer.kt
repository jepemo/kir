package com.github.jepemo.kir.web

import kotlin.reflect.KFunction

interface KirHttpServer {
    fun start()
    fun addRoute(path: String, view: View)
    fun addRoute(path: String, method: KFunction<*>)
}
