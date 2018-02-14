package com.github.jepemo.kir.sampleapp.api

import com.github.jepemo.kir.web.http.Default
import com.github.jepemo.kir.web.http.Group
import com.github.jepemo.kir.web.http.HttpMethod
import com.github.jepemo.kir.web.http.Route


@Group("/user")
class UserService {
    @Default
    fun find() = {

    }

    @Default(method = arrayOf(HttpMethod.POST))
    fun postUser() = {

    }

    @Route("/:id")
    fun findById(id: String) = {

    }
}