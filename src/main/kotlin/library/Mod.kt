package library

const val MOD = 1_000_000_007L
fun Long.mod() = (this % MOD).let { if (it < 0) it + MOD else it }

/**
 * always positive number (Use for minus)
 */
fun Long.toModInt(mod: Long = 1000000007L) = ModInt.valueOf(this, mod)
fun Int.toModInt(mod: Long = 1000000007L) = ModInt.valueOf(this.toLong(), mod)
class ModInt private constructor(val value: Long, val mod: Long) {
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

    operator fun div(other: ModInt): ModInt {
        if (other.value == 0L) throw ArithmeticException("/ by zero")
        val value = (this.value * modInverse(other.value, mod)) % mod
        return ModInt(value, mod)
    }

    override fun toString() = value.toString()

    /** O(log(n)): n >= 0 */
    fun modPow(n: Long) = ModInt(modPow(this.value, n, mod), mod)

    /**  O(log(MOD)) */
    fun inverse() = ModInt(modInverse(this.value, mod), mod)
}

/** O(log(m)) mod <= 2_147_483_648, n >= 0 */
fun modPow(n: Long, m: Long, MOD: Long = (1L shl 31)): Long {
    if (m < 0) throw ArithmeticException("The power of negative exponent is not integer.")
    if (MOD <= 0) throw ArithmeticException("MOD <= 0")

    var ret = 1L
    var x = (n % MOD)
    if (x < 0) x += MOD
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

    if (a == 0L) throw ArithmeticException("n is not relatively prime to mod")

    var b = mod
    var u = 1L
    var v = 0L
    while (b > 0) {
        val t = a / b
        a -= t * b
        a = b.also { b = a } // swap(a, b)
        u -= t * v
        u = v.also { v = u } // swap(u, v)
    }
    u %= mod
    if (u < 0) u += mod
    return u
}

/**
 * solve ax ≡ b (mod m)
 *
 * return x, or -1 if x is not exist.
 */
fun solveAXCongruentBModM(a: Long, b: Long, mod: Long, valueOfNotExist: Long = -1): Long {
    val g = gcd(a, mod)

    // ax - b ≡ 0 (mod m)
    // ga'x - b = ygm'　※g = gcd(a, m)
    // b = g(a'x - ym') ※bはgの倍数でないければいけない
    if (b % g != 0L) return valueOfNotExist // 解なし

    // b = g(a'x - ym')の両辺をgで割ると
    // b / g = a'x - ym'
    // a'x - b / g = ym'
    // a'x ≡ b / g (mod m') ※a'とm'は互いに素なのでa'の逆元が求められる
    // x ≡　(b / g) * (a'^-1) (mod m')
    val ag = a / g
    val bg = b / g
    val modg = mod / g
    return ((bg * modInverse(ag, modg)) % modg).let { if (it < 0) it + modg else it }
}