package com.github.jepemo.kir.web

import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import mu.KLogging
import java.util.*
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.jvm.javaType

class KirHttpServer (var port: Int = 8080) {
    companion object: KLogging()

    val vertx = Vertx.vertx()
    val router = Router.router(vertx)

    init {
        router.route().handler(BodyHandler.create());
    }

    fun start() {
        vertx.createHttpServer().requestHandler{ router.accept(it) }.listen(port);
    }

    fun addRoute(path: String, handler: (RoutingContext) -> Unit) {
        router.get(path).handler(handler)
    }

    fun addRoute(path: String, method: KFunction<*>) {
        logger.debug { "* Registering: " + path }
        if (method.returnType.javaType.typeName.equals("java.lang.String")) {
            if (method.parameters.size == 0) {
                router.get(path).handler({ routingContext ->
                    val response = routingContext.response()
                    val result = method.call()
                    response.putHeader("content-type", "text/plain")
                    response.end("" + result)
                })
            }
            else {
                val params = extractParams(path)
                router.get(path).handler({ routingContext ->
                    val urlValues = HashMap<String, String>()
                    for(param in params) {
                        val requestValue = routingContext.request().getParam(param)
                        if (requestValue != null) {
                            urlValues.put(param, requestValue)
                        }
                    }

                    val args = HashMap<KParameter, Any>()
                    for (kparam in method.parameters) {
                        for (urlValue in urlValues) {
                            if (kparam.name.equals(urlValue.key)) {
                                args.put(kparam, urlValue.value)
                            }
                        }
                    }

                    val result = method.callBy(args)
                    val response = routingContext.response()
                    response.putHeader("content-type", "text/plain")
                    response.end("" + result)
                })
            }
        }
        else if (method.returnType.javaType.typeName.startsWith("java.util.List")) {
            println("List")
        }
        else if (method.returnType.javaType.typeName.startsWith("java.util.Map")) {
            println("Map")
        }
        else if (method.returnType is HttpResponse) {
            println("HttpResponse")
        }
    }

    // TODO: Review, incorrect regular expression
    private fun extractParams(path: String) : List<String> {
        val params = ArrayList<String>()

        val regex = Regex("\\:([a-zA-Z]+)")
        for (mr in regex.findAll(path)) {
            for (gValue in mr.groupValues) {
                params.add(gValue)
            }
        }

        return params
    }
}
