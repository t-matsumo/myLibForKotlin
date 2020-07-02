package library

/** O(n) */
fun <T>elementCountMap(list: List<T>): Map<T, Int> {
    val countMap = mutableMapOf<T, Int>()
    for (e in list) {
        countMap[e] = (countMap[e] ?: 0) + 1
    }
    return countMap
}

/** O(log(n)) */
tailrec fun countDiv(n: Long, divisor: Long, count: Long = 0L): Long =
    if (n % divisor == 0L) countDiv(n / divisor, divisor, count + 1) else count