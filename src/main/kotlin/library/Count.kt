package library

/** O(n) */
fun <T> elementCountMap(list: List<T>) = list.groupingBy { it }.eachCount()

// 二分法を使うとloglog(n)でできるらしい？
/** O(log(n)) */
tailrec fun countDiv(n: Long, divisor: Long, count: Long = 0L): Long =
    if (n % divisor == 0L) countDiv(n / divisor, divisor, count + 1) else count