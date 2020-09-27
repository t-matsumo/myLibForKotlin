package library

/** O(log(n)): n = min(a, b) */
tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

/** O(log(n)): n = min(a, b) */
fun lcm(a: Long, b: Long)= a / gcd(a, b) * b

/**
 * O(log(n)): n = min(a, b)
 * @return Triple<gcd(a, b), x, y> : satisfy "a * x + b * y = gcd(a, b)", |x| <= b, |y| <= a
 */
fun extGcd(a: Long, b: Long): Triple<Long, Long, Long> = if (b != 0L) {
        extGcd(b, a % b).let { (d, y, x) -> Triple(d, x, y - (a / b) * x) }
    } else {
        Triple(a, 1, 0)
    }