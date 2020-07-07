import java.io.PrintWriter
import kotlin.system.measureTimeMillis
import library.ModInt

val br = System.`in`.bufferedReader()
val out = PrintWriter(System.out)

fun main() {
    br.use { out.use {
        solve()
        out.flush()
    } }
}

fun solve() {
    val t = measureTimeMillis {
        val a = ModInt.valueOf(100)

        var b = ModInt.valueOf(100)
        for (i in 0 until 100000) {
            b = b + a
        }
        out.println(b)

        var c = ModInt.valueOf(100)
        for (i in 0 until 100000) {
            c = c - a
        }
        out.println(c)

        var d = ModInt.valueOf(100)
        for (i in 0 until 100000) {
            d = d * a
        }
        out.println(d)

        var e = ModInt.valueOf(100)
        for (i in 0 until 10000000) {
            e = e / a
        }
        out.println(e)
    }
    out.println("Time: $t")
}

fun readInteger() = Integer.parseInt(br.readLine())
fun readLong() = java.lang.Long.parseLong(br.readLine())
fun readStringList() = br.readLine()!!.split(' ')
fun readIntegerList() = readStringList().map(Integer::parseInt)
fun readLongList() = readStringList().map(java.lang.Long::parseLong)
fun readDoubleList() = readStringList().map(java.lang.Double::parseDouble)
