package Day06

import readStringInputData
import kotlin.math.abs

data class Coord(val x: Int, val y: Int) {
    fun distance(other: Coord) = abs(this.x - other.x) + abs(this.y - other.y)
}

fun main(args: Array<String>) {

    val fileName = "src/main/resources/inputDay06.txt"
    val inputCoords: List<Coord> = readStringInputData(fileName).map { parseCoords(it) }

    // Calc bounding box - Anything area that reaches the edge is infinite (maybe???)
    val minX = inputCoords.map{ it.x }.min()
    val minY = inputCoords.map{ it.y }.min()
    val maxX = inputCoords.map{ it.x }.max()
    val maxY = inputCoords.map{ it.y }.max()



}


fun parseCoords(str: String): Coord {
    // 252, 125
    val intList= str.split(", ").map(String::toInt)
    return Coord(intList[0], intList[1])
}

