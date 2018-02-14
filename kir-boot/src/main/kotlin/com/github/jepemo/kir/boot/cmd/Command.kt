package com.github.jepemo.kir.boot.cmd

abstract class Command {
    abstract val command : String
    abstract val shortHelp : String
    abstract val fullHelp : String
    abstract fun run(args : List<String>)
}
