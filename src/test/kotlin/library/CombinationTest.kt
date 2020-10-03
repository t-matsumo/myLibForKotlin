package library

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class CombinationTest {
    @Test fun testPascalTriangle() {
        val table = pascalTriangleTable()
        val expects = longArrayOf(1, 10, 45, 120, 210, 252, 210, 120, 45, 10, 1)
        for ((expected, actual) in expects zip table[10]) {
            assertEquals(expected, actual)
        }
    }

    @Test fun testPascalTriangleWithSize() {
        val table = pascalTriangleTable(10)
        val expects = longArrayOf(1, 10, 45, 120, 210, 252, 210, 120, 45, 10, 1)
        for ((expected, actual) in expects zip table[10]) {
            assertEquals(expected, actual)
        }
    }

    @Test fun testPascalTriangleWithSizeZero() {
        val table = pascalTriangleTable(0)
        assertEquals(1, table[0][0])
    }

    @Test fun testPascalTriangleWithPrimeMod() {
        val table = pascalTriangleTable(mod = 7)
        val expects = longArrayOf(1, 3, 3, 1, 0, 0, 0, 1, 3, 3, 1)
        for ((expected, actual) in expects zip table[10]) {
            assertEquals(expected, actual)
        }
    }

    @Test fun testCombination() {
        val table = pascalTriangleTable(mod = 1000000007)
        val aa = Combination(mod = 1000000007)
        for (i in 0..20) {
            for (j in 0..20) {
                assertEquals(table[i][j], aa[i, j])
            }
        }
    }
}
