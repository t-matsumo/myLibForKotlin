package library

/**
 * n: 対象の要素数
 * S: 対象
 * e: Sの単位元 a op e = e op a = a
 * op: 結合法則を満たす演算　(a op b) op c = a op (b op c)
 * F: 作用素　
 * mapping: FをSに作用させる関数
 * composition: Fを合成する関数
 * id: 作用してもSが変わらないFを返す関数
 * */
class LazySegmentTree<S, F>(
    list: List<S>,
    private val e: () -> S,
    private val op: (S, S) -> S,
    private val mapping: (F, S) -> S,
    private val composition: (F, F) -> F,
    private val id: () -> F
) {
    private val tree: MutableList<S> // 1-indexed
    private val lazyTree: MutableList<F> // 1-indexed
    private val bottomSize: Int
    private val height: Int

    constructor(
        n: Int,
        e: () -> S,
        op: (S, S) -> S,
        mapping: (F, S) -> S,
        composition: (F, F) -> F,
        id: () -> F
    ): this(
        List(n) { e() },
        e,
        op,
        mapping,
        composition,
        id
    )

    init {
        var bottomSize = 1
        var height = 0
        while (bottomSize < list.size) {
            bottomSize *= 2
            height++
        }
        this.bottomSize = bottomSize
        this.height = height

        tree = MutableList(2 * bottomSize) { e() }
        lazyTree = MutableList(bottomSize) { id() }

        for (i in list.indices) {
            tree[i + bottomSize] = list[i]
        }
        for (i in (bottomSize - 1) downTo 1) {
            update(i)
        }
    }

    /**
     * p in 0 until n
     * O(log(n))
     */
    operator fun set(p: Int, x: S) {
        val p = p + bottomSize
        for (i in height downTo 1) push(p shr i)
        tree[p] = x
        for (i in 1..height) update(p shr i)
    }

    /**
     * p in 0 until n
     * O(log(n))
     */
    operator fun get(p: Int): S {
        val p = p + bottomSize
        for (i in height downTo 1) push(p shr i)
        return tree[p]
    }

    private fun update(k: Int) {
        tree[k] = op(tree[k * 2], tree[k * 2 + 1])
    }

    private fun allApply(k: Int, f: F) {
        tree[k] = mapping(f, tree[k])
        if (k < bottomSize) lazyTree[k] = composition(f, lazyTree[k])
    }

    private fun push(k: Int) {
        allApply(2 * k, lazyTree[k])
        allApply(2 * k + 1, lazyTree[k])
        lazyTree[k] = id()
    }

    /**
     * op(list[l], ..., list[r - 1])
     * l = r のときはe()
     * 0 <= l <= r <= n
     * O(log(n))
     */
    fun prod(l: Int, r: Int): S {
        var l = l + bottomSize
        var r = r + bottomSize
        if (l == r) return e()

        for (i in height downTo 1) {
            if (l shr i shl i != l) push(l shr i)
            if (r shr i shl i != r) push(r - 1 shr i)
        }
        var sml = e()
        var smr = e()
        while (l < r) {
            if (l and 1 == 1) sml = op(sml, tree[l++])
            if (r and 1 == 1) smr = op(tree[--r], smr)
            l = l shr 1
            r = r shr 1
        }
        return op(sml, smr)
    }

    fun apply(p: Int, f: F) {
        val p = p + bottomSize
        for (i in height downTo 1) push(p shr i)
        tree[p] = mapping(f, tree[p])
        for (i in 1..height) update(p shr i)
    }

    /**
     * i in l until r に対して list[i] = f(list[i])
     * 0 <= l <= r <= n
     * O(log(n))
     */
    fun apply(l: Int, r: Int, f: F) {
        var l = l + bottomSize
        var r = r + bottomSize
        if (l == r) return

        for (i in height downTo 1) {
            if (l shr i shl i != l) push(l shr i)
            if (r shr i shl i != r) push(r - 1 shr i)
        }
        run {
            val l2 = l
            val r2 = r
            while (l < r) {
                if (l and 1 == 1) allApply(l++, f)
                if (r and 1 == 1) allApply(--r, f)
                l = l shr 1
                r = r shr 1
            }
            l = l2
            r = r2
        }
        for (i in 1..height) {
            if (l shr i shl i != l) update(l shr i)
            if (r shr i shl i != r) update(r - 1 shr i)
        }
    }


    // 二分探索をいつか実装したい
}
