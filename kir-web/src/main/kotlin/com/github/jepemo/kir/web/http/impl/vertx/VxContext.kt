package com.github.jepemo.kir.web.http.impl.vertx

import com.github.jepemo.kir.web.http.Context
import io.vertx.ext.web.RoutingContext

/**
 * Created by jere on 29/03/17.
 */

class VxContext(val routingContext: RoutingContext) : Context {
    override fun getParam(paramName: String): String {
        return routingContext.request().getParam(paramName)
    }
}