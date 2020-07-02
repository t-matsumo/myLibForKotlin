import java.io.PrintWriter

val br = System.`in`.bufferedReader()
val out = PrintWriter(System.out)

fun main() {
    br.use { out.use {
        solve()
        out.flush()
    } }
}

fun solve() {
    out.println(lcm(2, 6))
}

fun lcm(a: Long, b: Long): Long {
    tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

    return a / gcd(a, b) * b
}

fun readInteger() = Integer.parseInt(br.readLine())
fun readLong() = java.lang.Long.parseLong(br.readLine())
fun readStringList() = br.readLine()!!.split(' ')
fun readIntegerList() = readStringList().map(Integer::parseInt)
fun readLongList() = readStringList().map(java.lang.Long::parseLong)
fun readDoubleList() = readStringList().map(java.lang.Double::parseDouble)
