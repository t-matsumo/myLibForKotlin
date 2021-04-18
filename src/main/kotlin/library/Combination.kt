package library

/** O(b) */
fun combinationWithoutMod(a: Int, b: Int): Long {
    require(a in 0..60) { "a must be in 0..60 for prevent to overflow" }
    require(b in 0..a) { "b must be in 0..a" }
    val tmpB = if (a < b * 2) a - b else b
    return (1..tmpB).fold(1L) { acc, i -> (acc * (a - i + 1)) / i }
}

/**
 * O(n^2)
 * @param size 0..2000
 * @param mod 1..(1L shl 62)
 * @return table[[n]][[m]]: nCm
 */
fun pascalTriangleTable(size: Int = 2000, mod: Long = 1_000_000_007): Array<LongArray> {
    require(size in 0..2000) { "The argument [size] must be in range 0..2000 for preventing from out of memory." }
    require(mod in 1..(1L shl 62)) {
        "Argument [mod] must be in range 1..(1L shl 62) for preventing from arithmetic error."
    }

    val table = Array(size + 1) { LongArray(size + 1) }
    table[0][0] = 1L
    for (i in 0 until size) {
        for (j in 0..i) {
            val tmp = table[i][j]
            table[i + 1][j] = (table[i + 1][j] + tmp) % mod
            table[i + 1][j + 1] = (table[i + 1][j + 1] + tmp) % mod
        }
    }
    return table
}

/**
 * @param n 0..20_000_000
 * @param mod prime number && 1..(1L shl 31 + 1)
 */
class Combination(n: Int = 20_000_000, private val mod: Long = 1_000_000_007L) {
    private val fact = LongArray(n + 1)
    private val ifact = LongArray(n + 1)

    /** O(n) */
    init {
        require(n in 0..20_000_000) { "Argument [n] must be in range 0..20_000_000 for preventing from out of memory." }
        require(mod in 1..(1L shl 31 + 1)) {
            "Argument [mod] must be in range 1..(1L shl 31 + 1) for preventing from arithmetic error."
        }
        require(mod.isPrime()) { "Argument [mod] must be prime number for culcurating mod inverse." }

        fact[0] = 1L
        for (i in 1..n) fact[i] = (fact[i - 1] * i).mod()
        ifact[n] = fact[n].modInverse()
        for (i in n downTo 1) ifact[i - 1] = (ifact[i] * i).mod()
    }

    /**
     * O(1)
     * @return [n]C[k]
     */
    operator fun get(n: Int, k: Int) = if (k in 0..n) ((fact[n] * ifact[k]).mod() * ifact[n-k]).mod() else 0L

    /** O(this^(1/2)) */
    private fun Long.isPrime(): Boolean {
        if (this == 2L) return true
        if (this <= 1L || this % 2L == 0L) return false
        var i = 3L
        while (i <= this / i) {
            if (this % i == 0L) return false
            i += 2L
        }
        return true
    }

    private fun Long.mod() = (this % mod).let { if (it < 0) it + mod else it }

    /** O(log(MOD)) */
    private fun Long.modInverse(): Long {
        var a = this.mod()
        check(a != 0L) { "[this] must be relatively prime to mod" }

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
        return u.mod()
    }
}
