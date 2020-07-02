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
        val x = findRoot(x)
        val y = findRoot(y)
        if (x == y) return

        if (rank[x] < rank[y]) {
            parent[x] = y
            size[y] += size[x]
        } else {
            parent[y] = x
            if (rank[x] == rank[y]) rank[x]++

            size[x] += size[y]
        }
    }

    /** O(α(n)) */
    fun getSize(x: Int) = size[findRoot(x)]
}
