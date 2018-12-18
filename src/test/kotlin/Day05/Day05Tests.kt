package Day05

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals


class Day05Tests {

    private val exampleInput = "dabAcCaCBAcCcaDA"

    @TestFactory
    fun `Running reactable`() = listOf(
        Pair('a', 'A') to true,
        Pair('A', 'a') to true,
        Pair('a', 'a') to false,
        Pair('A', 'A') to false,
        Pair('a', 'b') to false,
        Pair('a', 'B') to false,
        Pair('A', 'B') to false
    ).map { (input, expected) ->
        DynamicTest.dynamicTest("when I compare '${input.first}' to '${input.second}' then I get $expected") {
            assertEquals(expected, reactable(input.first, input.second))
        }
    }

    @TestFactory
    fun `Running react`() = listOf(
        "aA" to "",
        "abBA" to "",
        "abAB" to "abAB",
        "aabAAB" to "aabAAB",
        exampleInput to "dabCBAcaDA",
        "xXwIiSsjJWnQqNk" to "k"
    ).map { (input, expected) ->
        DynamicTest.dynamicTest("when I react \"$input\" then I get \"$expected\"") {
            assertEquals(expected, react(input))
        }
    }
}
