package day07

import utils.Vector
import utils.asCharGrid
import java.io.File


fun main() {
    val grid = File("src/day07/input.txt").readText().asCharGrid { it.toString() }
    println(grid)
    val start = grid.positions.filter { grid[it] == "S" }
    println(start)
    /*
    var splits = 0
    for (yPos in grid.yPositions) {
        if (yPos == 0) continue
        for (xPos in grid.xPositions) {
            val pos = Vector(xPos, yPos)
            val curr = grid[pos]
            val above = grid[pos + Vector(0, -1)]
            if (above == 'S' || above == '|') {
                if (curr == '.') {
                    grid[pos] = '|'
                }
                if (curr == '^') {
                    splits++
                    grid[pos + Vector(-1, 0)] = '|'
                    grid[pos + Vector(1, 0)] = '|'
                }
            }
        }
    }
    println(grid)
    println(splits)
     */
    var splits = 0
    for (yPos in grid.yPositions) {
        if (yPos == 0) continue
        for (xPos in grid.xPositions) {
            val pos = Vector(xPos, yPos)
            val curr = grid[pos]
            val above = grid[pos + Vector(0, -1)]
            if (above == "S" || above == "|" || above.contains(Regex("\\d"))) {
                val aboveVal = if (above == "|" || above == "S") 1 else above.toLong()
                if (curr == ".") {
                    grid[pos] = aboveVal.toString()
                }
                if (curr == "^") {
                    splits++
                    val leftPos = pos + Vector(-1, 0)
                    if (grid[leftPos] == ".") {
                        grid[leftPos] = aboveVal.toString()
                    } else if (grid[leftPos].contains(Regex("\\d"))) {
                        grid[leftPos] = (grid[leftPos].toLong() + aboveVal).toString()
                    }
                    val rightPos = pos + Vector(1, 0)
                    if (grid[rightPos] == ".") {
                        grid[rightPos] = aboveVal.toString()
                    } else if (grid[rightPos].contains(Regex("\\d"))) {
                        grid[rightPos] = (grid[rightPos].toLong() + aboveVal).toString()
                    }
                }
                if (curr.contains(Regex("\\d"))) {
                    println(curr)
                    grid[pos] = (curr.toLong()+aboveVal).toString()
                }
            }
        }
    }
    println(grid)
    println(splits)
    println(grid.xPositions.map { Vector(it, grid.maxPos.y) }.filter { grid[it].contains(Regex("\\d")) }.map { grid[it].toLong() }.sum())
}
