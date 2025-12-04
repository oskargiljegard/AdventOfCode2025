package day04

import utils.Grid
import utils.Vector
import utils.asCharGrid
import utils.gridOf
import java.io.File

fun main() {
    val lines = File("src/day04/input.txt").readLines()
    println(lines)
    val grid = lines.joinToString("\n").asCharGrid { it }
    val deltas = listOf(
        Vector(-1, -1),
        Vector(-1, 0),
        Vector(-1, 1),
        Vector(0, -1),
        Vector(0, 1),
        Vector(1, -1),
        Vector(1, 0),
        Vector(1, 1)
    )
    //val grid2 = lines.joinToString("\n").asCharGrid { it }
    /*
    var sum = 0
    for (p in grid.positions) {
        val n = deltas.map { (it + p) }.filter { it.isWithin(grid) }
        if (n.filter { grid[it] == '@' }.count() < 4 && grid[p] ==  '@' ) {
            println(p)
            sum++;
            grid2[p] = 'x'
        }
    }
    println(sum)
    //println(grid2.toStringIndexed { pos, ch -> "$ch" })
     */
    var sum = 0
    var removedAny = false
    while (true) {
        removedAny = false
        for (p in grid.positions) {
            val n = deltas.map { (it + p) }.filter { it.isWithin(grid) }
            if (n.filter { grid[it] == '@' }.count() < 4 && grid[p] == '@') {
                grid[p] = '.'
                sum++;
                removedAny = true
            }
        }
        if (!removedAny) {
            break
        }
    }
    println(sum)

}

fun Vector.isWithin(grid: Grid<Char>): Boolean {
    if (x >= grid.size.x) return false
    if (y >= grid.size.y) return false
    if (x < 0) return false
    if (y < 0) return false
    return true
}
