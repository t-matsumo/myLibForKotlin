package library

// 未検証（きっとあってるはず、そのうち汎用的になるかも？）
// お気持ち実装なので、RMQでないのは気にしないww
class SegmentTree(length: Int) {
    private val tree: Array<Set<Char>>
    private val leafCount: Int

    init {
        var cap = 0
        var last = 1
        while (true) {
            cap += last
            if (last >= length) break
            last *= 2
        }

        if (length == 0) cap = 0
        leafCount = last
        tree = Array(cap) { emptySet<Char>() }
    }

    fun debug() {
        for ((v, i ) in tree.withIndex()) {
            out.print(v to i)
            if (v in listOf(0, 2, 6, 14)) out.println()
        }
        out.println()
    }

    fun update(i: Int, v: Char) {
        var nodeIndex = tree.size - leafCount + i
        tree[nodeIndex] = setOf(v)
        while (nodeIndex > 0) {
            nodeIndex = (nodeIndex - 1) / 2
            val a = tree[nodeIndex * 2 + 1]
            val b = tree[nodeIndex * 2 + 2]
            tree[nodeIndex] = a union b
        }
    }

    fun query(l: Int, r: Int, nodeIndex: Int = 0, currentL: Int = 0, currentR: Int = leafCount): Set<Char> {
        if (currentR <= l || r <= currentL) return emptySet()
        if (l <= currentL &&  currentR <= r)  return tree[nodeIndex]
        val a = query(l, r, nodeIndex * 2 + 1, currentL, (currentL + currentR) / 2)
        val b = query(l, r, nodeIndex * 2 + 2, (currentL + currentR) / 2, currentR)
        return a union b
    }
}