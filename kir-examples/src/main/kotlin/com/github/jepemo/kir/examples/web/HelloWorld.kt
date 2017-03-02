package com.github.jepemo.kir.examples.web.helloworld

import com.github.jepemo.kir.web.*
import com.github.jepemo.kir.web.HttpResponse.*

@Route("/")
fun hello () = "Hello World"

@Route("/hello/:to")
fun helloTo(to: String) = "Hello $to!"

@Route("/goodbye")
fun goodbye() = Ok("Goodbye!")

fun main(args : Array<String>) {
    App.setupConsoleLog()
    App.start()
}