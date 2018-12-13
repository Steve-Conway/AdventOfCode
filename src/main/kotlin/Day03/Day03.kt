package Day03

import readStringInputData

data class Claim(val id: Int, val leftMargin: Int, val topMargin: Int, val width:Int, val height: Int)
data class Coord(val x: Int, val y: Int)

typealias ClaimedCoords = HashMap<Coord, Int>

val claimRegEx = Regex("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)")
val claimedCoords = ClaimedCoords()

fun parseClaim(claimStr: String) : Claim? {

    val matchResult = claimRegEx.matchEntire(claimStr)
    if (matchResult != null) {
        val (id, leftMargin, topMargin, width, height) = matchResult.destructured
        return Claim(id.toInt(), leftMargin.toInt(), topMargin.toInt(), width.toInt(), height.toInt())
    }
    return null
}

fun claimCoords(claim: Claim) {

    for (x: Int in claim.leftMargin until claim.leftMargin + claim.width) {
        for (y: Int in claim.topMargin until claim.topMargin + claim.height) {
            val claimCoord = Coord(x, y)
            val claimedCnt = claimedCoords.get(claimCoord)
            if (claimedCnt == null) {
                claimedCoords[claimCoord] = 1
            } else {
                claimedCoords[claimCoord] = claimedCnt + 1
            }
        }
    }
}

fun countMultipleClaims() : Int =
    // Could accumulate in claimCoords
    claimedCoords.count { (_, v) -> v > 1}

fun claimIsNonOverlapping(claim: Claim) : Boolean {

    for (x: Int in claim.leftMargin until claim.leftMargin + claim.width) {
        for (y: Int in claim.topMargin until claim.topMargin + claim.height) {
            val claimCoord = Coord(x, y)
            val claimedCnt = claimedCoords.get(claimCoord)!!
            if (claimedCnt != 1) {
                return false
            }
        }
    }
    return true
}


fun main(args: Array<String>) {

    val fileName = "src/main/resources/inputDay03.txt"
    val inputData = readStringInputData(fileName)

    inputData.forEach {
        val claim = parseClaim(it)
        if (claim != null) {
            claimCoords(claim)
        }
    }

    println("multiple claim count = ${countMultipleClaims()}")

    inputData.forEach {
        val claim = parseClaim(it)
        if (claim != null && claimIsNonOverlapping(claim)) {
            println("Nonoverlapping claim found, id = ${claim.id}")
        }

    }

}

