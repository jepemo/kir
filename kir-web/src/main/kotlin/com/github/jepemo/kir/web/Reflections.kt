package com.github.jepemo.kir.web

import java.io.File
import java.io.IOException
import java.util.*
import kotlin.reflect.KFunction
import kotlin.reflect.jvm.kotlinFunction


class Reflections {
    companion object {

        @Throws(ClassNotFoundException::class, IOException::class)
        fun getClasses(packageName: String): Array<Class<*>> {
            val classLoader = Thread.currentThread().contextClassLoader!!
            val path = packageName.replace('.', '/')
            val resources = classLoader.getResources(path)
            val dirs = ArrayList<File>()
            while (resources.hasMoreElements()) {
                val resource = resources.nextElement()
                dirs.add(File(resource.file))
            }
            val classes = ArrayList<Class<*>>()
            for (directory in dirs) {
                classes.addAll(findClasses(directory, packageName))
            }
            return classes.toTypedArray()
        }

        @Throws(ClassNotFoundException::class)
        private fun findClasses(directory: File, packageName: String): List<Class<*>> {
            val classes = ArrayList<Class<*>>()
            if (!directory.exists()) {
                return classes
            }
            val files = directory.listFiles()
            for (file in files!!) {
                if (file.isDirectory) {
                    assert(!file.name.contains("."))
                    classes.addAll(findClasses(file, packageName + "." + file.name))
                } else if (file.name.endsWith(".class")) {
                    classes.add(Class.forName(packageName + '.' + file.name.substring(0, file.name.length - 6)))
                }
            }
            return classes
        }

        @Throws(ClassNotFoundException::class)
        fun getCallingPackage(): Package? {
            val t = Thread.currentThread()
            val stackTrace = t.stackTrace
            val ste = stackTrace[4]
            val methodName = ste.methodName
            val className = ste.className
            var kls: Class<*>? = Class.forName(className)

            return kls!!.`package`
        }

        fun getPathRoutes(): Map<String, KFunction<*>> {
            val routes = HashMap<String, KFunction<*>>()

            val pkgName = Reflections.getCallingPackage()!!.getName()
            val classes = Reflections.getClasses(pkgName);

            for (c in classes) {
                for (m in c.methods) {
                    val an = m.getAnnotation(Route::class.java)
                    if (an != null) {
                        val path = an.path
                        routes.put(an.path, m.kotlinFunction!!);
                    }
                }
            }

            return routes
        }
    }
}