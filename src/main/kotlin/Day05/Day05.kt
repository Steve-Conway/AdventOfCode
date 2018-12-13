package Day05

import readStringInputData

fun main(args: Array<String>) {
    val fileName = "src/main/resources/inputDay05.txt"
    val inputData = readStringInputData(fileName).joinToString(separator = "")

    println("Initial React length = ${inputData.length}")

    val reacted = react(inputData)
    println("Reacted length = ${reacted.length}")

    var bestLength = inputData.length
    var bestChar = '_'

    for (char in 'a'..'z') {
        val newTestData = inputData.filter { it != char && it != char.toUpperCase() }
        val newReacted = react(newTestData)
        if (newReacted.length < bestLength) {
            bestLength = newReacted.length
            bestChar = char
        }
        println("For '$char' reacted length = ${newReacted.length}")
    }
    println("Best char was '$bestChar' with length $bestLength")
}

fun react(polymer: String): String {

    var reactingPolymer = polymer.toCharArray().toMutableList()
    var reacted = false

    do {
        var i = 0
        reacted = false
        while (i < reactingPolymer.size - 1) {

            when {
                i < 0 -> println("Negative i, polymer size is ${reactingPolymer.size}")
                i > reactingPolymer.size - 1 -> println("i = $i but polymer size is ${reactingPolymer.size}")
            }

            if (i < 0 || i > (reactingPolymer.size - 1)) {
                println("")
            } else if (i < 0 || i > (reactingPolymer.size - 1)) {
                println("")
            }

            if (reactable(reactingPolymer[i], reactingPolymer[i+1])) {
                reactingPolymer.removeAt(i+1)
                reactingPolymer.removeAt(i)
                reacted = true
            } else {
                i++
            }
        }

    } while (reacted)

    return String(reactingPolymer.toCharArray())
}

fun reactable(unit1: Char, unit2: Char): Boolean =
    unit1 != unit2 && unit1.toLowerCase() == unit2.toLowerCase()
