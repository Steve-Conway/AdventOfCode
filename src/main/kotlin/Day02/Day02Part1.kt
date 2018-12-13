package Day02

import readStringInputData

fun main(args: Array<String>) {

    var twiceTotal = 0
    var thriceTotal = 0

    val fileName = "src/main/resources/inputDay02.txt"

    readStringInputData(fileName).forEach {
        val grouped = it.groupBy { s -> s }

        if (grouped.any { (_,v) -> v.size == 2 }) twiceTotal++
        if (grouped.any { (_,v) -> v.size == 3 }) thriceTotal++
    }
    println("Checksum = ${twiceTotal * thriceTotal}")
}

