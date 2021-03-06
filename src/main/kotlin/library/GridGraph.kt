package library

import java.util.ArrayDeque
import java.util.PriorityQueue

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

/**
 * ダイクストラ法 O(？？) with priority queue
 * ノードへ進むためのコストがノードに書いてある場合
 */
fun gridDijkstra(h: Int, w: Int, grid: Array<List<Long>>, startX: Int = 0, startY: Int = 0): Array<LongArray> {
    /** point of coordinate */
    data class Point(val x: Int, val y: Int, val cost: Long)

    val countGrid = Array(h) { LongArray(w) { Long.MAX_VALUE / 4 } }
    val queue = PriorityQueue<Point> { a, b -> a.cost.compareTo(b.cost) }
    queue.add(Point(startX, startY, 0L))
    countGrid[startY][startX] = 0L
    while (queue.isNotEmpty()) {
        val node = queue.remove()
        if (countGrid[node.y][node.x] < node.cost) continue

        for (d in GridDirection.values()) {
            val x = node.x + d.dx
            val y= node.y + d.dy
            if (!(y in 0 until h && x in 0 until w)) continue
            val cost = node.cost + grid[y][x]
            if (cost < countGrid[y][x]) {
                countGrid[y][x] = cost
                queue.add(Point(x, y, cost))
            }
        }
    }

    return countGrid
}