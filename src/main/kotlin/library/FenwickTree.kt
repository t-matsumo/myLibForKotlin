package library

/** 0-indexed */
class FenwickTree(originalData: LongArray) {
    private val data = originalData.copyOf()

    /** O(n)*/
    init {
        for (i in 1..this.data.size) {
            val p = i + (i and -i)

            // オーバーフローしないでー(> <) check()の条件どうすればいいんだろう...
            if (p <= this.data.size) data[p - 1] += data[i - 1]
        }
    }

    /** O(n)*/
    constructor(n: Int) : this(LongArray((n)))

    /** O(log(n))*/
    fun add(i: Int, x: Long) {
        var p = i + 1
        while (p <= data.size) {
            data[p - 1] += x
            p += (p and -p)
        }
    }

    /** O(log(n))*/
    fun sum(l: Int, r: Int) = sum(r) - sum(l)

    private fun sum(r: Int): Long {
        var i = r
        var s = 0L
        while (i > 0) {
            s += data[i - 1]
            i -= (i and -i)
        }
        return s
    }
}