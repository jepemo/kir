package com.github.jepemo.kir.web

import io.vertx.ext.web.RoutingContext
import mu.KLoggable
import mu.KLogging
import mu.KotlinLogging

annotation class Route(val path: String)

class App {
    companion object {
        private val logger = KotlinLogging.logger {}
        private val server = KirHttpServer()

        fun addRoute(path: String, handler: (RoutingContext) -> Unit) {
            server.addRoute(path, handler)
        }

        fun start() {
            val pathRoutes = Reflections.getPathRoutes()
            for((path, method) in pathRoutes) {
                server.addRoute(path, method)
            }

            server.start()
        }
    }
}


