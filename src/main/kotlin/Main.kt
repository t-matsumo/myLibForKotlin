import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.util.*
import kotlin.math.*

val br = BufferedReader(InputStreamReader(System.`in`))
val out = PrintWriter(System.out)

fun main() {
    br.use { out.use {
        solve()
        out.flush()
    } }
}

fun solve() {
}

fun readInteger() = Integer.parseInt(br.readLine())
fun readLong() = java.lang.Long.parseLong(br.readLine())
fun readStringList() = br.readLine()!!.split(' ')
fun readIntegerList() = readStringList().map(Integer::parseInt)
fun readLongList() = readStringList().map(java.lang.Long::parseLong)
fun readDoubleList() = readStringList().map(java.lang.Double::parseDouble)
