package com.cheroliv.util

import com.cheroliv.util.App.Companion.FILTER_FILEPATH
import com.cheroliv.util.App.Companion.MOTIF_FILENAME
import com.cheroliv.util.App.Companion.RESULT_FILEPATH
import mu.KotlinLogging
import org.junit.BeforeClass
// import org.unix4j.Unix4j.find
// import org.unix4j.unix.find.FindOptionSets.INSTANCE
import java.io.File
import kotlin.test.*
import kotlin.text.Charsets.UTF_8

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
            assert(File(RESULT_FILEPATH).exists())
            if (!File(FILTER_FILEPATH).exists()) File(FILTER_FILEPATH).createNewFile()
            App().builder(RESULT_FILEPATH, MOTIF_FILENAME)
        }
    }

    @Test//test le retour de builder
    fun `paths end with MOTIF_FILENAME or empty`() {
        File(FILTER_FILEPATH)
                .readText(UTF_8)
                .lineSequence()
                .forEach {
                    assert(it.contains(MOTIF_FILENAME) ||
                            it.isEmpty() ||
                            it.isBlank())
                }
    }

    @Test
    @Ignore//test le retour de builder
    fun `paths after home not start with dot`() {
        File(FILTER_FILEPATH)
                .readText(UTF_8)
                .lineSequence()
                .forEach {
                    assert(!it.replace(USER_HOME_PATH + File.separator, "")
                            .startsWith('.'))
                }
    }

    @Test
    @Ignore//test le retour de builder
    fun `paths contains only time MOTIF_FILENAME`() {
        File(FILTER_FILEPATH).readText(UTF_8).lineSequence().forEach {
            if (it.isBlank() || it.isEmpty())
                assertEquals(1, it.split(MOTIF_FILENAME).size - 1)
        }
    }

    @Test//test le retour de builder
    fun `build folder path cannot be in FILTER_FILEPATH`() {
    }

    @Test//test le retour de builder
    fun `test method isPathContainsMotifOnce()`() {
    }

    @Test//test le retour de builder
    fun `test method isPathStartWithDotAfterHome()`() {
    }
}
