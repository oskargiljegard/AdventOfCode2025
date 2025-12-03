package day03

import java.io.File

fun main() {
    val lines = File("src/day03/input.txt").readLines()
    lines.sumOf { findJolt(it, 2).toLong() }.also { println(it) }
    lines.sumOf { findJolt(it, 12).toLong() }.also { println(it) }
}

private fun findJolt(line: String, len: Int): String {
    val joltValues = mutableListOf<Int>()
    var remInts = line.map { it.toString().toInt() }
    var numJoltsLeft = len
    while (numJoltsLeft > 0) {
        val joltWindow = remInts.dropLast(numJoltsLeft - 1)
        val (idx, max) = joltWindow.withIndex().maxBy { (i, v) -> v }
        joltValues.add(max)
        remInts = remInts.drop(idx + 1)
        numJoltsLeft--
    }
    return joltValues.joinToString("")
}