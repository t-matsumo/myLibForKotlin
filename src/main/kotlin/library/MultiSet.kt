package library

import java.util.*

// PriorityQueueとして使えそう　削除がO(log(n))になる
// 実装していないメソッドは、this::iteratorが使われてO(n)になる可能性がある。
class MultiSet<E : Comparable<E>>(
    private val treeMap: TreeMap<E, Int> = TreeMap()
): AbstractMutableCollection<E>() {
    constructor(comparator: Comparator<E>): this(TreeMap(comparator))

    private var rowSize: Int = 0
    override val size: Int get() { return rowSize }

    // O(log(n))
    override fun add(element: E): Boolean {
        treeMap.merge(element, 1, Int::plus)
        rowSize++

        return true
    }

    // O(n)
    override fun iterator(): MutableIterator<E> {
        return object : MutableIterator<E> {
            private var currentIndex = 0
            private val values = treeMap.entries.flatMap { List(it.value) { _ -> it.key } }

            override fun hasNext() = currentIndex in values.indices
            override fun next()= values[currentIndex++]
            override fun remove() { remove(values[currentIndex - 1]) }
        }
    }

    // O(log(n))
    override fun remove(element: E) = if (treeMap.containsKey(element)) {
        treeMap.computeIfPresent(element) { _: E, value: Int ->
            val newValue = value - 1
            if (newValue == 0) null else newValue
        }
        rowSize--
        true
    } else {
        false
    }

    // 実装しないとSelf::iteratorが使われる
    override fun clear() {
        treeMap.clear()
        rowSize = 0
    }

    // O(log(n))
    override fun contains(element: E) = treeMap.containsKey(element)

    // O(log(n)) 実装しないとSelf::iteratorが使われる
    fun first() = treeMap.firstKey()
    fun firstOrNull() = if (treeMap.isEmpty()) null else first()

    // O(log(n)) 実装しないとSelf::iteratorが使われる
    fun last() = treeMap.lastKey()
    fun lastOrNull() = if (treeMap.isEmpty()) null else last()
}