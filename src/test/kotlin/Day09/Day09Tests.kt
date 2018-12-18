package Day09

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals

class Day09Tests {

    @TestFactory
    fun `Check results`() =
            listOf(
                    Pair(9, 25) to 32L,
                    Pair(10, 1618) to 8317L,
                    Pair(13, 7999) to 146373L,
                    Pair(17, 1104) to 2764L,
                    Pair(21, 6111) to 54718L,
                    Pair(30, 5807) to 37305L

            ).map { (input, expected) ->
                DynamicTest.dynamicTest("for ${input.first} players and ${input.second} marbles is $expected") {
                    assertEquals(expected, calculate(input.first, input.second))
                }
            }

}
