package com.github.jepemo.kir.web

annotation class Route(val path: String)

class App {
    companion object {
        val server = KirHttpServer()

        fun addRoute(path: String, handler: () -> Unit) {
            server.addRoute(path, handler)
        }

        fun start() {
            val pathRoutes = Reflections.getPathRoutes()
            for((path, method) in pathRoutes) {
                addRoute(path, {})
            }

            // Start
            server.start()
        }
    }
}


