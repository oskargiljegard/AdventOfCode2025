package day06

import utils.split
import utils.transpose
import java.io.File

data class Problem(val op: Char, val grid: List<List<Char>>)

fun main() {
    val lines = File("src/day06/input.txt").readLines()
    val maxLength = lines.maxOf { it.length }
    val fullGrid = lines.map { it.padEnd(maxLength).toList() }
    val problems = fullGrid.splitOnEmptyColumns().map { chunkGrid ->
        val op = chunkGrid.last().first()
        val grid = chunkGrid.dropLast(1)
        Problem(op, grid)
    }
    println(problems.sumOf { it.solve() })
    println(problems.map { p -> p.copy(grid = p.grid.transpose()) }.sumOf { it.solve() })
}

fun Problem.solve(): Long {
    val nums = grid.map { row -> row.filter { it != ' ' }.joinToString("").toLong() }
    return when (op) {
        '+' -> nums.sum()
        '*' -> nums.reduce { a, b -> a * b }
        else -> throw IllegalArgumentException("Unknown op: $op")
    }
}

fun List<List<Char>>.splitOnEmptyColumns() = transpose()
    .split { row -> row.all { c -> c == ' ' } }
    .map { it.transpose() }


