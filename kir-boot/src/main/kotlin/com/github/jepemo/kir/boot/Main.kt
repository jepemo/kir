package com.github.jepemo.kir.boot

fun printHelp() {
    println("Usage: kb <action> [arguments]")
    println("List of actions:")
    println("\thelp:\t")
    println("\tcreate:\t")
    println("\trun:\t")
    println("\tdebug:\t")
    println("\tdeploy:\t")
    println("\tmongo:\t")
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
