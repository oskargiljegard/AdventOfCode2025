package day06

import java.io.File

fun main() {
    val lines = File("src/day06/input.txt").readLines()
    println(lines)
    val symbols = lines.last().split(" ").filter { it.isNotEmpty() }
    println(symbols)
    val numbersLines = lines.dropLast(1).map { it.padEnd(lines.maxOf { it.length }, ' ') }
    val numbers = mutableListOf<MutableList<Long>>()
    var currentColumn = mutableListOf<Long>()
    for (i in 0 until numbersLines[0].length) {
        if (numbersLines.all { it[i] == ' ' }) {
            numbers.add(currentColumn)
            currentColumn = mutableListOf()
        } else {
            val chars = numbersLines.map { it[i] }.filter { it != ' ' }
            val number = chars.joinToString("").toLong()
            currentColumn.add(number)
        }
    }
    numbers.add(currentColumn)
    println(numbers)
    val results = symbols.mapIndexed { i, s ->
        val numberList = numbers[i]
        return@mapIndexed when (s) {
            "+" -> numberList.sum()
            "*" -> numberList.reduce { acc, n -> acc * n }
            else -> throw IllegalArgumentException("Unknown symbol: $s")
        }
    }
    println(results)
    println(results.sum())
    /*
    val numbers = lines.dropLast(1).map { it.split(" ").filter { it.isNotEmpty() }.map { it.toLong() } }
    println(numbers)
    val results = symbols.mapIndexed { i, s ->
        val numberList = numbers.map { it[i] }
        return@mapIndexed when (s) {
            "+" -> numberList.sum()
            "*" -> numberList.reduce { acc, n -> acc * n }
            else -> throw IllegalArgumentException("Unknown symbol: $s")
        }
    }
    println(results)
    println(results.sum())

     */
}