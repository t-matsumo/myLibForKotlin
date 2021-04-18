import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

val br = BufferedReader(InputStreamReader(System.`in`))
val out = PrintWriter(System.out)

fun main() {
    br.use {
        out.use {
//            repeat(readInteger()) { solve() }
            solve()
            out.flush()
        }
    }
}

fun solve() {
}

fun readLine() = br.readLine()!!
fun readInteger() = Integer.parseInt(br.readLine(), 10)
fun readLong() = java.lang.Long.parseLong(br.readLine(), 10)
fun readStringList() = br.readLine()!!.split(" ")
fun readIntegerList() = br.readLine()!!.split(" ").map { Integer.parseInt(it, 10) }
fun readLongList() = br.readLine()!!.split(" ").map { java.lang.Long.parseLong(it, 10) }
fun readDoubleList() = br.readLine()!!.split(" ").map { java.lang.Double.parseDouble(it) }
