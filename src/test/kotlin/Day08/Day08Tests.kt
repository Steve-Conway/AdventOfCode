package Day08

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day08Tests {

    val exampleInput = listOf(2, 3, 0, 3, 10, 11, 12, 1, 1, 0, 1, 99, 2, 1, 1, 2)

    @Test
    fun `Build node tree for example input`() {

        val nodeA = buildNodeTree(exampleInput)

        assertEquals(16, nodeA.totalLength)
        assertEquals(2, nodeA.numChildNodes)
        assertEquals(3, nodeA.numMetaData)
        assertEquals(listOf(1, 1, 2), nodeA.metadata)

        val (nodeB, nodeC) = nodeA.childNodes

        assertEquals(5, nodeB.totalLength)
        assertEquals(0, nodeB.numChildNodes)
        assertEquals(3, nodeB.numMetaData)
        assertEquals(listOf(10, 11, 12), nodeB.metadata)

        assertEquals(6, nodeC.totalLength)
        assertEquals(1, nodeC.numChildNodes)
        assertEquals(1, nodeC.numMetaData)
        assertEquals(listOf(2), nodeC.metadata)

        val nodeD = nodeC.childNodes[0]

        assertEquals(3, nodeD.totalLength)
        assertEquals(0, nodeD.numChildNodes)
        assertEquals(1, nodeD.numMetaData)
        assertEquals(listOf(99), nodeD.metadata)
    }

    @Test
    fun `Visit node tree`() {

        val root = buildNodeTree(exampleInput)

        val metaData = mutableListOf<Int>()

        visitNodeTree(root){
            metaData.addAll(it.metadata)
        }

        assertEquals(listOf(1, 1, 2, 10, 11, 12, 2, 99), metaData)
    }


    @Test
    fun `Accumulate metadata`() {

        val root = buildNodeTree(exampleInput)

        var metadataTotal = 0

        visitNodeTree(root){
            metadataTotal += it.metadata.sum()
        }

        assertEquals(138, metadataTotal)
    }

}

