package com.github.jepemo.kir.web

import mu.KotlinLogging
import java.util.*

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

annotation class Route(
    val path: String,
    val responseType: String = "text/plain",
    val method: Array<HttpMethod> = arrayOf(HttpMethod.GET, HttpMethod.POST)
)

annotation class Group(
    val path: String
)

annotation class Default(
    val path: String = "/",
    val responseType: String = "text/plain",
    val method: Array<HttpMethod> = arrayOf(HttpMethod.GET)
)

class App {
    companion object {
        /** */
        private val logger = KotlinLogging.logger {}
        /** */
        private var routes: MutableMap<String, View> = HashMap()

        private var httpWrapper : Class<*> = Class.forName("com.github.jepemo.kir.server-vertx-wrapper")
            set(value) {
                httpWrapper = value
            }


//        fun addRoute(path: String, handler: (RoutingContext) -> Unit) {
//            server.addRoute(path, handler)
//        }

        fun addRoute(path: String, view: View) {
            routes.put(path, view)
        }

        fun setupConsoleLog() {
            System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG")
        }

        fun start() {
            // Instantiate the server
            val server = httpWrapper.newInstance() as KirHttpServer

            val pathRoutes = Reflections.getPathRoutes()
            for((path, method) in pathRoutes) {
                server.addRoute(path, method)
            }

            for((path, view) in routes) {
                server.addRoute(path, view)
            }

            server.start()
        }
    }
}


