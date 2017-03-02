package com.github.jepemo.kir.web

import io.vertx.ext.web.RoutingContext
import mu.KotlinLogging

annotation class Route(val path: String, val responseType: String = "text/plain")

class App {
    companion object {
        private val logger = KotlinLogging.logger {}
        private val server = KirHttpServer()

        fun addRoute(path: String, handler: (RoutingContext) -> Unit) {
            server.addRoute(path, handler)
        }

        fun setupConsoleLog() {
            System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG")
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


