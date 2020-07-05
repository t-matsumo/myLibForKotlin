package library

/** always positive number (Use for minus)
 * MODは素数（フェルマーの小定理を利用するため）
 */
class ModInt(x: Long, val MOD: Long = 1000000007L) {
    val x = ((x % MOD) + MOD) % MOD

    operator fun unaryMinus() = ModInt(-x, MOD)

    operator fun plus(other: ModInt): ModInt {
        var value = x + other.x
        if (value >= MOD) { value -= MOD }
        return ModInt(value, MOD)
    }

    operator fun minus(other: ModInt): ModInt {
        var value = x + MOD - other.x
        if (value >= MOD) { value -= MOD }
        return ModInt(value, MOD)
    }

    operator fun times(other: ModInt): ModInt {
        val value = (x * other.x) % MOD
        return ModInt(value, MOD)
    }

    operator fun div(other: ModInt)
            = if (other.x == 0L) throw ArithmeticException("/ by zero") else this * other.inverse()


    override fun toString() = x.toString()


    /** O(log(n)) */
    private fun pow(n: Long): ModInt {
        var ret = ModInt(1L, MOD)
        var x = this
        var k = n
        while (k > 0) {
            if ((k and 1L) == 1L) { ret *= x }
            x *= x
            k = k shr 1
        }
        return ret
    }

    /**  O(log(MOD)) */
    private fun inverse() = pow(MOD - 2)
}

/** O(log(m)) mod <= 2_147_483_648 */
fun pow(n: Long, m: Long, MOD: Long = (1L shl 31)): Long {
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

/** O(log(MOD))
 * MODは素数（フェルマーの小定理を利用するため）
 */
fun inverse(n: Long, MOD: Long = 1000000007L) = pow(n, MOD - 2L, MOD)