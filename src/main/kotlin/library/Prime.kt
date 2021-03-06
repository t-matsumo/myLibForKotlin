package library

/** O(n^(1/2)) */
fun Long.isPrime(): Boolean {
    if (this == 2L) return true
    if (this <= 1L || this % 2L == 0L) return false
    var i = 3L
    while (i <= this / i) {
        if (this % i == 0L) return false
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
    return devisor
}

/** 前処理：O(nlog(logn)) 素数列挙:O(log(n)) */
fun primeListForQuery() {
    /** O(nlog(logn)) // n * n <= Int.MAX_VALUE, n <= 10^6 */
    fun primeDivTable(n: Int): IntArray {
        // iをふるい落とす値をtable[i]に入れる
        // iが素数のときはtable[i] = i
        val table = IntArray(n + 1) { it }
        if (n >= 0) table[0] = 0
        if (n >= 1) table[1] = 1
        var i = 2
        while (i * i <= n) {
            if (table[i] == i) {
                for (j in (i + i)..n step i) { table[j] = i }
            }
            i++
        }
        return table
    }

    val primeDivTable = primeDivTable(1000000)

    // ここから下の処理がlog(n)でできる(試し割りが不要なため)
    val primeList = mutableListOf<Int>() // 求めたいもの
    var target = 100 // 構成する素数を求めたい値
    while (target > 1) {
        val prime = primeDivTable[target]
        primeList.add(prime)
        while (target % prime == 0) target /= prime
    }
}