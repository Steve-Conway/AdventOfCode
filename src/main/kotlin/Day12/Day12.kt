package Day12

import readStringInputData

const val fileName = "src/main/resources/inputDay12.txt"

fun main(args: Array<String>) {

    val initialData = parseInputData(readStringInputData(fileName))

    var data = initialData
    for (i in 1..20) {
        data = tick(data)
    }
//    println("Part 1 Score = ${score(data)}")

    val seen = mutableSetOf<Data>()

    data = initialData
    for (i in 1..164) {
        data = tick(data)
//        println("${data.state}")
        val repeat = seen.firstOrNull{ it.state == data.state }
        if (repeat != null) {
            println("${data.generation.toString().padStart(2)}: ${data.state}")
            println("offset=${data.zeroOffset} score=${score(data)}")
        } else {
            seen.add(data)
        }
    }




}

enum class Pot(val repr: String) {
    PLANT("#"),
    NO_PLANT(".");

    override fun toString(): String = repr
}

data class State(val pots: List<Pot>) {

    val size = pots.size

    override fun toString() : String  =
        pots.fold("") { acc, pot -> acc + pot.repr }
}

data class Rule(val mask: List<Pot>, val output: Pot) {
    override fun toString(): String  =
        mask.fold("") { acc, pot -> acc + pot.repr } + " => " + output.repr
}

data class Data(val state: State, val rules: List<Rule>, val zeroOffset: Int, val generation: Int)

fun tick(data: Data) : Data {
    val newState = mutableListOf<Pot>()
    for (i in 0..data.state.size - 5) {
        newState.add(matchRule(data.state.pots.subList(i, i + 5), data.rules))
    }

    val firstPlantIdx = newState.subList(0, 3).indexOf(Pot.PLANT)
    if (firstPlantIdx >= 0) {
        repeat(3 - firstPlantIdx) {
            newState.add(0, Pot.NO_PLANT)
        }
    }

    val lastPlantIdx = newState.subList(newState.size - 4, newState.size).lastIndexOf(Pot.PLANT)
    if (lastPlantIdx >= 0) {
        repeat(lastPlantIdx) {
            newState.add(Pot.NO_PLANT)
        }
    }

    return Data(State(newState), data.rules, data.zeroOffset -1 + firstPlantIdx, data.generation+1)
}

fun score(data: Data) : Int {

    var score = 0

    for (i in 0 until data.state.size) {
        if (data.state.pots[i] == Pot.PLANT) {
            score += i + data.zeroOffset
        }
    }

    return score
}

fun matchRule(pots: List<Pot>, rules: List<Rule>): Pot {
    val matched= rules.firstOrNull { rule ->
        rule.mask[0] == pots[0]
                && rule.mask[1] == pots[1]
                && rule.mask[2] == pots[2]
                && rule.mask[3] == pots[3]
                && rule.mask[4] == pots[4]
    }
    val result = matched?.output ?: Pot.NO_PLANT

//    println("pots: $pots, matched rule = $matched, result is $result")

    return result
}


fun parseInputData(inputData: List<String>) : Data {

    val firstLinePrefix = "initial state: "

    if (!inputData[0].startsWith(firstLinePrefix)) throw IllegalStateException("Invalid first line: ${inputData[0]}")

    val initialState = listOf(
        (1..3).map { Pot.NO_PLANT },
        inputData[0]
            .substring(firstLinePrefix.length)
            .toCharArray()
            .map { parsePot(it) },
        (1..3).map { Pot.NO_PLANT }
    ).flatten()

    val rules = inputData.subList(1, inputData.size).map { parseRule(it) }

    return Data(State(initialState), rules, -3, 0)
}

fun parsePot(potChar: Char) : Pot =
    when (potChar) {
        Pot.PLANT.repr.first() -> Pot.PLANT
        Pot.NO_PLANT.repr.first() -> Pot.NO_PLANT
        else -> throw IllegalStateException("Invalid pot character: '$potChar'")
    }

fun parseRule(ruleStr: String): Rule {

    val (maskStr, outputStr) = ruleStr.split(" => ")

    val mask = maskStr.toCharArray().map { parsePot(it) }
    val output = parsePot(outputStr.first())

    return Rule(mask, output)
}
