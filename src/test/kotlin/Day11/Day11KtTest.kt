package Day11

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals

internal class Day11KtTest {

    data class CalcPowerInput(val x: Int, val y: Int, val serialNumber: Int)

    @TestFactory
    fun `calculate power`() =
        listOf(
            CalcPowerInput(3, 5, 8) to 4,
            CalcPowerInput(122, 79, 57) to -5,
            CalcPowerInput(217, 196, 39) to 0,
            CalcPowerInput(101, 153, 71) to 4
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("for $input got $expected") {
                assertEquals(expected, calcPower(input.x, input.y, input.serialNumber))
            }
        }

    @TestFactory
    fun `calculate best power squares`() =
        listOf(
            18 to PowerSquare(33, 45, 29, 3)
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("for $input got $expected") {
                assertEquals(expected, getBestPowerSquare(input, 3, 3))
            }
        }
}
