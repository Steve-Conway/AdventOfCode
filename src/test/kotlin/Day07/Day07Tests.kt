package Day07

import assertk.assertAll
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day07Tests {

    private val exampleInput = listOf(
        "Step C must be finished before step A can begin.",
        "Step C must be finished before step F can begin.",
        "Step A must be finished before step B can begin.",
        "Step A must be finished before step D can begin.",
        "Step B must be finished before step E can begin.",
        "Step D must be finished before step E can begin.",
        "Step F must be finished before step E can begin."
    )


    @Test
    fun `Parse dependents for example input`() {

        val (dependencies, dependents) = parseDependents(exampleInput)

        assertAll {
            assertEquals(6, dependencies.size)
            assertEquals(mutableSetOf('C'), dependencies['A'])
            assertEquals(mutableSetOf('A'), dependencies['B'])
            assertEquals(mutableSetOf(), dependencies['C'])
            assertEquals(mutableSetOf('A'), dependencies['D'])
            assertEquals(mutableSetOf('B', 'D', 'F'), dependencies['E'])
            assertEquals(mutableSetOf('C'), dependencies['F'])
        }

        assertAll {
            assertEquals(6, dependents.size)
            assertEquals(mutableSetOf('B', 'D'), dependents['A'])
            assertEquals(mutableSetOf('E'), dependents['B'])
            assertEquals(mutableSetOf('A', 'F'), dependents['C'])
            assertEquals(mutableSetOf('E'), dependents['D'])
            assertEquals(mutableSetOf(), dependents['E'])
            assertEquals(mutableSetOf('E'), dependents['F'])
        }
    }

    @Test
    fun `Get order for example input`() {

        val (dependencies, dependents) = parseDependents(exampleInput)

        val order = getOrder(dependencies, dependents)

        assertEquals("CABDFE", order)
    }

    @Test
    fun `Get elapsed time for example input`() {

        val (dependencies, dependents) = parseDependents(exampleInput)

        val elapsedTime = getTime(dependencies, dependents, 2, 0)

        assertEquals(15, elapsedTime)
    }
}