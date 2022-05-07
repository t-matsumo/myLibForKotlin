package library

import kotlin.math.absoluteValue
import kotlin.math.sign

// gcdが必要
// 整数の最小ベクトル?
// 既約分数っぽいやつ
// 符号は分子につける???(これが必要なら別に用意すると良さそう。0割りどうしよう。。。、必要になったら使うときに頑張るで良いかも？)
data class Frac(var a: Long, var b: Long) {
    init {
        when {
            b == 0L -> {
                this.a = a.sign.toLong()
                this.b = 0
            }
            a == 0L -> {
                this.a = 0
                this.b = b.sign.toLong()
            }
            else -> {
                val gcd = gcd(a.absoluteValue, b.absoluteValue)
                this.a = a / gcd
                this.b = b / gcd
            }
        }
    }
}