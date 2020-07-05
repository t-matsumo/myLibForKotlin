package library

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ModTest {
    // TODO: Add test for pow and inverse
    @Nested
    inner class ModIntTest {
        val MOD = 17L // prime number.

        val value = MOD + 1L
        val modInt = ModInt(value, MOD)

        val otherValue = MOD + 2L
        val other = ModInt(otherValue, MOD)

        val zero = ModInt(0, MOD)

        fun Long.mod() = ((this % MOD) + MOD) % MOD // mod is 0 or grater than 0, always.

        @AfterEach
        fun afterEach() = assertEquals(value.mod(), modInt.x) // modInt is imutable

        @Test
        fun construct() {
            for (i in (-3 * MOD)..(3 * MOD)) {
                assertEquals(i.mod(), ModInt(i, MOD).x)
            }
        }

        @Nested
        inner class UnaryMinusTest {
            @Test
            fun unaryMinus() = assertEquals((-value).mod(), (-modInt).x)

            @Test
            fun unaryMinusTwice() = assertEquals(value.mod(), (-(-modInt)).x)
        }

        @Nested
        inner class PlusTest {
            @Test
            fun plus() = assertEquals((value.mod() + otherValue.mod()).mod(), (modInt + other).x)

            @Test
            fun commutativeLaw() = assertEquals((modInt + other).x, (other + modInt).x)

            @Test
            fun associativeLaw() {
                val other2 = ModInt(3 + MOD, MOD)
                assertEquals(((modInt + other) + other2).x, (other + (modInt + other2)).x)
            }

            @Test
            fun plusZero() = assertEquals(value.mod(), (modInt + zero).x)

            @Test
            fun plusInverse() = assertEquals(0, (modInt + -(modInt)).x)
        }

        @Nested
        inner class MinusTest {
            @Test
            fun minus() = assertEquals((value.mod() - otherValue.mod()).mod(), (modInt - other).x)

            @Test
            fun minusZero() = assertEquals(value.mod(), (modInt - zero).x)

            @Test
            fun minusInverse() = assertEquals(0, (modInt - modInt).x)
        }

        @Nested
        inner class TimesTest {
            @Test
            fun times() = assertEquals((value.mod() * otherValue.mod()).mod(), (modInt * other).x)

            @Test
            fun commutativeLaw() = assertEquals((modInt * other).x, (other * modInt).x)

            @Test
            fun associativeLaw() {
                val other2 = ModInt(3 + MOD, MOD)
                assertEquals(((modInt * other) * other2).x, (other * (modInt * other2)).x)
            }

            @Test
            fun distributiveLaw() {
                val other2 = ModInt(3 + MOD, MOD)
                assertEquals((modInt * (other + other2)).x, ((modInt * other) + (modInt * other2)).x)
            }

            @Test
            fun timesZero() = assertEquals(0, (modInt * zero).x)

            @Test
            fun timesInverse() = assertEquals(1, (modInt * (ModInt(1, MOD) / modInt)).x)
        }

        @Nested
        inner class DivTest {
            @Test
            fun div() = assertEquals((value.mod() * inverse(otherValue, MOD)).mod(), (modInt / other).x)

            @Test
            fun distributiveLaw() {
                val other2 = ModInt(3 + MOD, MOD)
                assertEquals(((other + other2) / modInt).x, ((other / modInt) + (other2 / modInt)).x)
            }

            @Test
            fun divZero() {
                val exception = assertThrows<ArithmeticException> { (modInt / zero).x }
                assertEquals("/ by zero", exception.message)
            }

            @Test
            fun divInverse() = assertEquals(1, (modInt / modInt).x)
        }
    }
}
