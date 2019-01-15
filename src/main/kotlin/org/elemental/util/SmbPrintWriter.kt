package org.elemental.util

import java.io.*
import java.nio.charset.Charset
import java.util.Stack

class SmbPrintWriter internal constructor(writer: Writer,
                                          private val outputDeviceName: String,
                                          private val process: Process) : PrintWriter(writer) {

    override fun println() {
        synchronized(lock) {
            write("\r")
            write("\n")
        }
    }
    override fun close() {
        super.close()
        try {
            if (process.waitFor() == 0) {

                println("waitFor = 0")
                return
            }
        } catch (ex: InterruptedException) {
            throw RuntimeException(ex)
        }
        val errors = BufferedReader(InputStreamReader(process.inputStream))
        val errorsLines = Stack<String>()
        try {
            while(errors.readLine() != null) {
                errorsLines.push(errors.readLine())
            }
        } catch (ex: IOException) {
            throw RuntimeException(ex)
        }
        throw RuntimeException("\"$outputDeviceName\": ${errorsLines.pop()}")
    }
    companion object {
        private val WINDOWS_1252 = Charset.forName("WINDOWS-1252")!!
        private fun makeCommand(printerName: String): List<String> = listOf("smbclient", printerName, "-N", "-c", "print -")
        private fun makePrinterWriter(outputDeviceName: String, command: List<String>): SmbPrintWriter {
            try {
                println(command)
                val process = ProcessBuilder(command)
                        .redirectErrorStream(true)
                        .start()
                val outputStream = process.outputStream
                val writer = OutputStreamWriter(outputStream, WINDOWS_1252)
                return SmbPrintWriter(writer, outputDeviceName, process)
            } catch (e: Exception) {
                throw RuntimeException(e.toString())
            }
        }
        fun newInstance(outputDeviceName: String): SmbPrintWriter {
            val command = makeCommand(outputDeviceName.replace('\\', '/'))
            return makePrinterWriter(outputDeviceName, command)
        }
    }
}