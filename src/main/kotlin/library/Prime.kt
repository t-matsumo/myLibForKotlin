package library

/** O(n^(1/2)) // n * n <= Long.MAX_VALUE n <= 10^8 or more */
fun isPrime(n: Long): Boolean {
    if (n == 1L) { return false }
    if (n == 2L) { return true }
    if (n % 2L == 0L) { return false }
    var i = 3L
    while (i * i <= n) {
        if (n % i == 0L) { return false }
        i += 2L
    }
    return true
}

/** O(nlog(logn)) // n * n <= Int.MAX_VALUE, n <= 10^6 */
fun primeTable(n: Int): BooleanArray {
    val table = BooleanArray(n + 1) { true }
    if (n >= 0) table[0] = false
    if (n >= 1) table[1] = false
    var i = 2
    while (i * i <= n) {
        if (table[i]) {
            for (j in (i + i)..n step i) { table[j] = false }
        }
        i++
    }
    return table
}

/**
    O(n^(1/2)) // n * n <= Long.MAX_VALUE n <= 10^8 or more
    if n == 1, return emptyMap
 */
fun primeFactor(n: Long): Map<Long, Long> {
    val factorMap = mutableMapOf<Long, Long>()
    var target = n
    var i = 2L
    while (i * i <= target) {
        while (target % i == 0L) {
            factorMap[i] = factorMap.getOrElse(i) { 0L } + 1L
            target /= i
        }
        i++
    }

    if (target != 1L) { factorMap[target] = 1 }
    return factorMap
}

/**
    O(n^(1/2)) // n * n <= Long.MAX_VALUE n <= 10^8 or more
    約数列挙
 */
fun devisor(n: Long): List<Long> {
    val devisor = mutableListOf<Long>()
    var i = 1L
    while (i * i <= n) {
        if (n % i == 0L) {
            devisor.add(i)
            if (i * i != n) { devisor.add(n / i) }
        }
        i++
    }
    devisor.sort()
    return devisor
}