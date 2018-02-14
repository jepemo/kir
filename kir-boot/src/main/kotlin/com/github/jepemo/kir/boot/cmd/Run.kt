package com.github.jepemo.kir.boot.cmd

class Run : Command () {
    override val command: String = "run"
    override val shortHelp: String = "Run project"
    override val fullHelp: String = ""

    override fun run(args: List<String>) {
    }
}
