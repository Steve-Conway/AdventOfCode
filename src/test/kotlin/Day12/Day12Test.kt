package Day12

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day12Test {

    val exampleData = listOf(
        "initial state: #..#.#..##......###...###",
        "...## => #",
        "..#.. => #",
        ".#... => #",
        ".#.#. => #",
        ".#.## => #",
        ".##.. => #",
        ".#### => #",
        "#.#.# => #",
        "#.### => #",
        "##.#. => #",
        "##.## => #",
        "###.. => #",
        "###.# => #",
        "####. => #"
    )

    @Test
    fun `20 iterations of the example data`() {
        var data = parseInputData(exampleData)
        println(" 0: offset=${data.zeroOffset} ${data.state}")
        for (i in 1..20) {
           data = tick(data)
            println("${data.generation.toString().padStart(2)}: offset=${data.zeroOffset} ${data.state}")
        }

        assertEquals(325, score(data))
    }

}