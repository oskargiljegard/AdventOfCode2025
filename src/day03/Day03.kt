package day03

import java.io.File

fun main() {
    val lines = File("src/day03/input.txt").readLines()
    /*
    var sum = 0
    for (line in lines) {
        val ints = line.map { it.toString().toInt() }
        val max = ints.dropLast(1).maxOrNull()!!
        val maxIdx = ints.indexOf(max)
        val nextMax = ints.drop(maxIdx + 1).maxOrNull()!!
        println("$max$nextMax")
        sum += "$max$nextMax".toInt()
    }
    println(sum)
     */
    var sum = 0L
    for (line in lines) {
        val x = findJolt(line, 12).toLong()
        sum += x
        println(x)
    }
    println(sum)
}

private fun findJolt(line: String, len: Int): String {
    val ints = line.map { it.toString().toInt() }
    val maxNums = mutableListOf<Int>()
    var remainingInts = ints
    var remainingLen = len
    while (remainingLen > 0) {
        val max = remainingInts.dropLast(remainingLen - 1).maxOrNull()!!
        maxNums.add(max)
        val maxIdx = remainingInts.indexOf(max)
        remainingInts = remainingInts.drop(maxIdx + 1)
        remainingLen--
    }
    return maxNums.joinToString("")
}