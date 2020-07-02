package library

data class RunLength<T>(val value: T, val length: Int)

/** O(s.length) */
fun runLengthEncode(s: CharSequence): List<RunLength<Char>> {
    if (s.isEmpty()) return emptyList()

    val runLengthList = mutableListOf<RunLength<Char>>()
    var currentChar = s.first()
    var length = 1
    for (c in s.substring(1)) {
        if (c == currentChar) {
            length++
        } else {
            runLengthList += RunLength(currentChar, length)
            currentChar = c
            length = 1
        }
    }
    runLengthList += RunLength(currentChar, length)

    return runLengthList
}