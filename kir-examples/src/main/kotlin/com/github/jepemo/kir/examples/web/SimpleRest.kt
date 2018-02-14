package com.github.jepemo.kir.examples.web.simplerest

import com.github.jepemo.kir.web.http.App
import com.github.jepemo.kir.web.http.HttpMethod
import com.github.jepemo.kir.web.http.HttpResponse.*
import com.github.jepemo.kir.web.http.Route

@Route("/user/:id")
fun findUser(id: String) = mapOf("id" to id, "name" to "Sherlock", "surname" to "Holmes", "active" to true)

@Route("/user", method = arrayOf(HttpMethod.GET))
fun allUsers() =
    mapOf(
        "result" to listOf(
            mapOf("id" to 1, "name" to "Sherlock", "surname" to "Holmes", "active" to true),
            mapOf("id" to 2, "name" to "John", "surname" to "Watson", "active" to true),
            mapOf("id" to 3, "name" to "James", "surname" to "Moriarty", "active" to false)
        )
    )

@Route("/movie")
fun allMovies() = Json("{ \"key\" : 1  }")

fun main(args : Array<String>) {
    App.start()
}


