package com.cheroliv.util

import com.cheroliv.util.App.Companion.MOTIF_FILENAME
import com.cheroliv.util.App.Companion.RESULT_FILENAME
import mu.KotlinLogging
import org.junit.BeforeClass
//import org.unix4j.Unix4j.find
//import org.unix4j.unix.find.FindOptionSets.INSTANCE
import java.io.File
import kotlin.test.*

private val log = KotlinLogging.logger {}

class AppTest {

    companion object {
        val USER_HOME_PATH: String = System.getProperty("user.home")

        @BeforeClass
        @JvmStatic
        fun setup() {
            val resultFile = File(RESULT_FILENAME)
            when {
                !resultFile.exists() -> resultFile.createNewFile()
            }
            App().builder(RESULT_FILENAME, MOTIF_FILENAME)
        }
    }

    @Test
    fun testAppHasAGreeting() {
        val classUnderTest = App()
        assertNotNull(classUnderTest.greeting,
                "app should have a greeting")
    }

    @Test
    fun result_file_exists() {
        val cmdResultFile = File(RESULT_FILENAME)
        assert(cmdResultFile.exists())
        assertFalse(cmdResultFile.isDirectory)
        assert(cmdResultFile.isFile)
    }

    @Test
    fun paths_end_with_MOTIF_FILENAME_or_empty() {
        File(RESULT_FILENAME).readText(Charsets.UTF_8).lineSequence().forEach {
            assert(it.contains(MOTIF_FILENAME) || it.isEmpty() || it.isBlank())
        }
    }

    @Test
    @Ignore
    fun paths_after_home_not_start_with_dot() {
        File(RESULT_FILENAME)
                .readText(Charsets.UTF_8)
                .lineSequence()
                .forEach {
                    assert(!it.replace(USER_HOME_PATH + File.separator, "")
                            .startsWith('.'))
                }
    }

    @Test
    @Ignore
    fun paths_contains_only_time_MOTIF_FILENAME() {
        File(RESULT_FILENAME).readText(Charsets.UTF_8).lineSequence().forEach {
            if (it.isBlank() || it.isEmpty())
                assertEquals(1, it.split(MOTIF_FILENAME).size - 1)
        }
    }


    fun testSth() {
        // find ~ -type d -name node_modules
        // locate *node_modules  > node_modules_file_paths.txt
//        print(find(
//                INSTANCE.d,
//                System.getProperty("user.home")+ "/src",
//                MOTIF_FILENAME).toStringResult())


    }
}
