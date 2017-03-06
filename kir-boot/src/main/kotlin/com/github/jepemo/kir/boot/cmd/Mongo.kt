package com.github.jepemo.kir.boot.cmd


class Mongo : Command () {
    override val command: String = "mongo"
    override val shortHelp: String = "Open *mongodb* console"
    override val fullHelp: String = ""

    override fun run(args: List<String>) {
    }
}
