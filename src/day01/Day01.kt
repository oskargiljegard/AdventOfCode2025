package day01

import java.io.File

fun main() {
    val lines = File("src/day01/input.txt").readLines()
    println(lines)
    var zeroTimes = 0
    var curr = 50
    /*
    for (line in lines) {
        val firstLetter = line.first()
        val rest = line.substring(1).toInt()
        /*
        when (firstLetter) {
            'L' -> curr = (curr - rest) % 100
            'R' -> curr = (curr + rest) % 100
            else -> throw IllegalArgumentException("Unknown letter '$firstLetter'")
        }
        if (curr == 0) {
            zeroTimes++
        }

        // Part 1: 19, 20, 1147
         */
        /*
        when (firstLetter) {
            'L' -> curr = (curr - rest)
            'R' -> curr = (curr + rest)
            else -> throw IllegalArgumentException("Unknown letter '$firstLetter'")
        }
        if (curr == 0) {
            zeroTimes++
        }
        while (curr < 0) {
            curr += 100
            zeroTimes++
        }
        while (curr >= 100) {
            curr -= 100
            zeroTimes++
        }
         */
    }
     */

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
        // Part 2: 6796, 7273, 6701 (off by 1 in expanded loop), 6789
    }
    println(zeroTimes)
}
