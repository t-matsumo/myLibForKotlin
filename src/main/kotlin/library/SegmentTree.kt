package library

/**
 * e: 単位元 a op e = e op a = a
 * op: 結合法則を満たす演算　(a op b) op c = a op (b op c)
 */
class SegmentTree<T>(length: Int, private val e: T, private val op: (T, T) -> T) {
    private val tree: MutableList<T>
    private val leafCount: Int

    /** O(n) */
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
        tree = MutableList(cap) { e }
    }

    /** O(nlog(n)) */
    constructor(list: List<T>, e: T, op: (T, T) -> T) : this(list.size, e, op) {
        for ((i, a) in list.withIndex()) this.update(i, a)
    }

    /** 0-indexed O(log(n)) */
    fun update(i: Int, v: T) {
        var nodeIndex = tree.size - leafCount + i
        tree[nodeIndex] = v
        while (nodeIndex > 0) {
            nodeIndex = (nodeIndex - 1) / 2
            val index = nodeIndex * 2 + 1
            tree[nodeIndex] = op(tree[index], tree[index + 1])
        }
    }

    /**
     * 0-indexed
     * [l, r)の要素にopを適用した値を返す
     * O(log(n))
     */
    fun query(l: Int, r: Int, nodeIndex: Int = 0, currentL: Int = 0, currentR: Int = leafCount): T {
        if (currentR <= l || r <= currentL) return e
        if (l <= currentL &&  currentR <= r)  return tree[nodeIndex]
        val mid = (currentL + currentR) / 2
        val childLeftNodeIndex = nodeIndex * 2 + 1
        val a = query(l, r, childLeftNodeIndex, currentL, mid)
        val b = query(l, r, childLeftNodeIndex + 1, mid, currentR)
        return op(a, b)
    }
}