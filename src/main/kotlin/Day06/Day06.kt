package Day06

import readStringInputData
import kotlin.math.abs

data class Coord(val x: Int, val y: Int) {
    fun distanceTo(other: Coord) = abs(this.x - other.x) + abs(this.y - other.y)
}

fun main(args: Array<String>) {

    val fileName = "src/main/resources/inputDay06.txt"
    val targetCoords: Set<Coord> = readStringInputData(fileName).map { parseCoords(it) }.toSet()

    // Calc bounding box - Anything area that reaches the edge is infinite (maybe???)
    val minX = targetCoords.map { it.x }.min() ?: throw IllegalStateException("No target coords")
    val minY = targetCoords.map { it.y }.min() ?: throw IllegalStateException("No target coords")
    val maxX = targetCoords.map { it.x }.max() ?: throw IllegalStateException("No target coords")
    val maxY = targetCoords.map { it.y }.max() ?: throw IllegalStateException("No target coords")

    val grid = fillGrid(targetCoords, minX, minY, maxX, maxY)
    val containedCoords = targetCoords.minus(getEdgeReachingCoords(grid))
    val coordAreas = getCoordAreas(grid, containedCoords)
    val biggestArea = coordAreas.values.max()

    println("Biggest area is $biggestArea")

    println("Begin Part 2")

    val safeRegionSize = getRegionSizeWithinDistance(targetCoords, minX, minY, maxX, maxY, 10000)

    println("Safe regions size is $safeRegionSize")

}

fun parseCoords(str: String): Coord {
    // 252, 125
    val intList = str.split(", ").map(String::toInt)
    return Coord(intList[0], intList[1])
}

/**
 * Null response means more than one nearest target Coord
 */
fun getNearestTargetCoord(coord: Coord, targetCoords: Set<Coord>): Coord? {

    var minDistance = 999999
    var nearestTargetCoord: Coord? = null

    for (targetCoord in targetCoords) {
        val distance = targetCoord.distanceTo(coord)
        when {
            distance == minDistance -> nearestTargetCoord = null // Two coords with equal nearest distance
            distance < minDistance -> {
                nearestTargetCoord = targetCoord
                minDistance = distance
            }
        }
    }
    return nearestTargetCoord
}


fun fillGrid(targetCoords: Set<Coord>, minX: Int, minY: Int, maxX: Int, maxY: Int): List<List<Coord?>> {

    val grid = mutableListOf<MutableList<Coord?>>()

    for (y in minY..maxY) {
        grid.add(mutableListOf())
        val currentRow = grid.last()
        for (x in minX..maxX) {
            currentRow.add(getNearestTargetCoord(Coord(x, y), targetCoords))
        }
    }
    return grid
}

fun getEdgeReachingCoords(grid: List<List<Coord?>>): Set<Coord> {

    fun addCoord(acc: MutableSet<Coord>, coord: Coord?) {
        if (coord != null) {
            acc.add(coord)
        }
    }

    val edgeCoords = mutableSetOf<Coord>()
    for (x in 0 until grid[0].size) addCoord(edgeCoords, grid[0][x]) // Top edge
    for (x in 0 until grid[0].size) addCoord(edgeCoords, grid[grid.size-1][x]) // Bottom edge
    for (y in 0 until grid.size) addCoord(edgeCoords, grid[y][0]) // Left edge
    for (y in 0 until grid.size) addCoord(edgeCoords, grid[y][grid[0].size-1]) // Right edge

    return edgeCoords
}


fun getCoordAreas(grid: List<List<Coord?>>, coords: Set<Coord>): Map<Coord, Int> {

    return grid.flatten().filterNotNull().filter{ coords.contains(it)}.groupingBy { it }.eachCount()
}

fun getRegionSizeWithinDistance(targetCoords: Set<Coord>, minX: Int, minY: Int, maxX: Int, maxY: Int, threshold: Int) : Int {


    // Assume that we only need to search the area defined by extending the
    // boundary by threshold/number of coords.

    val margin = threshold / targetCoords.size

    var total = 0

    for (x in (minX - margin) .. (maxX + margin)) {
        for (y in (minY - margin) .. (maxY + margin)) {
            if (getTotalDistance(targetCoords, Coord(x, y)) < threshold) {
                total++
            }
        }
    }

    return total
}

fun getTotalDistance(targetCoords: Set<Coord>, coord: Coord): Int {
    return targetCoords.sumBy { it.distanceTo(coord) }
}


