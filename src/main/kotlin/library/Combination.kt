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

/** n <= 20000000
 * MODは素数（フェルマーの小定理を利用するため）
 */
class Combination(n: Int = 20000000, val MOD: Long = 1000000007L) {
    val fact = LongArray(n + 1)
    val ifact = LongArray(n + 1)

    /** O(n) */
    init {
        fact[0] = 1L
        for (i in 1..n) { fact[i] = (fact[i - 1] * i) % MOD }
        ifact[n] = inverse(fact[n], MOD)
        for (i in n downTo 1) { ifact[i - 1] = (ifact[i] * i) % MOD }
    }

    /** O(1) */
    fun value(n: Int, k: Int) = if (k < 0 || k > n) 0 else (((fact[n] * ifact[k]) % MOD) * ifact[n-k]) % MOD

    /** O(log(m)) */
    fun pow(n: Long, m: Long, MOD: Long): Long {
        var ret = 1L
        var x = n
        var k = m
        while (k > 0) {
            if ((k and 1L) == 1L) { ret = (ret * x) % MOD }
            x = (x * x) % MOD
            k = k shr 1
        }
        return ret
    }

    /** O(log(MOD)) */
    fun inverse(n: Long, MOD: Long) = pow(n, MOD - 2L, MOD)
}
