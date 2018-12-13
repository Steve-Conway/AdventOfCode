package Day04

import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith


class Day04Tests {

    val exampleEntries = listOf(
        Entry(DateStamp(year = 1558, month = 11, day = 1, hour = 0, minute = 0), BeginShiftEvent(10)),
        Entry(DateStamp(year = 1558, month = 11, day = 1, hour = 0, minute = 5), fallsAsleepEvent),
        Entry(DateStamp(year = 1558, month = 11, day = 1, hour = 0, minute = 25), wakesUpEvent),
        Entry(DateStamp(year = 1558, month = 11, day = 1, hour = 0, minute = 30), fallsAsleepEvent),
        Entry(DateStamp(year = 1558, month = 11, day = 1, hour = 0, minute = 55), wakesUpEvent),
        Entry(
            DateStamp(year = 1558, month = 11, day = 1, hour = 0, minute = 58),
            BeginShiftEvent(99)
        ),
        Entry(DateStamp(year = 1558, month = 11, day = 2, hour = 0, minute = 40), fallsAsleepEvent),
        Entry(DateStamp(year = 1558, month = 11, day = 2, hour = 0, minute = 50), wakesUpEvent),
        Entry(DateStamp(year = 1558, month = 11, day = 3, hour = 0, minute = 5), BeginShiftEvent(10)),
        Entry(DateStamp(year = 1558, month = 11, day = 3, hour = 0, minute = 24), fallsAsleepEvent),
        Entry(DateStamp(year = 1558, month = 11, day = 3, hour = 0, minute = 29), wakesUpEvent),
        Entry(DateStamp(year = 1558, month = 11, day = 4, hour = 0, minute = 2), BeginShiftEvent(99)),
        Entry(DateStamp(year = 1558, month = 11, day = 4, hour = 0, minute = 36), fallsAsleepEvent),
        Entry(DateStamp(year = 1558, month = 11, day = 4, hour = 0, minute = 46), wakesUpEvent),
        Entry(DateStamp(year = 1558, month = 11, day = 5, hour = 0, minute = 3), BeginShiftEvent(99)),
        Entry(DateStamp(year = 1558, month = 11, day = 5, hour = 0, minute = 45), fallsAsleepEvent),
        Entry(DateStamp(year = 1558, month = 11, day = 5, hour = 0, minute = 55), wakesUpEvent)
    )

    val minutesForGuard10 = arrayOf(
        5 to 1,
        6 to 1,
        7 to 1,
        8 to 1,
        9 to 1,
        10 to 1,
        11 to 1,
        12 to 1,
        13 to 1,
        14 to 1,
        15 to 1,
        16 to 1,
        17 to 1,
        18 to 1,
        19 to 1,
        20 to 1,
        21 to 1,
        22 to 1,
        23 to 1,
        24 to 2,
        25 to 1,
        26 to 1,
        27 to 1,
        28 to 1,
        30 to 1,
        31 to 1,
        32 to 1,
        33 to 1,
        34 to 1,
        35 to 1,
        36 to 1,
        37 to 1,
        38 to 1,
        39 to 1,
        40 to 1,
        41 to 1,
        42 to 1,
        43 to 1,
        44 to 1,
        45 to 1,
        46 to 1,
        47 to 1,
        48 to 1,
        49 to 1,
        50 to 1,
        51 to 1,
        52 to 1,
        53 to 1,
        54 to 1)

    val minutesForGuard99 = arrayOf(
        36 to 1,
        37 to 1,
        38 to 1,
        39 to 1,
        40 to 2,
        41 to 2,
        42 to 2,
        43 to 2,
        44 to 2,
        45 to 3,
        46 to 2,
        47 to 2,
        48 to 2,
        49 to 2,
        50 to 1,
        51 to 1,
        52 to 1,
        53 to 1,
        54 to 1
    )

    val exampleSleepMinuteTotals = mapOf(
        10 to mapOf(*minutesForGuard10),
        99 to mapOf(*minutesForGuard99)
    )

