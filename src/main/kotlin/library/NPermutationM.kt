package library

/** O(n) mod <= 2_147_483_648 */
fun calcNPermutationM(n: Long, m: Long, MOD: Long = (1L shl 31)): Long {
    var ret = 1L
    for (i in (n - m + 1)..n) { ret = (ret * i) % MOD }
    return ret
}

/** O(x) mod <= 2_147_483_648 */
fun calcFactorial(x: Long, MOD: Long = (1L shl 31)) = calcNPermutationM(x, x, MOD)
