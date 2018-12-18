package Day01

import java.io.File

fun main(args: Array<String>) {

    var freq = 0

    File("src/main/resources/inputDay01.txt").forEachLine {
        freq += it.toIntOrNull() ?: 0
    }

    println("Final freq = $freq")

}