import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter

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
fun readInteger() = Integer.parseInt(br.readLine())
fun readLong() = java.lang.Long.parseLong(br.readLine())
fun readStringList() = readLine().split(' ')
fun readIntegerList() = readStringList().map(String::toInt)
fun readLongList() = readStringList().map(String::toLong)
fun readDoubleList() = readStringList().map(String::toDouble)
