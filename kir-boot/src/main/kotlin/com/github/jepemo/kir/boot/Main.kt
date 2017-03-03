package com.github.jepemo.kir.boot

fun printHelp() {
    println("Usage: kb <action> [arguments]")
    println("List of actions:")
    println("\thelp: Show this help :D\t")
    println("\tcreate [name]: Create new *Kir* project\t")
    println("\tdebug: Run project in *Debug* mode\t")
    println("\trun: Run project\t")
    println("\tdeploy: Export as executable *jar*\t")
    println("\tmongo: Open *mongodb* console\t")
}

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        printHelp()
        System.exit(0)
    }

    val action = args[0]
    val arguments = if (args.size > 1) {
        args.asList().subList(1, args.size)
    }
    else {
        listOf<String>()
    }

    when (action) {
        "create" -> {
            CreateAction.run(arguments)
        }
        "run" -> {

        }
        "debug" -> {

        }
        "deploy" -> {

        }
        "mongo" -> {

        }
        else -> {
            printHelp()
            System.exit(0)
        }
    }
}
