package Day09

fun main(args: Array<String>) {
    val score = calculate(455, 71223)
    println("Score = $score")

    val biggerScore = calculate(455, 7122300)
    println("Big score = $biggerScore")
}

fun calculate(numPlayers: Int, lastMarble: Int): Long {

    val scores = mutableMapOf<Int, Long>() // player to score
    var currentMarble = Marble(0)
    var currentPlayer = 0

    for (marbleValue in 1..lastMarble) {
        if (marbleValue % 23 == 0) {
            scores[currentPlayer] = scores.getOrDefault(currentPlayer, 0) + marbleValue
            currentMarble = currentMarble.previous.previous.previous.previous.previous.previous.previous
            scores[currentPlayer] = scores.getOrDefault(currentPlayer, 0) + currentMarble.value
            currentMarble = currentMarble.remove()
        } else {
            currentMarble = currentMarble.next.insertAfter(marbleValue)
        }
        currentPlayer++
        if (currentPlayer >= numPlayers) {
            currentPlayer = 0
        }
    }

    return scores.maxBy { (_, v) -> v }!!.value
}

class Marble(val value: Int) {

    var next: Marble = this
    var previous: Marble = this

    constructor(value: Int, next: Marble, previous: Marble): this(value) {
        this.next = next
        this.previous = previous
    }

    fun insertAfter(value: Int): Marble {
        val newStone = Marble(value, next, this)
        next.previous = newStone
        next = newStone
        return newStone
    }

    fun remove(): Marble {
        previous.next = next
        next.previous = previous
        return next
    }
}
