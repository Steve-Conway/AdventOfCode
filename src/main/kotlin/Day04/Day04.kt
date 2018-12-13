package Day04

import readStringInputData
import kotlin.IllegalStateException

interface Event

val wakesUpEvent = object : Event {
    override fun toString(): String {
        return "wakes up"
    }
}

val fallsAsleepEvent = object : Event {
    override fun toString(): String {
        return "falls asleep"
    }
}

class BeginShiftEvent(val guardId: Int) : Event {
    override fun toString(): String {
        return "Guard #$guardId begins shift"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BeginShiftEvent

        if (guardId != other.guardId) return false

        return true
    }

    override fun hashCode(): Int {
        return guardId
    }
}

val eventRegEx = Regex("\\[(\\d\\d\\d\\d)-(\\d\\d)-(\\d\\d) (\\d\\d):(\\d\\d)] (.*)")
val guardRegEx = Regex("Guard #(\\d+) begins shift")
const val WAKES_UP = "wakes up"
const val FALLS_ASLEEP = "falls asleep"

class DateStamp(val year: Int, val month: Int, val day: Int, val hour: Int, val minute: Int) : Comparable<DateStamp> {
    override fun compareTo(other: DateStamp): Int = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        day != other.day -> day - other.day
        hour != other.hour -> hour - other.hour
        else -> minute - other.minute
    }

    override fun toString(): String {
        return "DateStamp(year=$year, month=$month, day=$day, hour=$hour, minute=$minute)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DateStamp

        if (year != other.year) return false
        if (month != other.month) return false
        if (day != other.day) return false
        if (hour != other.hour) return false
        if (minute != other.minute) return false

        return true
    }

    override fun hashCode(): Int {
        var result = year
        result = 31 * result + month
        result = 31 * result + day
        result = 31 * result + hour
        result = 31 * result + minute
        return result
    }
}

class Entry(val dateStamp: DateStamp, val event: Event) : Comparable<Entry> {
    override fun compareTo(other: Entry) = dateStamp.compareTo(other.dateStamp)
    override fun toString(): String {
        return "Entry($dateStamp, $event)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Entry

        if (dateStamp != other.dateStamp) return false
        if (event != other.event) return false

        return true
    }

    override fun hashCode(): Int {
        var result = dateStamp.hashCode()
        result = 31 * result + event.hashCode()
        return result
    }
}

fun main(args: Array<String>) {
    val fileName = "src/main/resources/inputDay04.txt"
    val inputData = readStringInputData(fileName)

    val entries = inputData.map {
        parseEntry(it)
    }.sorted()

    val sleepTotals = getSleepTotals(entries)

    val sleepiestGuard = getSleepiestGuard(sleepTotals)

    println("Sleepiest guard is $sleepiestGuard")

    val sleepiestMinute = getSleepiestMinute(sleepTotals, sleepiestGuard)

    println("Sleepiest minute is $sleepiestMinute")
    println("Magic number is ${sleepiestGuard * sleepiestMinute}")

    val (sleepiestGuard2, sleepiestMinute2) = getSleepiestMinuteStrategy2(sleepTotals)

    println("Strategy2: Sleepiest guard is $sleepiestGuard2, sleepiest minute is $sleepiestMinute2, magic number is ${sleepiestGuard2 * sleepiestMinute2}")
}

fun parseEntry(entry: String): Entry {
    val eventMatchResult = eventRegEx.matchEntire(entry)
    if (eventMatchResult != null) {
        val(year, month, day, hour, min, eventMsg) = eventMatchResult.destructured
        val event = when (eventMsg) {
            WAKES_UP -> wakesUpEvent
            FALLS_ASLEEP -> fallsAsleepEvent
            else -> parseBeginShiftEvent(eventMsg)
        }
        return Entry(DateStamp(year.toInt(), month.toInt(), day.toInt(), hour.toInt(), min.toInt()), event)
    }
    throw IllegalStateException("Invalid entry $entry")
}

fun parseBeginShiftEvent(eventMsg: String): Event {
    val (guardId)  = guardRegEx.matchEntire(eventMsg)?.destructured ?: throw IllegalStateException("Invalid event message $eventMsg")
    return BeginShiftEvent(guardId.toInt())
}

typealias GuardId = Int
typealias SleepTotal = Int
typealias Minute = Int

fun getSleepiestGuard(sleepTotals: Map<GuardId, Map<Minute, SleepTotal>>): GuardId {

    return sleepTotals.maxBy { (_, v) -> v.values.sum()}?.key ?: throw IllegalStateException("No entries")
}

fun getSleepTotals(entries: List<Entry>): Map<GuardId, Map<Minute, SleepTotal>> {
    val yearZero = DateStamp(year = 0, month = 0, day = 0, hour = 0, minute = 0)
    val noGuardIdSet = -99

    var currentGuard = noGuardIdSet
    var startTime = DateStamp(year = 0, month = 0, day = 0, hour = 0, minute = 0)

    val sleepMinuteTotals = mutableMapOf<GuardId, MutableMap<Minute, SleepTotal>>()

    entries.forEach {
        when(it.event) {
            is BeginShiftEvent -> currentGuard = it.event.guardId
            fallsAsleepEvent -> startTime = it.dateStamp
            wakesUpEvent -> {
                if (currentGuard == noGuardIdSet) throw IllegalStateException("No guard id set")
                if (startTime == yearZero) throw IllegalStateException("Start time not set")

                val sleepTotalsForGuard = sleepMinuteTotals[currentGuard] ?: mutableMapOf()

                for (minute in startTime.minute until it.dateStamp.minute) {
                    sleepTotalsForGuard[minute] = (sleepTotalsForGuard[minute] ?: 0) + 1
                }
                sleepMinuteTotals[currentGuard] = sleepTotalsForGuard
                startTime = yearZero
            }
        }
    }
    return sleepMinuteTotals
}

fun getSleepiestMinute(sleepTotals: Map<GuardId, Map<Minute, SleepTotal>>, sleepiestGuard: GuardId): Minute {

    return sleepTotals[sleepiestGuard]?.maxBy { (_, sleepTotal) -> sleepTotal }?.key ?: throw IllegalStateException("No entries")
}

fun maxMinuteTotal(minuteTotals : Map<Minute, SleepTotal>) : Minute {
    return minuteTotals.maxBy { (_, sleepTotal) -> sleepTotal }?.value ?: throw IllegalStateException("Missing minute totals")
}

fun getSleepiestMinuteStrategy2(sleepTotals: Map<GuardId, Map<Minute, SleepTotal>>): Pair<GuardId, Minute> {

    val sleepiestGuard = sleepTotals.maxBy { (_, minuteTotals) -> maxMinuteTotal(minuteTotals) }?.key
        ?: throw IllegalStateException("Missing minute totals")

    val sleepiestMinute: Int = sleepTotals[sleepiestGuard]?.maxBy { (_, sleepTotal) -> sleepTotal }?.key
        ?: throw IllegalStateException("Missing minute totals")

    return Pair(sleepiestGuard, sleepiestMinute)
}

