package library

/** O(log(n)): n = min(a, b) */
tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

/** O(log(n)): n = min(a, b) */
fun lcm(a: Long, b: Long)= a / gcd(a, b) * b