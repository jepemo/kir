package com.github.jepemo.kir.examples.web.helloworld

import com.github.jepemo.kir.web.*

@Route("/")
fun hello () = "Hello World"

@Route("/hello/:to")
fun helloTo(to: String) = "Hello $to!"

fun main(args : Array<String>) {
    App.start();
}