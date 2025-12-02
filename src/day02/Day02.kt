package day02

import java.io.File

fun main() {
    val lines = File("src/day02/input.txt").readLines()

    val ranges = lines[0].split(",").map { r ->
        val (from, to) = r.split("-").map { it.toLong() }
        from until (to + 1)
    }
    var sum = 0L
    for (r in ranges) {
        for (num in r) {
            if (isInvalid2(num)) {
                println(num)
                sum += num
            }
        }
    }
    println(sum)
    // 35367539282
    // 45814076230
}

private fun isInvalid2(num: Long): Boolean {
    val str = num.toString()
    for (i in 1 .. str.length / 2) {
        if (str.length % i != 0) continue
        val chunks = str.chunked(i)
        if (chunks.all { it == chunks[0] }) return true
    }
    return false
}

private fun isInvalid(num: Long): Boolean {
    val str = num.toString()
    if (str.length % 2 != 0) return false
    val first = str.take(str.length / 2)
    val last = str.takeLast(str.length / 2)
    return first == last
}
