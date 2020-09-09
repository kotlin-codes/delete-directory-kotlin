package com.cheroliv.util

// import org.unix4j.Unix4j.find
// import org.unix4j.unix.find.FindOptionSets.INSTANCE
import com.cheroliv.util.App.Companion.KEEP
import com.cheroliv.util.App.Companion.LOCATE_CMD_FILEPATH
import com.cheroliv.util.App.Companion.MOTIF_FILENAME
import mu.KotlinLogging
import org.junit.BeforeClass
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

private val log = KotlinLogging.logger {}

// pass password for sudo command
// echo mypassword | sudo -S command
//
// find ~ -type d -name node_modules
// locate *node_modules  > node_modules_file_paths.txt
//        print(find(
//                INSTANCE.d,
//                System.getProperty("user.home")+ "/src",
//                MOTIF_FILENAME).toStringResult())
class AppTest {

    companion object {
        val USER_HOME_PATH: String = System.getProperty("user.home")

        @BeforeClass
        @JvmStatic
        fun setup() {
            // Ensure my set of data is available
            val file = File(LOCATE_CMD_FILEPATH)
            assert(file.exists())
            assertFalse(file.isDirectory)
            assert(file.isFile)
            assert(file.readText(Charsets.UTF_8).lineSequence().count() > 1)
        }
    }

    @Test
    fun `paths end with MOTIF_FILENAME`() {
        val paths = readFile(LOCATE_CMD_FILEPATH)
        val filteredPaths = filterPathSequence(paths, MOTIF_FILENAME, KEEP)
        assert(filteredPaths.count() > 0) { "filtered paths should not be empty!" }
        filteredPaths.forEach {
            assert(it.contains(MOTIF_FILENAME))
        }
    }

    @Test
    fun `paths after home not start with dot`() {
        val paths = readFile(LOCATE_CMD_FILEPATH)
        val filteredPaths = filterPathSequence(paths, MOTIF_FILENAME, KEEP)
        filteredPaths.forEach {
            assert(!it.replace(USER_HOME_PATH + File.separator, "")
                    .startsWith('.'))
        }
    }

    @Test
    fun `paths contains only once MOTIF_FILENAME`() {
        val paths = readFile(LOCATE_CMD_FILEPATH)
        val filteredPaths = filterPathSequence(paths, MOTIF_FILENAME, KEEP)
        filteredPaths.forEach {
            assertEquals(1, it.split(MOTIF_FILENAME).size - 1)
        }
    }

    @Test
    fun `paths must be in the home directory`() {
        val paths = readFile(LOCATE_CMD_FILEPATH)
        val filteredPaths = filterPathSequence(paths, MOTIF_FILENAME, KEEP)
        filteredPaths.forEach {
            assert(it.startsWith(USER_HOME_PATH))
        }
    }

    @Test
    fun `paths don't contains KEEP`() {
        val paths = readFile(LOCATE_CMD_FILEPATH)
        val filteredPaths = filterPathSequence(paths, MOTIF_FILENAME, KEEP)
        var isFoundKeepItem = false
        loop1@ for (pathTokeep in KEEP) {
            loop2@ for (path in filteredPaths) {
                if (path == pathTokeep) {
                    isFoundKeepItem = true
                    break@loop1
                }
            }
        }
        assertFalse(isFoundKeepItem)
    }

    @Test
    fun `test method isPathContainsMotifOnce()`() {
        val path = "/home/cheroliv/Téléchargements/React-Portfolio-master/node_modules"
        assert(isPathContainsMotifOnce(path, MOTIF_FILENAME))
        assertFalse(isPathContainsMotifOnce(path.replace(
                File.separator + MOTIF_FILENAME, ""),
                MOTIF_FILENAME))
    }


    @Test
    fun `test method isPathStartWithDotAfterHome()`() {
        var path = "/home/cheroliv/Téléchargements/React-Portfolio-master/node_modules"
        assertFalse(isPathStartWithDotAfterHome(path))
        path = "/home/cheroliv/.nvm/versions/node/v12.18.2/lib/node_modules"
        assert(isPathStartWithDotAfterHome(path))
    }

}
