package com.github.jepemo.kir.web.http.impl

import com.github.jepemo.kir.dom.HtmlDom
import com.github.jepemo.kir.web.http.HttpResponse
import com.github.jepemo.kir.web.http.HttpResponse.*
import com.github.jepemo.kir.web.http.View
import io.vertx.core.json.JsonObject
import java.util.*
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.KType
import kotlin.reflect.jvm.javaType

class ViewFunction(val path: String, val method: KFunction<*>) : View() {

    val params = if (method.parameters.isNotEmpty()) {
        extractParams(path)
    } else {
        ArrayList()
    }

    override fun get() : HttpResponse {
        val args = HashMap<KParameter, Any>()

        if (params.isNotEmpty()) {
            val urlValues = HashMap<String, String>()
            for(param in params) {
                val requestValue = cxt?.getParam(param)
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

        val res = createResponse(result, method.returnType)

        return res
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

    private fun extractParams(path: String) : List<String> {
        val params = ArrayList<String>()

        val regex = Regex(":([a-zA-Z]+)")
        for (mr in regex.findAll(path)) {
            params += mr.groupValues
        }

        return params
    }
}