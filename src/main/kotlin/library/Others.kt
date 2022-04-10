package library

fun sumIntFromOneTo(last: Int) = last * (last + 1) / 2

fun calcMaxIndex(list: List<Int>) = list.indices.maxBy { list[it] }!!

fun calcExpectedValueForDice(maxValue: Int) = (1 + maxValue) / 2.toDouble()

// 多項式の割り算の例（下記の解法は前提に注意しないといけなさそう）
// https://atcoder.jp/contests/abc245/tasks/abc245_d
fun polynomialDivision(n: Int, m: Int, aList: List<Long>, cList: List<Long>) {
    val cList = cList.toLongArray()

    val ans = LongArray(m + 1)
    for (i in m downTo 0) {
        ans[i] = cList[i + n] / aList.last()
        for ((j, a) in aList.withIndex()) {
            cList[i + j] -= ans[i] * a
        }
    }

    println(ans.joinToString(" "))
}