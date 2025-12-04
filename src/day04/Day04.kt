package day04

import utils.Grid
import utils.Vector
import utils.asCharGrid
import utils.isWithin
import utils.moore
import utils.neighbours
import java.io.File

fun main() {
    val lines = File("src/day04/input.txt").readText()
    val grid = lines.asCharGrid { it }

    println(grid.positions.count { grid.canMove(it) })

    var sum = 0
    while (true) {
        val moveable = grid.positions.filter { grid.canMove(it) }
        if (moveable.isEmpty()) break
        sum += moveable.size
        for (p in moveable) {
            grid[p] = '.'
        }
    }
    println(sum)
}

private fun Grid<Char>.canMove(p: Vector): Boolean {
    if (this[p] != '@') return false
    val numNeighbours = p.neighbours(moore)
        .filter { it.isWithin(this) }
        .count { this[it] == '@' }
    return numNeighbours < 4
}
