package library

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.AfterEach

import library.ModInt
import library.pow
import library.inverse

class ModTest {
    val MOD = 17L // prime number.
    val value = MOD + 1L
    val otherValue = MOD + 2L
    val modInt = ModInt(value, MOD)
    val other = ModInt(otherValue, MOD)

    fun Long.mod() = ((this % MOD) + MOD) % MOD // mod is 0 or grater than 0, always.

    @AfterEach
    fun afterEach() {
        assertEquals(value.mod(), modInt.x) // modInt is imutable
    }

    @Test
    fun construct() {
        assertEquals(value.mod(), modInt.x)
    }

    @Test
    fun unaryMinus() {
        assertEquals((-value).mod(), (-modInt).x)
    }

    @Test
    fun plus() {
        assertEquals((value.mod() + otherValue.mod()).mod(), (modInt + other).x)
    }

    @Test
    fun minus() {
        assertEquals((value.mod() - otherValue.mod()).mod(), (modInt - other).x)
    }

    @Test
    fun times() {
        assertEquals((value.mod() * otherValue.mod()).mod(), (modInt * other).x)
    }

    @Test
    fun div() {
        assertEquals((value.mod() * inverse(otherValue, MOD).mod()).mod(), (modInt / other).x)
    }

    // TODO: inverse, pow, minus value
}
