package com.github.jepemo.kir.boot

import java.io.File



fun printLogo() {
    println("")
    println("#    # ### ######  ")
    println("#   #   #  #     # ")
    println("#  #    #  #     # ")
    println("###     #  ######  ")
    println("#  #    #  #   #   ")
    println("#   #   #  #    #  ")
    println("#    # ### #     # ")
    println("")
}

fun makeDir(path: String): File {
    val result = File(path)
    result.mkdirs()
    return result
}

object CreateAction {
    fun run(args: List<String>) {
        printLogo()
        val projectName: String?
        if (!args.isEmpty()) {
            projectName = args[0]
        }
        else {
            print("<Project name:")
            projectName = readLine()
        }

        println(">Creating project *$projectName*")
        makeDir(projectName!!)

        // Create libs directory
        println(">Copying dependencies")

        // Copy jar libs

        println(">Creating blank app structure")
        // Create files

        println(">Done. Type: ")
        println("\tkb debug")
    }
}
