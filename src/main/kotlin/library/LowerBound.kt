package library

/** O(log(n)): n = list.size */
fun <T : Comparable<T>>lowerBound(list: List<T>, target: T): Int {
    var left = -1
    var right = list.size
    while (right - left > 1) {
        val mid = (right + left) / 2
        if (target <= list[mid]) right = mid else left = mid
    }
    return right
}