package com.cheroliv.util

import com.cheroliv.util.App.Companion.MOTIF_FILENAME
import com.cheroliv.util.App.Companion.RESULT_FILEPATH
import java.io.File
import kotlin.text.Charsets.UTF_8

fun main() {
    App().builder(RESULT_FILEPATH, MOTIF_FILENAME)
}

class App {

    companion object {
        const val FILTER_FILEPATH: String = "result.txt"
        const val RESULT_FILEPATH: String = "src/test/resources/node_modules_file_paths.txt"
        const val MOTIF_FILENAME: String = "node_modules"
    }

    fun builder(path: String, motif: String) {
        val pathsFile = File(path)
        when {
            pathsFile.exists() && pathsFile.isFile -> {
                val pathsSequence = pathsFile.readText(UTF_8).lineSequence()
                // for ((index, value) in pathsSequence.withIndex()) {
                //println("The element at $index is $value")
                //}
                // 1) `paths after home not start with dot`
                // 2) `paths contains only time MOTIF_FILENAME`
                pathsSequence.forEach {
                    if (!isPathStartWithDotAfterHome(it) && isPathContainsMotifOnce(it)) {
                        //put this path in the result file
                    }
                }


                val resultFile = File(FILTER_FILEPATH)
                when {
                    resultFile.exists() && resultFile.isFile -> {
                        resultFile.readText(UTF_8).lineSequence().forEach {
                            when {
                                !(it.isBlank() || it.isEmpty()) -> {
                                    val currentFile = File(it)
                                    when {
                                        currentFile.exists() && currentFile.isDirectory ->
                                            currentFile.deleteRecursively()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun isPathContainsMotifOnce(it: String): Boolean {

        return true
    }

    fun isPathStartWithDotAfterHome(it: String): Boolean {
        return true
    }
}


