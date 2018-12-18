package Day02

import readStringInputData

fun main(args: Array<String>) {

    val fileName = "src/main/resources/inputDay02.txt"
    val inputData = readStringInputData(fileName)

    fun commonLetters(first: String, second: String): String? {
        val chars: MutableList<Char> = mutableListOf()
        var mismatched = false
        for (i: Int in 0 until first.length) {
            if (first[i] == second[i]) {
                chars.add(first[i])
            } else {
                if (mismatched) {
                    return null
                } else {
                    mismatched = true
                }
            }
        }
        return String(chars.toCharArray())
    }

    outer@ for (i: Int in 0 until inputData.size - 1) {
        for (j : Int in i+1 until inputData.size) {
            val common: String? = commonLetters(inputData[i], inputData[j])
            if (common != null) {
                println("Common letters are \"$common\" for \"${inputData[i]}\" and \"${inputData[j]}\"")
                break@outer
            }
        }
    }

}

