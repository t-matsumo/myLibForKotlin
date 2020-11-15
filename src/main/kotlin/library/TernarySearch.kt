package library

/**
 * 3分探索（下に凸な関数の最小値を求める例）
 * O(log(n))
 */
fun ternarySearch() {
    // 下に凸な関数
    fun f(t: Double) = t + p / 2.0.pow(t / 1.5)

    var l = 0.0
    var r = 1e18
    // 探索回数を確認（もっと良い方法がありそう）
//    out.println(r * (2.0 / 3).pow(150.0))
    for (i in 1..150) {
        val tmpL = l + (r - l) / 3
        val tmpR = r - (r - l) / 3
        if (f(tmpL) < f(tmpR)){
            r = tmpR
        } else {
            l = tmpL
        }
    }
    // l or r が答え（探索が終わっていれば同じ値になるはず）
}
