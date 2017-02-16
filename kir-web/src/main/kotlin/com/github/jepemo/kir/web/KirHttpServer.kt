package com.github.jepemo.kir.web

import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler

/**
 * Created by jere on 16/02/17.
 */

class KirHttpServer {
    val vertx = Vertx.vertx()
    val router = Router.router(vertx)

    constructor () {
        router.route().handler(BodyHandler.create());
    }

    fun start() {
//        server.requestHandler { request ->
//
//            // This handler gets called for each request that arrives on the server
//            val response = request.response()
//            response.putHeader("content-type", "text/plain")
//
//            // Write to the response and end it
//            response.end("Hello World!")
//        }

//        val server = vertx.createHttpServer()
//        server.requestHandler(router::class).listen(8080);
//        server.listen(8080)


        vertx.createHttpServer().requestHandler{ router.accept(it) }.listen(8080);
    }

    fun addRoute(path: String, handler: () -> Unit) {
        router.get(path).handler({ routingContext ->
            println(routingContext)
        })
    }
}
