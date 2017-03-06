package com.github.jepemo.kir.boot.cmd

import java.io.*
import java.util.ArrayList


object Colors {
    val RESET = "\u001B[0m"
    val BOLD_WHITE = "\u001B[1;37m"
    val BOLD_GREEN = "\u001B[32m"
}

object Utils {
    fun printLogo() {
        println("""${Colors.BOLD_GREEN}
#    # ### ######
#   #   #  #     #
#  #    #  #     #
###     #  ######
#  #    #  #   #
#   #   #  #    #
#    # ### #     #
    ${Colors.RESET}""")
    }

    fun printBold(text: String) {
        print("${Colors.BOLD_WHITE}$text${Colors.RESET}")
    }

    fun printlnBold(text: String) {
        println("${Colors.BOLD_WHITE}$text${Colors.RESET}")
    }

    fun makeDir(path: String): File {
        val result = File(path)
        result.mkdirs()
        return result
    }

//    fun createAndWrite(path: String, content: String) {
//        File(path).bufferedWriter().use { out ->
//            out.write(content)
//        }
//    }

    fun readCheckedInput(prompt: String, defaultValue: String) : String {
        var input : String? = defaultValue

        printBold("< $prompt [$defaultValue] ")
        input = readLine()

        if (input == null || input.trim() == "") {
            input = defaultValue
        }

        return input
    }

    /**
     * Read file from resources directory, replace vars and write to outputPath
     */
    fun readAndWrite(inputPath: String, outputPath: String, vars: Map<String, String>) {
        println("\t > Creating: " + outputPath)

        val stream = Thread.currentThread().contextClassLoader.getResourceAsStream(inputPath)

        val reader = BufferedReader(InputStreamReader(stream))
        var allText = reader.readText()

        for ((k, v) in vars) {
            allText = allText.replace("__" + k + "__", v)
        }

        File(outputPath).bufferedWriter().use { out ->
            out.write(allText)
        }
    }

    @Throws(IOException::class)
    private fun getResourceFiles(path: String): List<String> {
        val filenames = ArrayList<String>()

        getResourceAsStream(path).use({ input ->
            BufferedReader(InputStreamReader(input)).use { br ->
                var resource: String? = br.readLine()

                while (resource != null) {
                    filenames.add(resource)
                    resource = br.readLine()
                }
            }
        })

        return filenames
    }

    private fun getResourceAsStream(resource: String): InputStream {
        val input = getContextClassLoader().getResourceAsStream(resource)

        val rsc = Utils::class.java.getResourceAsStream(resource)

        println("Resource:" + rsc)

        return input ?: rsc
    }

    private fun getContextClassLoader(): ClassLoader {
        return Thread.currentThread().contextClassLoader
    }
}
