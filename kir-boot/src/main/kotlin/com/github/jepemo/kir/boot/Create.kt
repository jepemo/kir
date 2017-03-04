package com.github.jepemo.kir.boot

import java.io.File

fun printLogo() {
    println("""
#    # ### ######
#   #   #  #     #
#  #    #  #     #
###     #  ######
#  #    #  #   #
#   #   #  #    #
#    # ### #     #
    """)
}

fun makeDir(path: String): File {
    val result = File(path)
    result.mkdirs()
    return result
}

fun createAndWrite(path: String, content: String) {
    File(path).bufferedWriter().use { out ->
        out.write(content)
    }
}

fun createBuildGradle(projectName: String, packageName: String) {
    val kotlin_version = "1.1.0"

    createAndWrite(projectName+"/build.gradle", """
group '$packageName'
version '0.1'

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    }
}

apply plugin: 'kotlin'
apply plugin: 'application'

mainClassName = '$packageName.boot.MainKt'

jar {
    manifest {
        attributes 'Main-Class': '$packageName.boot.MainKt'
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compile "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
}
        """)
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

        print("<Package name:")
        val packageName = readLine()

        println(">Creating project *$projectName*")
        makeDir(projectName!!)

        // Create libs directory
        println(">Copying dependencies")

        // Copy jar libs
        println(">Creating blank app structure")
        createBuildGradle(projectName, packageName!!)
        val pkgPath = packageName.replace(".", "/")
        makeDir("$projectName/src/main/kotlin/$pkgPath/boot")
        makeDir("$projectName/src/main/kotlin/$pkgPath/view")

        println(">Done. Type: ")
        println("   cd $projectName")
        println("   kb debug")
    }
}