    @Nested
    inner class ParseBeginShiftEvent {

        @Test
        fun `given valid event msg returns BeginShiftEvent`() {

            val event = parseBeginShiftEvent(eventMsg = "Guard #99 begins shift")

            assertEquals(BeginShiftEvent(guardId = 99), event)
        }

        @Test
        fun `given invalid event msg throws Exception`() {
            assertFailsWith(IllegalStateException::class) {
                parseBeginShiftEvent(eventMsg = "Guard #99 goes home")
            }
        }
    }

    @Nested
    inner class ParseEntry {

        @Test
        fun `given valid wakes up entry returns Entry containing wakes up event`() {

            val entry = parseEntry(entry = "[1518-05-11 00:47] wakes up")

            assertEquals(
                Entry(
                    DateStamp(year = 1518, month = 5, day = 11, hour = 0, minute = 47),
                    event = wakesUpEvent
                ),
                entry
            )
        }

        @Test
        fun `given valid falls asleep entry returns Entry containing falls asleep event`() {

            val entry = parseEntry(entry = "[1518-07-07 00:21] falls asleep")

            assertEquals(
                Entry(
                    DateStamp(year = 1518, month = 7, day = 7, hour = 0, minute = 21),
                    event = fallsAsleepEvent
                ),
                entry
            )
        }

        @Test
        fun `given valid begins shift entry returns Entry containing guard begins shift event`() {

            val entry = parseEntry(entry = "[1518-08-17 00:01] Guard #3529 begins shift")

            assertEquals(
                Entry(
                    DateStamp(year = 1518, month = 8, day = 17, hour = 0, minute = 1),
                    event = BeginShiftEvent(guardId = 3529)
                ),
                entry
            )
        }

        @Test
        fun `given invalid entry throws exception`() {
            assertFailsWith(IllegalStateException::class) {
                parseEntry(entry = "[1518-08-17 00:01 xx] Guard #3529 begins shift")
            }
        }
    }

    @Nested
    inner class DateStampCompareTo {

        @TestFactory
        fun testCompareTo() = listOf(
            // Equal
            Pair(
                DateStamp(year = 1555, month = 12, day = 25, hour = 23, minute = 3),
                DateStamp(year = 1555, month = 12, day = 25, hour = 23, minute = 3)
            )
                    to 0,
            // Year differs
            Pair(
                DateStamp(year = 1555, month = 12, day = 25, hour = 23, minute = 3),
                DateStamp(year = 1556, month = 12, day = 25, hour = 23, minute = 3)
            )
                    to -1,
            Pair(
                DateStamp(year = 1556, month = 12, day = 25, hour = 23, minute = 3),
                DateStamp(year = 1555, month = 12, day = 25, hour = 23, minute = 3)
            )
                    to 1,
            // Month differs
            Pair(
                DateStamp(year = 1556, month = 11, day = 25, hour = 23, minute = 3),
                DateStamp(year = 1556, month = 12, day = 25, hour = 23, minute = 3)
            )
                    to -1,
            Pair(
                DateStamp(year = 1556, month = 12, day = 25, hour = 23, minute = 3),
                DateStamp(year = 1555, month = 11, day = 25, hour = 23, minute = 3)
            )
                    to 1,
            // Day differs
            Pair(
                DateStamp(year = 1556, month = 12, day = 24, hour = 23, minute = 3),
                DateStamp(year = 1556, month = 12, day = 25, hour = 23, minute = 3)
            )
                    to -1,
            Pair(
                DateStamp(year = 1556, month = 12, day = 25, hour = 23, minute = 3),
                DateStamp(year = 1555, month = 12, day = 24, hour = 23, minute = 3)
            )
                    to 1,
            // Hour differs
            Pair(
                DateStamp(year = 1556, month = 12, day = 25, hour = 22, minute = 3),
                DateStamp(year = 1556, month = 12, day = 25, hour = 23, minute = 3)
            )
                    to -1,
            Pair(
                DateStamp(year = 1556, month = 12, day = 25, hour = 23, minute = 3),
                DateStamp(year = 1555, month = 12, day = 25, hour = 22, minute = 3)
            )
                    to 1,
            // Minute differs
            Pair(
                DateStamp(year = 1556, month = 12, day = 25, hour = 23, minute = 3),
                DateStamp(year = 1556, month = 12, day = 25, hour = 23, minute = 4)
            )
                    to -1,
            Pair(
                DateStamp(year = 1556, month = 12, day = 25, hour = 23, minute = 4),
                DateStamp(year = 1555, month = 12, day = 25, hour = 23, minute = 3)
            )
                    to 1
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("When I compare ${input.first} to ${input.second} then I get $expected") {
                assertEquals(expected, input.first.compareTo(input.second))
            }
        }
    }

