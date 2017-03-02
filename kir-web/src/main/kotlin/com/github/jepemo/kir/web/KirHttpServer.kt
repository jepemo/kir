package com.github.jepemo.kir.web

import com.github.jepemo.kir.web.HttpResponse.Error
import com.github.jepemo.kir.web.HttpResponse.Text
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import mu.KLogging
import java.util.*
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.KType
import kotlin.reflect.full.cast
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.javaType

class KirHttpServer (var port: Int = 8080) {
    companion object: KLogging()

    val vertx = Vertx.vertx()
    val router = Router.router(vertx)

    init {
        router.route().handler(BodyHandler.create())
    }

    fun start() {
        vertx.createHttpServer().requestHandler{ router.accept(it) }.listen(port);
    }

    fun addRoute(path: String, handler: (RoutingContext) -> Unit) {
        router.get(path).handler(handler)
    }

    fun addRoute(path: String, method: KFunction<*>) {
        logger.info { "* Registering: " + path }

        val params = if (method.parameters.size > 0) {
            extractParams(path)
        } else {
            ArrayList()
        }

        router.get(path).handler({ routingContext ->
            val args = HashMap<KParameter, Any>()

            if (params != null && params.size > 0) {
                val urlValues = HashMap<String, String>()
                for(param in params) {
                    val requestValue = routingContext.request().getParam(param)
                    if (requestValue != null) {
                        urlValues.put(param, requestValue)
                    }
                }

                for (kparam in method.parameters) {
                    for ((key, value) in urlValues) {
                        if (kparam.name.equals(key)) {
                            args.put(kparam, value)
                        }
                    }
                }
            }

            val result = if (method.parameters.size == 0) {
                method.call()
            }
            else {
                // parameter #0 to of fun helloTo(kotlin.String): kotlin.String -> world
                println ("Arguments:")
                for ((k, v) in args) {
                    println ("$k -> $v")
                }

                method.callBy(args)
            }

            val response = routingContext.response()

            val res = createResponse(result, method.returnType)

            response.statusCode = res.statusCode
            response.putHeader("content-type", res.contentType)
            response.end(res.out)
        })
    }

    private fun  createResponse(result: Any?, type: KType): HttpResponse {
        var httpResponse : HttpResponse = Error()
        if (type.javaType.typeName.equals("java.lang.String")) {
            httpResponse = Text(result as String)
        }
        else if (type.javaType.typeName.startsWith("java.util.Map")) {
            httpResponse = HttpResponse.Json(JsonObject(result as Map<String, Any?>).toString())
        }
        else if (result is HttpResponse) {
            httpResponse = result
        }
        else {
            logger.error { "Incorrect return type <$type> for router" }
            System.exit(1)
        }

        return httpResponse
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
