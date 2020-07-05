package library

/**
 * always positive number (Use for minus)
 */
class ModInt constructor(val value: Long, val mod: Long) {
    companion object {
        @JvmStatic
        fun valueOf(value: Long, mod: Long = 1000000007L): ModInt {
            var v = value % mod
            if (v < 0) v += mod
            return ModInt(v, mod)
        }
    }

    operator fun unaryMinus() = ModInt(mod - value, mod)

    operator fun plus(other: ModInt): ModInt {
        var value = value + other.value
        if (value >= mod) { value -= mod }
        return ModInt(value, mod)
    }

    operator fun minus(other: ModInt): ModInt {
        var value = value - other.value
        if (value < 0) { value += mod }
        return ModInt(value, mod)
    }

    operator fun times(other: ModInt) = ModInt((value * other.value) % mod, mod)

    operator fun div(other: ModInt)
            = if (other.value == 0L) throw ArithmeticException("/ by zero") else this * other.inverse()

    override fun toString() = value.toString()


    /** O(log(n)): n >= 0 */
    fun modPow(n: Long): ModInt {
        var ret = 1L
        var x = this.value
        var k = n
        while (k > 0) {
            if ((k and 1L) == 1L) { ret = (ret * x) % mod }
            x = (x * x) % mod
            k = k shr 1
        }
        return ModInt(ret, mod)
    }

    /**  O(log(MOD)) */
    fun inverse(): ModInt {
        if (this.value == 0L) throw java.lang.IllegalStateException("Inverse is not exist for zero")

        var a = this.value
        var b = mod
        var u = 1L
        var v = 0L
        while (b > 0) {
            var t = a / b
            a -= t * b
            a = b.also { b = a } // swap(a, b)
            u -= t * v
            u = v.also { v = u } // swap(u, v)
        }
        u %= mod
        if (u < 0) u += mod
        return ModInt(u, mod)
    }
}

/** O(log(m)) mod <= 2_147_483_648, n >= 0 */
fun modPow(n: Long, m: Long, MOD: Long = (1L shl 31)): Long {
    var ret = 1L
    var x = n % MOD
    var k = m
    while (k > 0) {
        if ((k and 1L) == 1L) { ret = (ret * x) % MOD }
        x = (x * x) % MOD
        k = k shr 1
    }
    return ret
}

/**
 * O(log(MOD))
 */
fun modInverse(n: Long, mod: Long = 1000000007L): Long {
    var a = n % mod
    if (a < 0) a += mod

    if (a == 0L) throw java.lang.IllegalStateException("Inverse is not exist for zero")

    var b = mod
    var u = 1L
    var v = 0L
    while (b > 0) {
        var t = a / b
        a -= t * b
        a = b.also { b = a } // swap(a, b)
        u -= t * v
        u = v.also { v = u } // swap(u, v)
    }
    u %= mod
    if (u < 0) u += mod
    return u
}