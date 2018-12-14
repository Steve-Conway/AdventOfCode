package Day01

import readIntInputData

fun main(args: Array<String>) {

    var freq = 0
    val seen = mutableSetOf(freq)

    val inputFileName = "src/Day01/inputDay01.txt"

    val inputData = readIntInputData(inputFileName)

    loop@ for (i in 1..1000) {  // Hard limit to how many cycles to go through
        for (change in inputData) {
            freq += change
            if (seen.contains(freq)) {
                println("Repeated freq = ${freq}")
                break@loop
            }
            seen.add(freq)
        }
    }

}

