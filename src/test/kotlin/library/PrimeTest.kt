package library

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class PrimeTest {
    val primeList = arrayOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47)
    val isPrimes = booleanArrayOf(
        false, false, true, true, false, true, false, true, false, false,
        false, true, false, true, false, false, false, true, false, true,
        false, false, false, true, false, false, false, false, false, true
    )

    @Nested
    inner class IsPrimeTest {
        @Test
        fun primeTest() {
            for (i in 0 until 30) {
                assertEquals(isPrimes[i], isPrime(i.toLong()))
            }
        }
    }

    @Nested
    inner class PrimeTableTest {
        @Test
        fun primeTableTest() {
            val table = primeTable(29)
            for (i in 0..29) {
                assertEquals(isPrimes[i], table[i])
            }
        }
    }
}