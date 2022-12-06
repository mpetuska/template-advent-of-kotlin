import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.io.File
import java.util.*
import java.util.stream.Stream
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

typealias Lines = List<String>

abstract class Day {
    private val year = Calendar.getInstance().get(Calendar.YEAR)
    private val day by lazy { this::class.java.simpleName.removePrefix("Day") }

    abstract fun part1(input: Lines): Any
    abstract fun part2(input: Lines): Any

    private fun readInput(inputFileName: String) =
        this::class.java.getResource(inputFileName)?.toURI()?.let(::File)?.takeIf { it.length() > 0 }

    @OptIn(ExperimentalTime::class)
    private fun solve(inputFileName: String, backupInputFileName: String?, solution: (Lines) -> Any) {
        val input = readInput(inputFileName)
            ?: backupInputFileName?.let(::readInput)
            ?: error("$inputFileName not found")
        val lines = input.readLines()
        val (answer, duration) = measureTimedValue { solution(lines) }
        val time = duration.toComponents { seconds, nanoseconds -> "${seconds}s ${nanoseconds}ns" }
        pushToClipboard(answer.toString())
        println(
            """
            Solution took ${duration.inWholeNanoseconds}ns or $time.
                Input: $inputFileName
                Answer: $answer
                Answer copied to clipboard. 
                Don't forget to submit it at https://adventofcode.com/$year/day/$day
            """.trimIndent()
        )
    }

    private fun pushToClipboard(data: String) {
        Toolkit.getDefaultToolkit().systemClipboard.setContents(StringSelection(data), null)
    }


    @Test
    fun part1(): Unit = solve("part1.txt", null, ::part1)

    @Test
    fun part2(): Unit = solve("part2.txt", "part1.txt", ::part2)

    /**
     * [Lines] provider for JUnit [ParameterizedTest].
     * It reads the file named `{test_method_name}.txt` placed in the same directory as the test class.
     *
     * Usage:
     * ```kotlin
     * @ParameterizedTest
     * @ArgumentsSource(LinesSource::class)
     * fun part1(input: Lines) {}
     * ```
     */
    class LinesSource : ArgumentsProvider {
        private fun Class<*>.readInput(inputFileName: String) =
            this::class.java.getResource(inputFileName)?.toURI()?.let(::File)?.takeIf { it.length() > 0 }

        override fun provideArguments(context: ExtensionContext): Stream<out Arguments> {
            val clazz = context.testClass.get()
            val method = context.testMethod.get()
            val input = clazz.readInput("${method.name}.txt")
                ?: clazz.readInput("${method.name}.txt".replace("2", "1"))
                ?: error("${method.name}.txt not found")
            return Stream.of(Arguments.of(input.readLines()))
        }

    }
}