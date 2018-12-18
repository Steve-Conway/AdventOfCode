package Day10

import readStringInputData

val inputRegEx = Regex("position=< *([-,0-9]+), *([-,0-9]+)> velocity=< *([-,0-9]+), *([-,0-9]+)>")

fun main(args: Array<String>) {

    val fileName = "src/main/resources/inputDay10.txt"
    val points = readStringInputData(fileName).map{parsePoint(it)}
    val(time, bestPoints) = getClosestPattern(points, 20000, 300)
    println("Found best fit after $time seconds")
    printPoints(bestPoints)
}

fun getClosestPattern(points: List<Point>, timeLimit: Int, closenessThreshold: Int): Pair<Int, List<Point>> {

    var currentPoints = points
    var time = 0
    var currentCloseness = closeness(currentPoints)

    while (time <= timeLimit) {

        val newPoints = currentPoints.map{it.tick()}
        val newCloseness = closeness(newPoints)
        if (newCloseness < currentCloseness && currentCloseness > closenessThreshold) {
            println("Closeness=$currentCloseness")
            return Pair(time, currentPoints)
        }
        currentPoints = newPoints
        currentCloseness = newCloseness
        time++
    }
    throw IllegalStateException("Out of time")
}

fun printPoints(points: List<Point>) {
    val minX = points.map { it.x }.min() ?: throw IllegalStateException("No points")
    val maxX = points.map { it.x }.max() ?: throw IllegalStateException("No points")
    val minY = points.map { it.y }.min() ?: throw IllegalStateException("No points")
    val maxY = points.map { it.y }.max() ?: throw IllegalStateException("No points")

    println("minX=$minX, maxX=$maxX, minY=$minY, maxY=$maxY")

    for (y in minY..maxY) {
        for (x in minX..maxX) {
            if (points.filter {it.posEquals(Point(x, y, 0, 0))}.isEmpty()) {
                print(' ')
            } else {
                print('#')
            }
        }
        println()
    }
}

// A measure of how close points are. Just counting how many neighbours each has to right and down
fun closeness(points: List<Point>) : Int =
    points.sumBy { point -> countNeighbours(point, points) }

fun countNeighbours(point: Point, points: List<Point>) : Int {
    val right = Point(point.x + 1, point.y, 0, 0)
    val down = Point(point.x, point.y + 1, 0, 0)

    return points.filter{ it.posEquals(right) || it.posEquals(down)}.sumBy { 1 }
}

fun parsePoint(input: String) : Point {
    val(xStr, yStr, dXStr, dYStr) = inputRegEx.matchEntire(input)?.destructured
        ?: throw IllegalStateException("Malformed input \"$input\"")
    return Point(xStr.toInt(), yStr.toInt(), dXStr.toInt(), dYStr.toInt())
}

data class Point(val x: Int, val y: Int, val dX: Int, val dY: Int) {
    fun tick(): Point = Point(x + dX, y + dY, dX, dY)
    fun posEquals(other: Point) = this.x == other.x && this.y == other.y
}