package library

import java.util.*
import kotlin.math.abs

/** edge info */
data class Edge(val from: Int, val to: Int, val cost: Long)

/** distance */
data class Distance(val to: Int, val cost: Long) : Comparable<Distance> {
    override fun compareTo(other: Distance)= cost.compareTo(other.cost)
}

/** graph[fromNodeIndex]: edges to other node */
val n = 10 // number of node
val graph = Array(n) { mutableListOf<Edge>() }

/**
 * O((costMatrix.size)^3) costMatrix is changed
 * 全点対間最短路
 */
fun warshallFloyd(costMatrix: Array<LongArray>, LINF: Long = Long.MAX_VALUE) {
    val indices = costMatrix.indices
    for (k in indices)
        for (i in indices)
            for (j in indices)
                if (costMatrix[i][k] != LINF && costMatrix[k][j] != LINF)
                    costMatrix[i][j] = Math.min(costMatrix[i][j], costMatrix[i][k] + costMatrix[k][j])
}

/** E:辺数、V:頂点数  */
/** 深さ先探索 O(E) with stack
 * 経路の有無判定
 */
fun dfs(startNode: Int, edges: Array<MutableList<Edge>>): IntArray {
    val costArray = IntArray(edges.size)
    val isUnchecked = BooleanArray(edges.size) { true }
    val nodeStack = ArrayDeque<Int>()
    nodeStack.addLast(startNode)
    isUnchecked[startNode] = false
    while (nodeStack.isNotEmpty()) {
        val node = nodeStack.removeLast()
        for (edge in edges[node]) {
            if (isUnchecked[edge.to]) {
                costArray[edge.to] = costArray[node] + 1
                isUnchecked[edge.to] = false
                nodeStack.addLast(edge.to)
            }
        }
    }

    return costArray
}

/** 幅優先探索 O(E) with queue
 *  最短経路 辺の重みが正かつすべて同一 または木
 *  @param startNode 0~(n-1)
 *  @param edges
 */
fun bfs(startNode: Int, edges: Array<MutableList<Edge>>): IntArray {
    val costArray = IntArray(edges.size)
    val isUnchecked = BooleanArray(edges.size) { true }
    val nodeQueue = ArrayDeque<Int>()
    nodeQueue.addLast(startNode)
    isUnchecked[startNode] = false
    while (nodeQueue.isNotEmpty()) {
        val node = nodeQueue.removeFirst()
        for (edge in edges[node]) {
            if (isUnchecked[edge.to]) {
                costArray[edge.to] = costArray[node] + 1
                isUnchecked[edge.to] = false
                nodeQueue.addLast(edge.to)
            }
        }
    }

    return costArray
}

/** ダイクストラ法 O(E * long(V)) with priority queue
 *  辺の重みが正
 */
fun dijkstra(graph: List<List<Edge>>, startNode: Int, LINF: Long = Long.MAX_VALUE): List<Long> {
    val shortestDistanceList = MutableList(graph.size) { LINF }
    val nodeQueue = PriorityQueue<Distance>()
    nodeQueue.add(Distance(startNode, 0L))
    shortestDistanceList[startNode] = 0L
    while (nodeQueue.isNotEmpty()) {
        val distance = nodeQueue.remove()
        if (shortestDistanceList[distance.to] < distance.cost) continue
        for (edge in graph[distance.to]) {
            val cost = distance.cost + edge.cost
            if (cost < shortestDistanceList[edge.to]) {
                shortestDistanceList[edge.to] = cost
                nodeQueue.add(Distance(edge.to, cost))
            }
        }
    }
    return shortestDistanceList
}

/** ベルマンフォード法 O(EV)
 *  辺の重みが負, 負の閉路の検出
 */
fun bellmanFord(nodeNum: Int, edges: MutableList<Edge>, startNode: Int, LINF: Long = Long.MAX_VALUE): Pair<Boolean, LongArray> {
    val shortestDistanceArray = LongArray(nodeNum) { LINF }
    shortestDistanceArray[startNode] = 0L
    var hasNegativeCycle = false
    for (i in 0 until nodeNum) {
        var updated = false
        for (edge in edges) {
            if (shortestDistanceArray[edge.from] != LINF &&
                shortestDistanceArray[edge.to] > shortestDistanceArray[edge.from] + edge.cost) {
                shortestDistanceArray[edge.to] = shortestDistanceArray[edge.from] + edge.cost
                updated = true
            }
        }
        if (!updated) break
        if (updated && i == nodeNum - 1) {
            hasNegativeCycle = true
            break
        }
    }
    return Pair(hasNegativeCycle, shortestDistanceArray)
}

/**
 * graph: 0-indexed graph
 * 強連結成分分解をする
 */
class SCC(private val graph: List<List<Edge>>) {
    private val reverseGraph: List<List<Edge>>

    init {
        val reverseGraph = List(graph.size) { mutableListOf<Edge>() }
        for (edges in graph) {
            for (edge in edges) {
                reverseGraph[edge.to].add(Edge(edge.to, edge.from, edge.cost))
            }
        }
        this.reverseGraph = reverseGraph
    }

    /**
     * O(n)のはず？違くない？
     * @return 強連結成分分解をした結果 外側のListはトポロジカルソートされている。
     */
    fun build(): List<List<Int>> {
        val numArray = mutableListOf<Int>()
        val checkingIndexFor = IntArray(graph.size)
        val isChecked = BooleanArray(graph.size)
        val nodeStack = ArrayDeque<Int>()
        for (startNode in graph.indices) {
            if (isChecked[startNode]) continue

            nodeStack.addLast(startNode)
            isChecked[startNode] = true
            while (nodeStack.isNotEmpty()) {
                val node = nodeStack.peekLast()
                for (i in checkingIndexFor[node]..graph[node].lastIndex) {
                    val to = graph[node][i].to
                    if (!isChecked[to]) {
                        isChecked[to] = true
                        nodeStack.addLast(to)
                        checkingIndexFor[node] = i + 1
                        break
                    }
                }

                if (node == nodeStack.peekLast()) {
                    nodeStack.removeLast()
                    numArray.add(node)
                }
            }
        }

        val scc = mutableListOf<MutableList<Int>>()
        isChecked.fill(false)
        for (startNode in numArray.reversed()) {
            if (isChecked[startNode]) continue

            val list = mutableListOf<Int>()
            nodeStack.addLast(startNode)
            isChecked[startNode] = true
            while (nodeStack.isNotEmpty()) {
                val node = nodeStack.removeLast()
                list.add(node)
                for (edge in reverseGraph[node]) {
                    if (!isChecked[edge.to]) {
                        isChecked[edge.to] = true
                        nodeStack.addLast(edge.to)
                    }
                }
            }

            scc.add(list)
        }
        return scc
    }
}

/** direction */
enum class Direction(val dx: Int, val dy: Int) {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    UPPER_LEFT(-1, -1),
    UPPER_RIGHT(1, -1),
    LOWER_LEFT(-1, 1),
    LOWER_RIGHT(1, 1),
}

/** point of coordinate */
data class Point(val x: Long, val y: Long) {
    fun distanceTo(p: Point) = abs(p.x - this.x) + abs(p.y - this.y)
}