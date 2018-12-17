package Day07

import readStringInputData
import kotlin.math.min

typealias Step = Char
typealias Dependencies = MutableMap<Step, MutableSet<Step>>
typealias Dependents = MutableMap<Step, MutableSet<Step>>

val inputRegEx = Regex("Step ([A-Z]) must be finished before step ([A-Z]) can begin.")


fun main(args: Array<String>) {

    val fileName = "src/main/resources/inputDay07.txt"
    val (dependencies, dependents) = parseDependents(readStringInputData(fileName))

    val order = getOrder(dependencies, dependents)

    println("Part 1: order = $order")

   val (dependencies2, dependents2) = parseDependents(readStringInputData(fileName))

    val elapsedTime = getTime(dependencies2, dependents2, 5, 60)

    println("Part 2: Elapsed time = $elapsedTime")

}

fun parseDependents(inputLines: List<String>): Pair<Dependencies, Dependents> {

    val dependencies = mutableMapOf<Step, MutableSet<Step>>()
    val dependents = mutableMapOf<Step, MutableSet<Step>>()
    val steps = mutableSetOf<Step>()

    inputLines.forEach {
        val (beforeStr, afterStr) = inputRegEx.matchEntire(it)?.destructured
            ?: throw IllegalStateException("Malformed input \"$it\"")

        val before = beforeStr.first()
        val after = afterStr.first()

        steps.add(before)
        steps.add(after)

        if (!dependencies.containsKey(after)) dependencies[after] = mutableSetOf()
        if (!dependents.containsKey(before)) dependents[before] = mutableSetOf()

        dependencies[after]!!.add(before)
        dependents[before]!!.add(after)
    }

    steps.forEach {
        if (!dependencies.containsKey(it)) dependencies[it] = mutableSetOf()
        if (!dependents.containsKey(it)) dependents[it] = mutableSetOf()
    }

    return Pair(dependencies, dependents)
}


fun getOrder(dependencies: Dependencies, dependents: Dependents): String {

    val order = mutableListOf<Step>()
    var next: Step?

    do {
        next = getNext(dependencies)
        order.add(next)
        updateStatus(next, dependencies, dependents)
    } while (dependencies.isNotEmpty())

    return order.joinToString(separator = "")
}

fun getNext(dependencies: Dependencies): Step {

    // Next step has no dependencies, and if multiple, use the first alphabetically

    return dependencies
        .filter { (_, v) -> v.isEmpty() }
        .minBy { (k, _) -> k }
        ?.key
        ?: throw IllegalStateException("No next dependency")
}

fun updateStatus(next: Step, dependencies: Dependencies, dependents: Dependents) {

    // Remove the next executed step from each dependency list, as it has been satisfied
    dependents[next]?.forEach {
        dependencies[it]?.remove(next)
    }
    // Remove the executed step from dependents and dependencies
    dependents.remove(next)
    dependencies.remove(next)

}

fun getTime(dependencies: Dependencies, dependents: Dependents, numWorkers: Int, baseTime: Int): Int {

    var next: List<Step>
    var numAvailWorkers = numWorkers
    var elapsedTime = 0
    val inProgress = mutableMapOf<Step, Int>()


    do {
        val completed = mutableListOf<Step>()
        inProgress.keys.forEach {
            inProgress[it] = inProgress[it]!!.minus(1)
            if (inProgress[it] == 0) {
                completed.add(it)
            }
        }

        completed.forEach {
            updateStatus(it, dependencies, dependents)
            inProgress.remove(it)
            numAvailWorkers++
        }

        if (numAvailWorkers > 0) {
            next = getNext(dependencies, numAvailWorkers, inProgress.keys)
            numAvailWorkers -= next.size
            next.forEach {
                inProgress[it] = baseTime + it.minus('A') + 1
            }
        }

        // Tick
        if (dependencies.isEmpty()) {
            break
        }

        elapsedTime += 1

    } while (elapsedTime < 10000)

    return elapsedTime
}


fun getNext(dependencies: Dependencies, availWorkers: Int, inProgress: Set<Step>): List<Step> {

    // Next step has no dependencies, and if multiple, up to availWorkers, ordered alphabetically

    val availNext = dependencies
        .filter { (_, v) -> v.isEmpty() }
        .filter { (k, _) -> !inProgress.contains(k) }
        .keys
        .sorted()

    return availNext.subList(0, min(availNext.size, availWorkers))
}

