package library

/**
 * 0-indexed
 * @param originalData
 * @param zero 零元
 * @param plusOperator 和
 * @param minusOperator 差
 */
class FenwickTree<T>(
    originalData: List<T>,
    private val zero: T,
    private val plusOperator: (T, T) -> T,
    private val minusOperator: (T, T) -> T
) {
    private val data = originalData.toMutableList()

    /** O(n) */
    init {
        for (i in 1..this.data.size) {
            val p = i + (i and -i)
            if (p <= this.data.size) data[p - 1] = plusOperator(data[p - 1], data[i - 1])
        }
    }

    /**
     * O(n)
     * @param n 要素数
     * @param zero 零元
     * @param plusOperator 和
     * @param minusOperator 差
     */
    constructor(
        n: Int,
        zero: T,
        plusOperator: (T, T) -> T,
        minusOperator: (T, T) -> T
    ) : this(List<T>(n) { zero }, zero, plusOperator, minusOperator)

    /** O(log(n)) */
    fun add(i: Int, x: T) {
        var p = i + 1
        while (p <= data.size) {
            data[p - 1] = plusOperator(data[p - 1], x)
            p += (p and -p)
        }
    }

    /** O(log(n)) l..(r-1)の和を返す */
    fun sum(l: Int, r: Int) = minusOperator(sum(r), sum(l))

    private fun sum(r: Int): T {
        var i = r
        var s = zero
        while (i > 0) {
            s = plusOperator(s, data[i - 1])
            i -= (i and -i)
        }
        return s
    }
}