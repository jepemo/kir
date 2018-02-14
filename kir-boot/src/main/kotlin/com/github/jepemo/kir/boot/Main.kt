package com.github.jepemo.kir.boot

import com.github.jepemo.kir.boot.cmd.*

fun printHelp(commands: List<Command>) {
    println("Usage: kb <action> [arguments]")
    println("List of actions:")
    println("\thelp:\tShow this help :D")
    for (cmd in commands) {
        println("\t" + cmd.command + ": \t" + cmd.shortHelp)
    }
}

fun main(args: Array<String>) {

    val commands = listOf(
        Compile(),
        Create(),
        Export(),
        Mongo(),
        Run(),
        Test()
    )

    if (args.isEmpty()) {
        printHelp(commands)
        System.exit(0)
    }

    val action = args[0]
    val arguments = if (args.size > 1) {
        args.asList().subList(1, args.size)
    }
    else {
        listOf<String>()
    }

    var cmdExists = false
    for (cmd in commands) {
        if (action == cmd.command) {
            cmdExists = true
            cmd.run(arguments)
        }
    }

    if (!cmdExists) {
        println("!! Command <$action> doesn't exists")
        printHelp(commands)
        System.exit(0)
    }
}
