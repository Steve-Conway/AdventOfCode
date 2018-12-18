package Day10

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day10Tests {

    private val examplePointsInput = listOf(
        "position=< 9,  1> velocity=< 0,  2>",
        "position=< 7,  0> velocity=<-1,  0>",
        "position=< 3, -2> velocity=<-1,  1>",
        "position=< 6, 10> velocity=<-2, -1>",
        "position=< 2, -4> velocity=< 2,  2>",
        "position=<-6, 10> velocity=< 2, -2>",
        "position=< 1,  8> velocity=< 1, -1>",
        "position=< 1,  7> velocity=< 1,  0>",
        "position=<-3, 11> velocity=< 1, -2>",
        "position=< 7,  6> velocity=<-1, -1>",
        "position=<-2,  3> velocity=< 1,  0>",
        "position=<-4,  3> velocity=< 2,  0>",
        "position=<10, -3> velocity=<-1,  1>",
        "position=< 5, 11> velocity=< 1, -2>",
        "position=< 4,  7> velocity=< 0, -1>",
        "position=< 8, -2> velocity=< 0,  1>",
        "position=<15,  0> velocity=<-2,  0>",
        "position=< 1,  6> velocity=< 1,  0>",
        "position=< 8,  9> velocity=< 0, -1>",
        "position=< 3,  3> velocity=<-1,  1>",
        "position=< 0,  5> velocity=< 0, -1>",
        "position=<-2,  2> velocity=< 2,  0>",
        "position=< 5, -2> velocity=< 1,  2>",
        "position=< 1,  4> velocity=< 2,  1>",
        "position=<-2,  7> velocity=< 2, -2>",
        "position=< 3,  6> velocity=<-1, -1>",
        "position=< 5,  0> velocity=< 1,  0>",
        "position=<-6,  0> velocity=< 2,  0>",
        "position=< 5,  9> velocity=< 1, -2>",
        "position=<14,  7> velocity=<-2,  0>",
        "position=<-3,  6> velocity=< 2, -1>"
    )

    @TestFactory
    fun `Parse points`() =
        listOf(
            "position=< 9,  1> velocity=< 3,  2>" to Point(9, 1, 3, 2),
            "position=<-9,  1> velocity=<-3,  2>" to Point(-9, 1, -3, 2),
            "position=< 9, -1> velocity=< 3, -2>" to Point(9, -1, 3, -2),
            "position=<-9, -1> velocity=<-3, -2>" to Point(-9, -1, -3, -2)
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("for $input got $expected") {
                assertEquals(expected, parsePoint(input))
            }
        }

    @TestFactory
    fun `Count neighbours`() =
        listOf(
            Pair(
                Point(0, 0, 4, 5),
                listOf(Point(-1, 0, 4, 5), Point(0, -1, 4, 5))
            )
                    to 0,
            Pair(
                Point(0, 0, 4, 5),
                listOf(Point(-1, -1, 4, 5), Point(1, 1, 4, 5))
            )
                    to 0,
            Pair(
                Point(0, 0, 4, 5),
                listOf(Point(1, 0, 4, 5), Point(0, -1, 4, 5))
            )
                    to 1,
            Pair(
                Point(0, 0, 4, 5),
                listOf(Point(-1, 0, 4, 5), Point(0, 1, 4, 5))
            )
                    to 1,
            Pair(
                Point(0, 0, 4, 5),
                listOf(Point(1, 0, 4, 5), Point(0, 1, 4, 5))
            )
                    to 2
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("for point ${input.first} and ${input.second} got $expected") {
                assertEquals(expected, countNeighbours(input.first, input.second))
            }
        }

    @Test
    fun `Example data`() {

        val points = examplePointsInput.map{parsePoint(it)}
        val(time, bestPoints) = getClosestPattern(points, 5, 20)

        assertEquals(3, time)
        printPoints(bestPoints)
    }
}