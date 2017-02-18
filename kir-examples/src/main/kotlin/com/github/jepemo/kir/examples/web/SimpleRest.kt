package com.github.jepemo.kir.examples.web.simplerest

import com.github.jepemo.kir.web.App
import com.github.jepemo.kir.web.HttpResponse.*
import com.github.jepemo.kir.web.Route

@Route("/user/:id")
fun findUser(id: Int) = mapOf("id" to id, "name" to "Sherlock", "surname" to "Holmes", "active" to true)

@Route("/user")
fun allUsers() = listOf<Map<String, Any>>(
    mapOf("id" to 1, "name" to "Sherlock", "surname" to "Holmes", "active" to true),
    mapOf("id" to 2, "name" to "John", "surname" to "Watson", "active" to true),
    mapOf("id" to 3, "name" to "James", "surname" to "Moriarty", "active" to false)
)

@Route("/hello")
fun hello() = Ok("Hello World")

fun main(args : Array<String>) {
    App.start();
}


