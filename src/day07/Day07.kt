package day07

import utils.Vector
import utils.asCharGrid
import java.io.File

data class Point(var mul: Long, val isSplit: Boolean) {
    override fun toString(): String {
        return "$mul "
    }
}

fun main() {
    val grid = File("src/day07/input.txt").readText().asCharGrid {
        return@asCharGrid when (it) {
            'S' -> Point(1L, false)
            '.' -> Point(0L, false)
            '^' -> Point(0L, true)
            else -> error("Invalid input")
        }
    }

    var splits = 0
    for (yPos in grid.yPositions.drop(1)) {
        for (xPos in grid.xPositions) {
            val pos = Vector(xPos, yPos)
            val above = grid[pos + Vector(0, -1)]
            if (above.mul > 0) {
                if (grid[pos].isSplit) {
                    splits++
                    grid[pos + Vector(-1, 0)].mul += above.mul
                    grid[pos + Vector(1, 0)].mul += above.mul
                } else {
                    grid[pos].mul += above.mul
                }

            }
        }
    }
    println(splits)
    println(grid.xPositions.map { Vector(it, grid.maxPos.y) }.sumOf { grid[it].mul })
}
