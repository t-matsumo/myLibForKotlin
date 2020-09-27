package library

/** 0-indexed */
class FenwickTree(private val n: Int) {
    private val data = LongArray(n)

    /** O(n)*/
    constructor(data: LongArray) : this(data.size) { build(data) }

    /** O(log(n))*/
    fun add(i: Int, x: Long) {
        assert(i in 0 until n)
        var p = i + 1
        while (p <= n) {
            data[p - 1] += x
            p += (p and -p)
        }
    }

    /** O(log(n))*/
    fun sum(l: Int, r: Int): Long {
        assert(l in 0..r && r <= n)
        return sum(r) - sum(l)
    }

    private fun sum(r: Int): Long {
        var i = r
        var s = 0L
        while (i > 0) {
            s += data[i - 1]
            i -= (i and -i)
        }
        return s
    }

    private fun build(rawData: LongArray) {
        System.arraycopy(rawData, 0, data, 0, n)
        for (i in 1..n) {
            val p = i + (i and -i)
            if (p <= n) data[p - 1] += data[i - 1]
        }
    }
}