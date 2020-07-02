package library

/**
 * O(1): to = 0, 1, 2, 3
 * O(n * log(n)): to >= 4
 * mod <= 2_147_483_648
 */
fun sigmaK(to: Long, pow: Long = 1, MOD: Long = (1L shl 31)): Long {
    fun mod(n: Long) = n % MOD

    fun pow(n: Long, m: Long): Long {
        var ret = 1L
        var x = mod(n)
        var k = m
        while (k > 0) {
            if ((k and 1L) == 1L) { ret = mod(ret * x) }
            x = mod(x * x)
            k = k shr 1
        }
        return ret
    }

    return when (pow) {
        0L -> to
        1L -> to * (to + 1) / 2
        2L -> to * (to + 1) * (2 * to + 1) / 6
        3L -> (to * (to + 1) / 2) * (to * (to + 1) / 2)
        else -> {
            var sum = 0L
            for (i in 1..to) {
                sum += pow(i, pow)
            }
            sum
        }
    }
}