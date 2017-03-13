package com.github.jepemo.kir.boot.cmd

class Create : Command () {
    override val command : String = "create"
    override val shortHelp : String = "Create new *Kir* project"
    override val fullHelp : String = ""

    fun createBuildGradle(projectName: String, packageName: String, template: String) {
        Utils.readAndWrite("$template/build.gradle", projectName+"/build.gradle", mapOf(
                "KOTLIN_VERSION" to "1.1.0",
                "KOTLIN_HTML_VERSION" to "0.6.2",
                "PKG_NAME" to packageName,
                "VERSION" to "0.1"
        ))
    }

    override fun run(args: List<String>) {
        Utils.printLogo()

        val projectName = if (!args.isEmpty()) {
            args[0]
        }
        else {
            Utils.readCheckedInput("Project name", "myproject")
        }

        val template = Utils.readCheckedInput("Boot template (blank, rest,...)", "blank")
        val packageName = Utils.readCheckedInput("Package name", "com.mycompany.$projectName")

        Utils.printlnBold("> Creating project *$projectName*")
        Utils.makeDir(projectName)

        // Create libs directory
        Utils.printlnBold("> Copying dependencies")

        // Copy jar libs
        Utils.printlnBold("> Creating $template app structure")
        createBuildGradle(projectName, packageName, template)
        val pkgPath = packageName.replace(".", "/")
        Utils.makeDir("$projectName/src/main/kotlin/$pkgPath/")

        // FIXME: Copiar los ficheros que hay dentro de los resources
        Utils.makeDir("$projectName/src/main/kotlin/$pkgPath/boot")
        Utils.makeDir("$projectName/src/main/kotlin/$pkgPath/models")
        Utils.makeDir("$projectName/src/main/kotlin/$pkgPath/controllers")
        Utils.makeDir("$projectName/src/main/kotlin/$pkgPath/views")

        Utils.printlnBold("> Done. Type: ")
        println("   cd $projectName")
        println("   kb debug")
    }
}
