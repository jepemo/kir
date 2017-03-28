package com.github.jepemo.kir.web.http

import com.github.jepemo.kir.server_vertx_wrapper.KirVertxHttpServer
import com.github.jepemo.kir.web.utils.Reflections
import mu.KotlinLogging
import org.slf4j.impl.SimpleLogger
import java.util.*
import kotlin.reflect.KClass

class App {
    companion object {
        /** */
        private val logger = KotlinLogging.logger {}
        /** */
        private var routes: MutableMap<String, View> = HashMap()

        private var httpWrapper : KClass<*> = KirVertxHttpServer::class
            set(value) {
                httpWrapper = value
            }

        fun addRoute(path: String, view: View) {
            routes.put(path, view)
        }

        fun setupConsoleLog() {
            System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG")
        }

        fun start() {
            val server = httpWrapper.java.newInstance() as KirHttpServer
            server.port = 8080
            server.host = "localhost"

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


