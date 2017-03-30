package com.github.jepemo.kir.web.http.impl.vertx

import com.github.jepemo.kir.dom.HtmlDom
import com.github.jepemo.kir.web.http.Context
import com.github.jepemo.kir.web.http.HttpResponse
import com.github.jepemo.kir.web.http.HttpResponse.*
import com.github.jepemo.kir.web.http.KirHttpServer
import com.github.jepemo.kir.web.http.View
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServerOptions
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import mu.KLogging
import java.util.*
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.KType
import kotlin.reflect.jvm.javaType

class VxHttpServer : KirHttpServer {
    companion object: KLogging()

    override var host: String = "localhost"
        get() =  field
        set (value){
            field = value
        }

    override var port: Int = 8080
        get() = field
        set(value) {
            field = value
        }

    val vertx = Vertx.vertx()
    val router = Router.router(vertx)

    init {
        router.route().handler(BodyHandler.create())
    }

    override fun start() {
        val options: HttpServerOptions = HttpServerOptions()
            options.port = port
            options.host = host

        vertx.createHttpServer(options).requestHandler{ router.accept(it) }.listen()
    }

    override fun addRoute(path: String, view: View) {
//        KLogging.logger.info { "* Registering: " + path }
        router.get(path).handler { routingContext ->
            view.cxt = toKirContext(routingContext)
            val res = view.get()

            val response = routingContext.response()

            response.statusCode = res.statusCode
            response.putHeader("content-type", res.contentType)
            response.end(res.out)
        }
        router.post(path).handler { routingContext ->
            view.cxt = toKirContext(routingContext)
            val res = view.post()

            val response = routingContext.response()

            response.statusCode = res.statusCode
            response.putHeader("content-type", res.contentType)
            response.end(res.out)
        }
    }

    private fun toKirContext(routingContext: RoutingContext?): Context? {
        return VxContext(routingContext!!)
    }

    override fun addRoute(path: String, method: KFunction<*>) {
//        KLogging.logger.info { "* Registering: " + path }

        val params = if (method.parameters.isNotEmpty()) {
            extractParams(path)
        } else {
            ArrayList()
        }

        router.get(path).handler({ routingContext ->
            val args = HashMap<KParameter, Any>()

            if (params.isNotEmpty()) {
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

            val result = if (method.parameters.isEmpty()) {
                method.call()
            }
            else {
                // parameter #0 to of fun helloTo(kotlin.String): kotlin.String -> world
//                println ("Arguments:")
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
        if (type.javaType.typeName == "java.lang.String") {
            httpResponse = Text(result as String)
        }
        else if (type.javaType.typeName.startsWith("java.util.Map")) {
            httpResponse = Json(JsonObject(result as Map<String, Any?>).toString())
        }
        else if (type.javaType.typeName.startsWith("com.github.jepemo.kir.dom.HtmlDom")) {
            httpResponse = Html((result as HtmlDom).content)
        }
        else if (result is HttpResponse) {
            httpResponse = result
        }
        else {
//            KLogging.logger.error { "Incorrect return type <$type> for router" }
            System.exit(1)
        }

        return httpResponse
    }

    // TODO: Review, incorrect regular expression
    private fun extractParams(path: String) : List<String> {
        val params = ArrayList<String>()

        val regex = Regex(":([a-zA-Z]+)")
        for (mr in regex.findAll(path)) {
            params += mr.groupValues
        }

        return params
    }
}
