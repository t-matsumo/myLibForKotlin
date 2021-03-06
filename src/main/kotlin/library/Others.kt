package library

fun sumIntFromOneTo(last: Int) = last * (last + 1) / 2

fun calcMaxIndex(list: List<Int>) = list.indices.maxBy { list[it] }!!

fun calcExpectedValueForDice(maxValue: Int) = (1 + maxValue) / 2.toDouble()
