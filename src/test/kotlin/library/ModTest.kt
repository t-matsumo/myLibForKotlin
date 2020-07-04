package library

import org.junit.jupiter.api.Assertions.assertEquals
// import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.test.Test
// import kotlin.test.BeforeTest
import org.junit.jupiter.api.Nested
// import org.junit.jupiter.api.assertAll
// import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
// import org.junit.jupiter.api.assertTimeout
// import org.junit.jupiter.api.assertTimeoutPreemptively

import library.ModInt
import library.pow
import library.inverse

class ModTest {
    // @Nested
    // inner class ModIntTest {
        @Test
        fun unaryMinus() {
            val value = 2L
            val modInt = ModInt(value)
            assertEquals(-value, (-modInt).x)
        }

        @Test
        fun plus() {
            val value = 1L
            val modInt = ModInt(value)
            val otherValue = 2L
            val other = ModInt(otherValue)
            assertEquals(value + otherValue, (modInt + other).x)
        }

    // }
}