    @Nested
    inner class EntryCompareTo {
        @TestFactory
        fun testCompareTo() = listOf(
            // Equal on datestamp
            Pair(
                Entry(
                    DateStamp(year = 1555, month = 12, day = 25, hour = 23, minute = 3),
                    fallsAsleepEvent
                ),
                Entry(
                    DateStamp(year = 1555, month = 12, day = 25, hour = 23, minute = 3),
                    wakesUpEvent
                )
            )
                    to 0,
            // Less than on datestamp
            Pair(
                Entry(
                    DateStamp(year = 1555, month = 11, day = 25, hour = 23, minute = 3),
                    fallsAsleepEvent
                ),
                Entry(
                    DateStamp(year = 1555, month = 12, day = 25, hour = 23, minute = 3),
                    wakesUpEvent
                )
            )
                    to -1,
            // Greater than on datestamp
            Pair(
                Entry(
                    DateStamp(year = 1555, month = 12, day = 25, hour = 23, minute = 3),
                    fallsAsleepEvent
                ),
                Entry(
                    DateStamp(year = 1555, month = 11, day = 25, hour = 23, minute = 3),
                    wakesUpEvent
                )
            )
                    to 1
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("When I compare ${input.first} to ${input.second} then I get $expected") {
                assertEquals(expected, input.first.compareTo(input.second))
            }
        }
    }

    @Nested
    inner class GetSleepTotals {
        @Test
        fun `for example input gives correct totals`() {

            val sleepTotals = getSleepTotals(exampleEntries)

            assertEquals(setOf(10, 99), sleepTotals.keys)

            assertEquals(
                minutesForGuard10.asList(),
                sleepTotals[10]!!.entries
                    .sortedBy { (minute, _) -> minute }
                    .map { (minute, sleepTotal) -> Pair(minute, sleepTotal) }
            )

            assertEquals(
                minutesForGuard99.asList(),
                sleepTotals[99]!!.entries
                    .sortedBy { (minute, _) -> minute }
                    .map { (minute, sleepTotal) -> Pair(minute, sleepTotal) }
            )

        }
    }

    @Nested
    inner class GetSleepiestGuard {
        @Test
        fun `for example input gets correct guard`() {

            val guardId = getSleepiestGuard(exampleSleepMinuteTotals)

            assertEquals(10, guardId)
        }
    }

    @Nested
    inner class GetSleepiestMinute {
        @Test
        fun `for example input gets correct minute for guard #10`() {

            val sleepiestMinute = getSleepiestMinute(exampleSleepMinuteTotals, sleepiestGuard = 10)

            assertEquals(24, sleepiestMinute)
        }
    }

    @Nested
    inner class GetSleepiestMinuteStrategy2 {
        @Test
        fun `for example input get correct guard and minute`() {

            val (sleepiestGuard, sleepiestMinute) = getSleepiestMinuteStrategy2(exampleSleepMinuteTotals)

            assertEquals(99, sleepiestGuard)
            assertEquals(45, sleepiestMinute)
        }
    }
}