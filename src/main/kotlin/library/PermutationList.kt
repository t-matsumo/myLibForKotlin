package library

/** O((array.size)!) */
fun <T : Comparable<T>> permutationListFrom(array: Array<T>): List<Array<T>> {
    val current = array.copyOf()
    val permutationList = mutableListOf<Array<T>>()
    do { permutationList += current.copyOf() } while (nextPermutation(current))
    return permutationList
}

/** O(n): n = currentPermutation.size */
fun <T : Comparable<T>> nextPermutation(currentPermutation: Array<T>): Boolean {
    fun <T> Array<T>.reverse(start: Int, end: Int) {
        val midPoint = ((start + end) / 2) - 1
        if (midPoint < 0) return
        var reverseIndex = end - 1
        for (index in start..midPoint) {
            val tmp = this[index]
            this[index] = this[reverseIndex]
            this[reverseIndex] = tmp
            reverseIndex--
        }
    }

    val n = currentPermutation.size
    var i = n - 2
    while (i >= 0) {
        if (currentPermutation[i] < currentPermutation[i + 1]) break
        i--
    }
    if (i < 0) return false
    var j = n
    do { j-- } while (currentPermutation[i] > currentPermutation[j])
    if (currentPermutation[i] < currentPermutation[j]) {
        val tmp = currentPermutation[i]
        currentPermutation[i] = currentPermutation[j]
        currentPermutation[j] = tmp
        currentPermutation.reverse(i + 1, n)
        return true
    }
    return false
}