package Day08

import readStringInputData


data class Node (val numChildNodes: Int, val numMetaData: Int, val childNodes: List<Node>, val metadata: List<Int>, val totalLength : Int)

fun main(args: Array<String>) {

    val fileName = "src/main/resources/inputDay08.txt"
    val input = readStringInputData(fileName)[0].split(" ").map{ it.toInt()}
    val root: Node = buildNodeTree(input)

    var metadataTotal = 0

    visitNodeTree(root) {
        metadataTotal += it.metadata.sum()
    }

    println("Part 1: Metadata total is $metadataTotal")

}

fun buildNodeTree(input: List<Int>, pos: Int = 0): Node {

    val numChildNodes = input[pos]
    val numMetaData = input[pos+1]
    val childNodes = mutableListOf<Node>()

    var nodeStart = pos + 2
    var totalLength = 2 + numMetaData
    for (i in 1..numChildNodes) {
        val childNode = (buildNodeTree(input, nodeStart))
        childNodes.add(childNode)
        nodeStart += childNode.totalLength
        totalLength += childNode.totalLength
    }
    val metadata = input.subList(nodeStart, nodeStart + numMetaData)

    return Node(numChildNodes, numMetaData, childNodes, metadata, totalLength)
}

fun visitNodeTree(node: Node, visitor: (node: Node) -> Unit) {

    visitor(node)
    for (childNode in node.childNodes) {
        visitNodeTree(childNode, visitor)
    }
}
