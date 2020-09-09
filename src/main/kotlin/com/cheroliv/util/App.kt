package com.cheroliv.util

import org.unix4j.Unix4j.find
import org.unix4j.unix.find.FindOptionSets.INSTANCE
import com.cheroliv.util.App.Companion.KEEP
import com.cheroliv.util.App.Companion.MOTIF_FILENAME
import com.cheroliv.util.App.Companion.LOCATE_CMD_FILEPATH
import org.apache.commons.lang3.StringUtils
import java.io.File
import kotlin.text.Charsets.UTF_8

class App {
    companion object {
        const val LOCATE_CMD_FILEPATH = "src/test/resources/node_modules_file_paths.txt"
        const val MOTIF_FILENAME = "node_modules"
        val KEEP = arrayOf("/home/cheroliv/src/deleteDir/build")
    }
}

fun main() {
    val paths = readFile(LOCATE_CMD_FILEPATH)
    val filteredPaths = filterPathSequence(
            paths,
            MOTIF_FILENAME,
            KEEP)
    deleteFiles(filteredPaths)
}

// pass password for sudo command
// echo mypassword | sudo -S command
//
// find ~ -type d -name node_modules
// locate *node_modules  > node_modules_file_paths.txt
fun findFile(path: String, motif: String): Sequence<String> {
    return find(INSTANCE.d, path, motif).toString().lineSequence()
}

fun readFile(path: String): Sequence<String> {
    val pathsFile = File(path)
    when {
        pathsFile.exists() && pathsFile.isFile -> {
            return pathsFile.readText(UTF_8).lineSequence()
        }

    }
    return emptySequence()
}

fun deleteFiles(paths: Sequence<String>) {
    paths.forEach {
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

fun isPathContainsMotifOnce(path: String, motif: String): Boolean {
    return path.contains(motif) && StringUtils.countMatches(path, motif) == 1
}

fun isPathStartWithDotAfterHome(path: String): Boolean {
    val userHomepath = System.getProperty("user.home")
    return path.contains(userHomepath) &&
            path.startsWith(userHomepath + File.separator) &&
            path.replace(userHomepath + File.separator, "")
                    .startsWith(".")
}

fun filterPathSequence(paths: Sequence<String>, motif: String, keep: Array<String>): Sequence<String> {
    val filteredPaths: ArrayList<String> = arrayListOf()
    paths.forEach {
        // 1) `paths after home not start with dot`
        // 2) `paths contains only time MOTIF_FILENAME`
        // 3) paths not element of keep
        // 3) paths must be in the home directory
        when {
            !isPathStartWithDotAfterHome(it)
                    && isPathContainsMotifOnce(it, motif)
                    && !keep.contains(it)
                    && it.startsWith(System.getProperty("user.home"))
            -> filteredPaths.add(it)
        }
    }
    return filteredPaths.asSequence()
}

