package library

/**　Union find 0 ~ (n - 1)　*/
class UnionFind(n: Int) {
    val parent = IntArray(n) { it }
    val rank = IntArray(n)
    val size = IntArray(n) { 1 }

    /** O(α(n)) */
    fun findRoot(x: Int): Int {
        if (parent[x] == x) return x

        parent[x] = findRoot(parent[x])
        return parent[x]
    }

    /** O(α(n)) */
    fun sameRoot(x: Int, y: Int) = findRoot(x) == findRoot(y)

    /** O(α(n)) */
    fun unite(x: Int, y: Int) {
        val xRoot = findRoot(x)
        val yRoot = findRoot(y)
        if (xRoot == yRoot) return

        if (rank[xRoot] < rank[yRoot]) {
            parent[xRoot] = yRoot
            size[yRoot] += size[xRoot]
        } else {
            parent[yRoot] = xRoot
            if (rank[xRoot] == rank[yRoot]) rank[xRoot]++

            size[xRoot] += size[yRoot]
        }
    }

    /** O(α(n)) */
    fun getSize(x: Int) = size[findRoot(x)]
}
