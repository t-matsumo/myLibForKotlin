import java.io.PrintWriter

val br = System.`in`.bufferedReader()
val out = PrintWriter(System.out.bufferedWriter())

fun main() {
//    repeat(readInt()) { solve() }
    solve()
    out.flush()
}

fun solve() {
}

fun readLine() = br.readLine()!!
fun readInt() = readLine().toInt()
fun readLong() = readLine().toLong()
fun readStringList() = readLine().split(' ')
fun readIntList() = readStringList().map(String::toInt)
fun readLongList() = readStringList().map(String::toLong)