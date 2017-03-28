package com.github.jepemo.kir.web.http

import kotlin.reflect.KFunction

/** */
enum class HttpMethod {
    OPTIONS,
    GET,
    HEAD,
    POST,
    PUT,
    DELETE,
    TRACE,
    CONNECT
}

/** */
annotation class Route(
        val path: String,
        val responseType: String = "text/plain",
        val method: Array<HttpMethod> = arrayOf(HttpMethod.GET, HttpMethod.POST)
)

/** */
annotation class Group(
        val path: String
)

/** */
annotation class Default(
        val path: String = "/",
        val responseType: String = "text/plain",
        val method: Array<HttpMethod> = arrayOf(HttpMethod.GET)
)

/** */
interface KirHttpServer {
    /** */
    var host: String
    /** */
    var port: Int
    /** */
    fun addRoute(path: String, view: View)
    /** */
    fun addRoute(path: String, method: KFunction<*>)
    /** */
    fun start()
}
