package library

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import kotlin.math.max

// バグらせやすいので残しておく
// https://atcoder.jp/contests/abc179/tasks/abc179_e
// 数回処理をするとループするとき、n回までの合計を求める
fun loopImplementationExample(n: Long, x: Long, m: Long): Long {
//    val (n, x, m) = readLongList()

    // 何個目の項からループするのかを求める
    val indexMap = mutableMapOf<Long, Long>()
    var cur = x // 初期値
    var curIndex = 0L
    while (!indexMap.containsKey(cur)) {
        indexMap[cur] = curIndex
        curIndex++
        cur = cur * cur % m // ループする処理
    }
    val loopFromIndex = indexMap[cur]!!

    // ループする前の合計、1ループの合計、1ループの項数
    val sumBeforeLoop = indexMap
        .entries
        .fold(0L) { acc, v -> acc + if (v.value < loopFromIndex) v.key else 0 }
    val sumOneLoop = indexMap
        .entries
        .fold(0L) { acc, v -> acc + if (v.value >= loopFromIndex) v.key else 0 }
    val oneLoopSize = indexMap.count() - loopFromIndex

    // ループ開始後のn、ループする数、ループ終了後のn
    val nAfterStartLoop = max(0, n - loopFromIndex)
    val loopCount = nAfterStartLoop / oneLoopSize
    val nAfterEndLoop = nAfterStartLoop % oneLoopSize

    var ans = if (n <= loopFromIndex) 0L else sumBeforeLoop + loopCount * sumOneLoop

    var pre = if (n <= loopFromIndex) x else cur
    val simulateCount = if (n <= loopFromIndex) n.toInt() else nAfterEndLoop.toInt()
    repeat(simulateCount) {
        ans += pre
        pre = pre * pre % m // ループする処理
    }

    return ans
}
