package com.github.jepemo.kir.web

import java.io.IOException
import java.io.File
import java.util.concurrent.atomic.AtomicReference
import com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil.close
import java.lang.reflect.Method
import java.util.*


/**
 * Created by jere on 16/02/17.
 */

class Reflections {
    companion object {
        /**
         * Scans all classes accessible from the context class loader which belong to the given package and subpackages.

         * @param packageName The base package
         * *
         * @return The classes
         * *
         * @throws ClassNotFoundException
         * *
         * @throws IOException
         */
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

        /**
         * Recursive method used to find all classes in a given directory and subdirs.

         * @param directory   The base directory
         * *
         * @param packageName The package name for classes found inside the base directory
         * *
         * @return The classes
         * *
         * @throws ClassNotFoundException
         */
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

        /*
        @Throws(Exception::class)
        fun getMethod(stackTraceElement: StackTraceElement): Method {
            val stackTraceClassName = stackTraceElement.className
            val stackTraceMethodName = stackTraceElement.methodName
            val stackTraceLineNumber = stackTraceElement.lineNumber
            val stackTraceClass = Class.forName(stackTraceClassName)

            // I am only using AtomicReference as a container to dump a String into, feel free to ignore it for now
            val methodDescriptorReference = AtomicReference<String>()

            val classFileResourceName = "/" + stackTraceClassName.replace("\\.".toRegex(), "/") + ".class"
            val classFileStream = stackTraceClass.getResourceAsStream(classFileResourceName) ?: throw RuntimeException("Could not acquire the class file containing for the calling class")

            try {
                val classReader = ClassReader(classFileStream)
                classReader.accept(
                        object : EmptyVisitor() {
                            fun visitMethod(access: Int, name: String, desc: String, signature: String, exceptions: Array<String>): MethodVisitor? {
                                if (name != stackTraceMethodName) {
                                    return null
                                }

                                return object : EmptyVisitor() {
                                    fun visitLineNumber(line: Int, start: Label) {
                                        if (line == stackTraceLineNumber) {
                                            methodDescriptorReference.set(desc)
                                        }
                                    }
                                }
                            }
                        },
                        0
                )
            } finally {
                classFileStream.close()
            }

            val methodDescriptor = methodDescriptorReference.get() ?: throw RuntimeException("Could not find line " + stackTraceLineNumber)

            for (method in stackTraceClass.methods) {
                if (stackTraceMethodName == method.name && methodDescriptor == Type.getMethodDescriptor(method)) {
                    return method
                }
            }

            throw RuntimeException("Could not find the calling method")
        }
        */

        @Throws(ClassNotFoundException::class)
        fun getCallingPackage(): Package? {
            val t = Thread.currentThread()
            val stackTrace = t.stackTrace
            val ste = stackTrace[4]
            val methodName = ste.methodName
            val className = ste.className
            var kls: Class<*>? = Class.forName(className)

            return kls!!.`package`
            /*
            do {
                println(kls!!.`package`)
                for (candidate in kls!!.declaredMethods) {
                    if (candidate.name.equals(methodName)) {
                        //return candidate
                        return candidate.javaClass.`package`
                    }
                }
                kls = kls.superclass
            } while (kls != null)
            return null
            */
        }

        fun getPathRoutes(): Map<String, Method> {
            val routes = HashMap<String, Method>()

            val pkgName = Reflections.getCallingPackage()!!.getName()
            val classes = Reflections.getClasses(pkgName);

            for (c in classes) {
//                println("--> " + c.canonicalName)
                for (m in c.methods) {
//                    println("---> " + m)
                    val an = m.getAnnotation(Route::class.java)
                    if (an != null) {
                        val path = an.path
                        routes.put(an.path, m);
                    }
                }
            }

            return routes
        }
    }
}