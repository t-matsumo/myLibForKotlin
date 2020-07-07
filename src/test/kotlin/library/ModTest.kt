package library

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ModTest {
    val MOD = 17L // prime number.

    fun Long.mod() = ((this % MOD) + MOD) % MOD // mod is 0 or grater than 0, always.

    @Nested
    inner class ModIntTest {
        val value = MOD + 1L
        val modInt = ModInt.valueOf(value, MOD)

        val otherValue = MOD + 2L
        val other = ModInt.valueOf(otherValue, MOD)

        val zero = ModInt.valueOf(0, MOD)

        @AfterEach
        fun afterEach() = assertEquals(value.mod(), modInt.value) // modInt is immutable

        @Test
        fun construct() {
            for (i in (-3 * MOD)..(3 * MOD)) {
                assertEquals(i.mod(), ModInt.valueOf(i, MOD).value, "value:$i")
            }
        }

        @Nested
        inner class UnaryMinusTest {
            @Test
            fun unaryMinus() = assertEquals((-value).mod(), (-modInt).value)

            @Test
            fun unaryMinusTwice() = assertEquals(value.mod(), (-(-modInt)).value)
        }

        @Nested
        inner class PlusTest {
            @Test
            fun plus() = assertEquals((value.mod() + otherValue.mod()).mod(), (modInt + other).value)

            @Test
            fun commutativeLaw() = assertEquals((modInt + other).value, (other + modInt).value)

            @Test
            fun associativeLaw() {
                val other2 = ModInt.valueOf(3 + MOD, MOD)
                assertEquals(((modInt + other) + other2).value, (other + (modInt + other2)).value)
            }

            @Test
            fun plusZero() = assertEquals(value.mod(), (modInt + zero).value)

            @Test
            fun plusInverse() = assertEquals(0, (modInt + -(modInt)).value)
        }

        @Nested
        inner class MinusTest {
            @Test
            fun minus() = assertEquals((value.mod() - otherValue.mod()).mod(), (modInt - other).value)

            @Test
            fun minusZero() = assertEquals(value.mod(), (modInt - zero).value)

            @Test
            fun minusInverse() = assertEquals(0, (modInt - modInt).value)
        }

        @Nested
        inner class TimesTest {
            @Test
            fun times() = assertEquals((value.mod() * otherValue.mod()).mod(), (modInt * other).value)

            @Test
            fun commutativeLaw() = assertEquals((modInt * other).value, (other * modInt).value)

            @Test
            fun associativeLaw() {
                val other2 = ModInt.valueOf(3 + MOD, MOD)
                assertEquals(((modInt * other) * other2).value, (other * (modInt * other2)).value)
            }

            @Test
            fun distributiveLaw() {
                val other2 = ModInt.valueOf(3 + MOD, MOD)
                assertEquals((modInt * (other + other2)).value, ((modInt * other) + (modInt * other2)).value)
            }

            @Test
            fun timesZero() = assertEquals(0, (modInt * zero).value)

            @Test
            fun timesInverse() = assertEquals(1, (modInt * (ModInt.valueOf(1, MOD) / modInt)).value)
        }

        @Nested
        inner class DivTest {
            @Test
            fun div() = assertEquals((value.mod() * modInverse(otherValue, MOD)).mod(), (modInt / other).value)

            @Test
            fun distributiveLaw() {
                val other2 = ModInt.valueOf(3 + MOD, MOD)
                assertEquals(((other + other2) / modInt).value, ((other / modInt) + (other2 / modInt)).value)
            }

            @Test
            fun divZero() {
                val exception = assertThrows<ArithmeticException> { (modInt / zero).value }
                assertEquals("/ by zero", exception.message)
            }

            @Test
            fun divInverse() = assertEquals(1, (modInt / modInt).value)
        }

        @Nested
        inner class PowTest {
            // delegate to modPow
            @Test
            fun pow() = assertEquals(modPow(2, 10, MOD), ModInt.valueOf(2L, MOD).modPow(10L).value)
        }

        @Nested
        inner class InverseTest {
            // delegate to modInverse
            @Test
            fun inverse() = assertEquals(modInverse(1024L, MOD), ModInt.valueOf(1024L, MOD).inverse().value)
        }
    }

    @Nested
    inner class ModPowTest {
        @Test
        fun nIsZero() {
            assertEquals(0, modPow(0, 10, MOD))
            assertEquals(0, modPow(0, 11, MOD))
        }

        @Test
        fun nIsOne() {
            assertEquals(1, modPow(1, 10, MOD))
            assertEquals(1, modPow(1, 11, MOD))
        }

        @Test
        fun negativeN() {
            assertEquals(1024L.mod(), modPow(-2, 10, MOD))
            assertEquals((-2048L).mod(), modPow(-2, 11, MOD))
        }

        @Test
        fun positiveExponent() = assertEquals(1024L.mod(), modPow(2, 10, MOD))

        @Test
        fun zeroExponent() {
            assertEquals(1, modPow(2, 0, MOD))
            assertEquals(1, modPow(3, 0, MOD))
        }

        @Test
        fun negativeExponent() {
            val exception = assertThrows<ArithmeticException> { modPow(2, -1, MOD) }
            assertEquals("The power of negative exponent is not integer.", exception.message)

            val exception2 = assertThrows<ArithmeticException> { modPow(2, -10, MOD) }
            assertEquals("The power of negative exponent is not integer.", exception2.message)
        }

        @Test
        fun zeroOrNegativeMod() {
            val exception = assertThrows<ArithmeticException> { modPow(2, 10, 0) }
            assertEquals("MOD <= 0", exception.message)
            val exception2 = assertThrows<ArithmeticException> { modPow(2, 10, -10) }
            assertEquals("MOD <= 0", exception2.message)
        }
    }

    @Nested
    inner class ModInverseTest {
        @Test
        fun nIsNotRelativelyPrimeToMod() {
            val exception = assertThrows<ArithmeticException> { modInverse(0, MOD) }
            assertEquals("n is not relatively prime to mod", exception.message)
            val exception2 = assertThrows<ArithmeticException> { modInverse(MOD * 3, MOD) }
            assertEquals("n is not relatively prime to mod", exception2.message)
        }

        @Test
        fun nIsNegative() {
            assertEquals(modPow(-10, MOD - 2, MOD), modInverse(-10, MOD))
            assertEquals(modPow(-11, MOD - 2, MOD), modInverse(-11, MOD))
        }

        @Test
        fun nIsPositive() {
            assertEquals(modPow(10, MOD - 2, MOD), modInverse(10, MOD))
            assertEquals(modPow(11, MOD - 2, MOD), modInverse(11, MOD))
        }
    }
}
