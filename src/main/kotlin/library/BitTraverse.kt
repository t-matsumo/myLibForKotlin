package library

/**
 * O(n * (2 ^ n))
 */
fun bitTraverse(n: Int) {
    for (state in 0 until (1 shl n)) {
        for (i in 0 until n) {
            if (((state shr i) and 1) == 1) {
            }
        }
    }
}