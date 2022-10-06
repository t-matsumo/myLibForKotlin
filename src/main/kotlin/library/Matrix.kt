package library

class Matrix(
    private val numberOfRow: Int,
    private val numberOfColumn: Int,
    initialValueFunc: (i: Int, j: Int) -> Long = { _, _ -> 0L }
) {
    private val data = Array(numberOfRow) { i -> LongArray(numberOfColumn) { j -> initialValueFunc(i, j) } }

    operator fun set(i: Int, j: Int, value: Long) { data[i][j] = value }
    operator fun get(i: Int, j: Int) = data[i][j]

    operator fun times(other: Matrix): Matrix {
        val dataRowIndices = data[0].indices
        return Matrix(numberOfRow, other.numberOfColumn) { i, j ->
            dataRowIndices.fold(0L) { acc, k -> acc + data[i][k] * other.data[k][j] }
        }
    }
}

fun verticalVectorOf(vararg elements: Long) = Matrix(elements.size, 1) { i, _ -> elements[i] }


// 剰余を扱う行列
// いつかmodを変えられるようにしないと、、、
class ModIntMatrix(
    private val numberOfRow: Int,
    private val numberOfColumn: Int,
    initialValueFunc: (i: Int, j: Int) -> ModInt = { _, _ -> 0L.toModInt() }
) {
    private val data = Array(numberOfRow) { i -> Array(numberOfColumn) { j -> initialValueFunc(i, j) } }

    operator fun set(i: Int, j: Int, value: ModInt) { data[i][j] = value }
    operator fun get(i: Int, j: Int) = data[i][j]

    operator fun times(other: ModIntMatrix): ModIntMatrix {
        val dataRowIndices = data[0].indices
        return ModIntMatrix(numberOfRow, other.numberOfColumn) { i, j ->
            dataRowIndices.fold(0L.toModInt()) { acc, k -> acc + data[i][k] * other.data[k][j] }
        }
    }

    // O(max(row, column)^3 * logN)くらい n乗を求める
    fun pow(n: Long): ModIntMatrix {
        var ret = ModIntMatrix(numberOfRow, numberOfColumn) { i, j -> if (i == j) 1.toModInt() else 0.toModInt() }
        var x = this
        var k = n
        while (k > 0) {
            if ((k and 1L) == 1L) {
                ret = (x * ret)
            }
            x = (x * x)
            k = k shr 1
        }
        return ret
    }
}