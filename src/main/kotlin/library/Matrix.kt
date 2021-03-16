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
