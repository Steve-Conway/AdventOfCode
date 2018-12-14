package Day06

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals

class Day06Tests {

    val exampleCoords = setOf(Coord(1, 1), Coord(1, 6), Coord(8, 3), Coord(3, 4), Coord(5, 5), Coord(8, 9))

    val a = Coord(1, 1)
    val b = Coord(1, 6)
    val c = Coord(8, 3)
    val d = Coord(3, 4)
    val e = Coord(5, 5)
    val f = Coord(8, 9)
    val x = null

    val exampleGrid =
        listOf(
            listOf(a, a, a, a, x, c, c, c),
            listOf(a, a, d, d, e, c, c, c),
            listOf(a, d, d, d, e, c, c, c),
            listOf(x, d, d, d, e, e, c, c),
            listOf(b, x, d, e, e, e, e, c),
            listOf(b, b, x, e, e, e, e, x),
            listOf(b, b, x, e, e, e, f, f),
            listOf(b, b, x, e, e, f, f, f),
            listOf(b, b, x, f, f, f, f, f)
        )

    val exampleEdgeCoords = setOf(a, b, c, f)
    val exampleContainedCoords = setOf(d, e)

    @TestFactory
    fun `Coord distance`() =
        listOf(
            Pair(Coord(0, 0), Coord(1, 0)) to 1,
            Pair(Coord(0, 0), Coord(0, 1)) to 1,
            Pair(Coord(0, 0), Coord(1, 1)) to 2,
            Pair(Coord(0, 0), Coord(-1, 0)) to 1,
            Pair(Coord(0, 0), Coord(0, -1)) to 1,
            Pair(Coord(0, 0), Coord(-1, -1)) to 2,
            Pair(Coord(0, 0), Coord(1, -1)) to 2,
            Pair(Coord(0, 0), Coord(-1, 1)) to 2,
            Pair(Coord(2, 3), Coord(8, 10)) to 13,
            Pair(Coord(2, 3), Coord(8, -10)) to 19,
            Pair(Coord(2, 3), Coord(-8, -10)) to 23,
            Pair(Coord(8, 10), Coord(2, 3)) to 13,
            Pair(Coord(8, -10), Coord(2, 3)) to 19,
            Pair(Coord(-8, -10), Coord(2, 3)) to 23
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("from ${input.first} to ${input.second} is $expected") {
                assertEquals(expected, input.first.distanceTo(input.second))
            }
        }

    @TestFactory
    fun `Nearest coord`() =
        listOf(
            Pair(Coord(0, 0), setOf(Coord(1, 0))) to Coord(1, 0),
            Pair(Coord(0, 0), setOf(Coord(6, 3), Coord(9, 1), Coord(1, 0), Coord(-3, 2))) to Coord(1, 0),
            Pair(Coord(0, 0), setOf(Coord(6, 3), Coord(3, 6))) to null,
            Pair(Coord(0, 0), setOf(Coord(6, 3), Coord(9, 1), Coord(1, 0), Coord(3, 6), Coord(-3, 2))) to Coord(1, 0)
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("to ${input.first} in ${input.second} is $expected") {
                assertEquals(expected, getNearestTargetCoord(input.first, input.second))
            }
        }

    @Test
    fun `Fill example grid`() {

        val grid = fillGrid(exampleCoords, 1, 1, 8, 9)

        assertEquals(exampleGrid, grid)
    }

    @Test
    fun `Get edge reaching coords from example grid`() {
        val edgeCoords = getEdgeReachingCoords(exampleGrid)

        assertEquals(exampleEdgeCoords, edgeCoords)
    }

    @Test
    fun `Get coord areas for example grid`() {
        val coordAreas = getCoordAreas(exampleGrid, exampleContainedCoords)

        assertEquals(mapOf(d to 9, e to 17), coordAreas)
    }

}