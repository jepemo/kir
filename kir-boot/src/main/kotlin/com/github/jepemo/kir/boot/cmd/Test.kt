package com.github.jepemo.kir.boot.cmd

class Test : Command () {
    override val command: String = "test"
    override val shortHelp: String = "Execute unit tests"
    override val fullHelp: String = ""

    override fun run(args: List<String>) {
    }
}
