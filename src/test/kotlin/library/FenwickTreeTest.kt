package library

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FenwickTreeTest {
    @Test
    fun sumTest() {
        val fTree = FenwickTree(listOf(1L, 2L, 3L, 4L, 5L), 0L, { a: Long, b: Long -> a + b }, { a: Long, b: Long -> a - b })
        assertEquals(0, fTree.sum(0, 0))
        assertEquals(1, fTree.sum(0, 1))
        assertEquals(3, fTree.sum(0, 2))
        assertEquals(6, fTree.sum(0, 3))
        assertEquals(10, fTree.sum(0, 4))
        assertEquals(15, fTree.sum(0, 5))
        assertEquals(9, fTree.sum(1, 4))
        assertEquals(0, fTree.sum(5, 5))
    }

    @Test
    fun addTest() {
        val fTree = FenwickTree(listOf(1L, 2L, 3L, 4L, 5L), 0L, { a: Long, b: Long -> a + b }, { a: Long, b: Long -> a - b })
        fTree.add(0, 1)
        assertEquals(4, fTree.sum(0, 2))

        fTree.add(2, 3)
        assertEquals(8, fTree.sum(1, 3))

        assertEquals(9, fTree.sum(3, 5))
    }
}