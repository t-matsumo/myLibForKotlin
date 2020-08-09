package library

/**
 * O(n^2): n <= 2000
 * table[n][m]: nCm
 */
fun pascalTriangleTable(size: Int = 2000, MOD: Long = ((1L shl 62) - 1L)): Array<LongArray> {
    val table = Array(size + 1) { LongArray(size + 1) }
    table[0][0] = 1L
    for (i in 0 until size) {
        for (j in 0..i) {
            val tmp = table[i][j]
            table[i + 1][j] = (table[i + 1][j] + tmp) % MOD
            table[i + 1][j + 1] = (table[i + 1][j + 1] + tmp) % MOD
        }
    }
    return table
}

/** n <= 20000000 */
class Combination(n: Int = 20000000, val MOD: Long = 1000000007L) {
    val fact = LongArray(n + 1)
    val ifact = LongArray(n + 1)

    /** O(n) */
    init {
        fact[0] = 1L
        for (i in 1..n) { fact[i] = (fact[i - 1] * i) % MOD }
        ifact[n] = modInverse(fact[n], MOD)
        for (i in n downTo 1) { ifact[i - 1] = (ifact[i] * i) % MOD }
    }

    /** O(1) */
    fun value(n: Int, k: Int) = if (k < 0 || k > n) 0 else (((fact[n] * ifact[k]) % MOD) * ifact[n-k]) % MOD

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
}
