package Day03

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue


class Day03Tests {

    val exampleClaims = mapOf(
    Coord(3, 1) to 1,
    Coord(4, 1) to 1,
    Coord(5, 1) to 1,
    Coord(6, 1) to 1,

    Coord(3, 2) to 1,
    Coord(4, 2) to 1,
    Coord(5, 2) to 1,
    Coord(6, 2) to 1,

    Coord(1, 3) to 1,
    Coord(2, 3) to 1,
    Coord(3, 3) to 2,
    Coord(4, 3) to 2,
    Coord(5, 3) to 1,
    Coord(6, 3) to 1,

    Coord(1, 4) to 1,
    Coord(2, 4) to 1,
    Coord(3, 4) to 2,
    Coord(4, 4) to 2,
    Coord(5, 4) to 1,
    Coord(6, 4) to 1,

    Coord(1, 5) to 1,
    Coord(2, 5) to 1,
    Coord(3, 5) to 1,
    Coord(4, 5) to 1,
    Coord(5, 5) to 1,
    Coord(6, 5) to 1,

    Coord(1, 6) to 1,
    Coord(2, 6) to 1,
    Coord(3, 6) to 1,
    Coord(4, 6) to 1,
    Coord(5, 6) to 1,
    Coord(6, 6) to 1
    )

    val exampleClaim1 = Claim(id = 1, leftMargin = 1, topMargin = 3, width = 4, height = 4)
    val exampleClaim2 = Claim(id = 2, leftMargin = 3, topMargin = 1, width = 4, height = 4)
    val exampleClaim3 = Claim(id = 3, leftMargin = 5, topMargin = 5, width = 2, height = 2)


    @BeforeEach
    fun init() {
        claimedCoords.clear()
    }

    @Nested
    inner class ParseClaim {

        @Test
        fun `given minimal input returns Claim`() {
            val input = "#1 @ 2,3: 4x5"

            val claim = parseClaim(input)

            assertEquals(Claim(id = 1, leftMargin = 2, topMargin = 3, width = 4, height = 5), claim, "Incorrect claim")
        }

        @Test
        fun `given longer input returns Claim`() {
            val input = "#111 @ 222,333: 444x555"

            val claim = parseClaim(input)

            assertEquals(
                Claim(id = 111, leftMargin = 222, topMargin = 333, width = 444, height = 555),
                claim,
                "Incorrect claim"
            )
        }

        @Test
        fun `given invalid input returns null`() {
            val input = "#111 @ 222 333: 444x555"

            val claim = parseClaim(input)

            assertNull(claim, "Expected null claim")
        }

        @Test
        fun `given empty input returns null`() {
            val input = ""

            val claim = parseClaim(input)

            assertNull(claim, "Expected null claim")
        }
    }

    @Nested
    inner class ClaimCoords {

        @Test
        fun `given claim with single unit sided rectangle at origin then single coord claimed`() {

            val claim = Claim(id = 123, leftMargin = 0, topMargin = 0, width = 1, height = 1)

            claimCoords(claim)

            assertEquals(
                mapOf(Coord(0, 0) to 1),
                claimedCoords,
                "Incorrect claimed coords"
            )
        }

        @Test
        fun `given claim with multiple unit sided rectangle at origin then correct coords claimed`() {

            val claim = Claim(id = 123, leftMargin = 0, topMargin = 0, width = 2, height = 3)

            claimCoords(claim)

            assertEquals(
                mapOf(
                    Coord(0, 0) to 1,
                    Coord(1, 0) to 1,
                    Coord(0, 1) to 1,
                    Coord(1, 1) to 1,
                    Coord(0, 2) to 1,
                    Coord(1, 2) to 1
                ),
                claimedCoords,
                "Incorrect claimed coords"
            )
        }

        @Test
        fun `given claim with single unit sided rectangle not at origin then single coord claimed`() {

            val claim = Claim(id = 123, leftMargin = 5, topMargin = 10, width = 1, height = 1)

            claimCoords(claim)

            assertEquals(
                mapOf(Coord(5, 10) to 1),
                claimedCoords,
                "Incorrect claimed coords"
            )
        }

        @Test
        fun `given claim with multiple unit sided rectangle not at origin then correct coords claimed`() {

            val claim = Claim(id = 123, leftMargin = 7, topMargin = 66, width = 2, height = 3)

            claimCoords(claim)

            assertEquals(
                mapOf(
                    Coord(7, 66) to 1,
                    Coord(8, 66) to 1,
                    Coord(7, 67) to 1,
                    Coord(8, 67) to 1,
                    Coord(7, 68) to 1,
                    Coord(8, 68) to 1
                ),
                claimedCoords,
                "Incorrect claimed coords"
            )
        }

        @Test
        fun `given two non overlapping claims with single unit sided rectangles then two coords claimed`() {

            val claim1 = Claim(id = 1, leftMargin = 5, topMargin = 10, width = 1, height = 1)
            val claim2 = Claim(id = 2, leftMargin = 99, topMargin = 3, width = 1, height = 1)

            claimCoords(claim1)
            claimCoords(claim2)

            assertEquals(
                mapOf(
                    Coord(5, 10) to 1,
                    Coord(99, 3) to 1
                ),
                claimedCoords,
                "Incorrect claimed coords"
            )
        }

        @Test
        fun `given two overlapping claims with single unit sided rectangles then one coord claimed twice`() {

            val claim1 = Claim(id = 1, leftMargin = 5, topMargin = 10, width = 1, height = 1)
            val claim2 = Claim(id = 2, leftMargin = 5, topMargin = 10, width = 1, height = 1)

            claimCoords(claim1)
            claimCoords(claim2)

            assertEquals(
                mapOf(
                    Coord(5, 10) to 2
                ),
                claimedCoords,
                "Incorrect claimed coords"
            )
        }

        @Test
        fun `given three overlapping claims then correct coords claimed`() {

            // Example from advent of code

            claimCoords(exampleClaim1)
            claimCoords(exampleClaim2)
            claimCoords(exampleClaim3)

            assertEquals(
                exampleClaims,
                claimedCoords,
                "Incorrect claimed coords"
            )
        }
    }

    @Nested
    inner class CountMultipleClaims {

        @Test
        fun `given example claims then count is correct`() {
            claimedCoords.putAll(exampleClaims)

            val count = countMultipleClaims()

            assertEquals(4, count, "Incorrect count")
        }
    }

    @Nested
    inner class ClaimIsNonOverlapping {

        @Test
        fun `given example claims then claim 1 is overlapping`() {
            claimedCoords.putAll(exampleClaims)

            val nonOverlapping = claimIsNonOverlapping(exampleClaim1)

            assertFalse(nonOverlapping)
        }

        @Test
        fun `given example claims then claim 3 is non overlapping`() {
            claimedCoords.putAll(exampleClaims)

            val nonOverlapping = claimIsNonOverlapping(exampleClaim3)

            assertTrue(nonOverlapping)
        }
    }
}