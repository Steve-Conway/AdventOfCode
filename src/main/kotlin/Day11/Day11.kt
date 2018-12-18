package Day11

private const val serialNumber = 4842

fun main(args: Array<String>) {

    val bestPowerSquare = getBestPowerSquare(serialNumber, 3, 300)

    println("Best power is ${bestPowerSquare.power}, at ${bestPowerSquare.x},${bestPowerSquare.y},${bestPowerSquare.size}")
}

data class PowerSquare(val x: Int, val y: Int, val power: Int, val size: Int)

fun getBestPowerSquare(serialNumber: Int, minSize :Int, maxSize: Int) : PowerSquare {
    val grid = mutableListOf<MutableList<Int>>()

    for (y in 1..300) {
        for (x in 1..300) {
            grid.add(mutableListOf())
            grid[y - 1].add(calcPower(x, y, serialNumber))
        }
    }

    var bestX = 0
    var bestY = 0
    var bestPower = -10000
    var bestSize = 0

    for (size in minSize..maxSize) {
        println("Size=$size")
        for (y in 1..(300 - size + 1)) {
            for (x in 1..(300 - size + 1)) {
                val power = calcSquarePower(grid, x, y, size)
                if (power > bestPower) {
                    bestPower = power
                    bestX = x
                    bestY = y
                    bestSize = size
                }
            }
        }
    }
    return PowerSquare(bestX, bestY, bestPower, bestSize)
}

fun calcPower(x: Int, y: Int, serialNumber: Int): Int {

    val rackID = x + 10
    val power = ((rackID * y) + serialNumber) * rackID
    return ((power % 1000) / 100) - 5
}

fun calcSquarePower(grid: List<List<Int>>, x: Int, y: Int, size: Int) : Int {

    var total = 0
    for (ix in x until x+size)
        for (iy in y until y+size)
            total += grid[iy-1][ix-1]
    return total
}