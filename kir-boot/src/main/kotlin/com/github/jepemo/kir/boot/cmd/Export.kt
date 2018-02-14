package com.github.jepemo.kir.boot.cmd

class Export : Command () {
    override val command: String = "export"
    override val shortHelp: String = "Export to jar/war"
    override val fullHelp: String = ""

    override fun run(args: List<String>) {
    }
}