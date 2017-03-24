package com.github.jepemo.kir.sampleapp.api

import com.github.jepemo.kir.web.Default
import com.github.jepemo.kir.web.Group
import com.github.jepemo.kir.web.HttpMethod
import com.github.jepemo.kir.web.Route


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