package library

/** 桁dpのお気持ち実装 O(s.length) */
fun digitDp() {
    val s = "12345"
    // dp[i][smaller] 上位桁からi桁目までが、sより小さい/sと同じ（smaller=1/0）
    val dp = Array(s.length + 1) { Array(2) { 0 } }
    dp[0][0] = 1

    for (i in s.indices) {
        val n = s[i] - '0'

        // ここで10の定数倍
        for (d in 0..9) {
            dp[i + 1][1] += dp[i][1]
        }

        for (d in 0 until n) {
            dp[i + 1][1] += dp[i][0]
        }

        dp[i + 1][0] += dp[i][0]
    }
}