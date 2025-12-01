package day01

import java.io.File

fun main() {
    val lines = File("src/day01/input.txt").readLines()

    // Part 1
    var zeroTimes = 0
    var curr = 50
    for (line in lines) {
        val firstLetter = line.first()
        val rest = line.substring(1).toInt()
        when (firstLetter) {
            'L' -> curr = (curr - rest) % 100
            'R' -> curr = (curr + rest) % 100
            else -> throw IllegalArgumentException("Unknown letter '$firstLetter'")
        }
        if (curr == 0) {
            zeroTimes++
        }
    }
    println(zeroTimes)

    // Part 2
    zeroTimes = 0
    curr = 50
    val linesExpanded = lines.flatMap { line ->
        val firstLetter = line.first()
        val rest = line.substring(1).toInt()
        return@flatMap (0 until rest).map { "${firstLetter}1" }
    }
    for (line in linesExpanded) {
        val firstLetter = line.first()
        val rest = line.substring(1).toInt()
        when (firstLetter) {
            'L' -> curr = (curr - rest) % 100
            'R' -> curr = (curr + rest) % 100
            else -> throw IllegalArgumentException("Unknown letter '$firstLetter'")
        }
        if (curr == 0) {
            zeroTimes++
        }
    }
    println(zeroTimes)
}
