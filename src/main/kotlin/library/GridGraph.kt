package library

import java.util.ArrayDeque

/** direction */
enum class GridDirection(val dx: Int, val dy: Int) {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0),
}

/** 幅優先探索 O(E) with queue
 * @param grid 幅w、高さhの状態(要素数hのString配列)（高さ：h, 幅：w）
 *  @return countGrid 各マスへの最短経路
 */
fun gridBfs(h: Int, w: Int, grid: Array<String>): Array<LongArray> {
    /** point of coordinate */
    data class Point(val x: Int, val y: Int)

    val countGrid = Array(h) { LongArray(w) }
    val checkedGrid = Array(h) { BooleanArray(w) }
    val queue = ArrayDeque<Point>()
    queue.addLast(Point(0, 0))
    checkedGrid[0][0] = true
    while (queue.isNotEmpty()) {
        val node = queue.removeFirst()
        for (d in GridDirection.values()) {
            val x = node.x + d.dx
            val y= node.y + d.dy
            if (!(y in 0 until h && x in 0 until w)) continue
            if (checkedGrid[y][x]) continue

            if (grid[y][x] == '#') continue

            countGrid[y][x] = countGrid[node.y][node.x] + 1
            checkedGrid[y][x] = true
            queue.addLast(Point(x, y))
        }
    }

    return countGrid
}