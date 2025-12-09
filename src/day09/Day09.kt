package day09

import utils.Grid
import utils.Vector
import utils.gridOf
import java.io.File
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

enum class Dir {
    H,
    V
}

val H = Dir.H
val V = Dir.V

data class Lazer(val pos: Vector, val dir: Dir, var length: Double)

data class Source(val pos: Vector, val lazers: List<Lazer>)

fun main() {
    val lines = File("src/day09/input-mini.txt").readLines()
    val tiles = lines.map { line ->
        val (x, y) = line.split(",")
        Vector(x.toDouble(), y.toDouble())
    }
    val pairs =
        tiles.flatMapIndexed { i1, t1 -> tiles.flatMapIndexed { i2, t2 -> if (i1 < i2) listOf(t1 to t2) else listOf() } }
    val max = pairs.maxOf { (a, b) ->
        abs(a.x.toLong() - b.x.toLong() + 1) * abs(a.y.toLong() - b.y.toLong() + 1)
    }
    println(max)

    fun intersects(t1: Vector, t2: Vector, from: Vector, to: Vector): Boolean {
        val mintx = min(t1.x, t2.x)
        val minty = min(t1.y, t2.y)
        val maxtx = max(t1.x, t2.x)
        val maxty = max(t1.y, t2.y)

        val minlx = min(from.x, to.x)
        val minly = min(from.y, to.y)
        val maxlx = max(from.x, to.x)
        val maxly = max(from.y, to.y)
        return when {
            from.x == to.x -> (to.x in mintx + 0.5..maxtx - 0.5) && (minly < maxty - 0.5 && maxly > minty + 0.5)
            from.y == to.y -> (to.y in minty + 0.5..maxty - 0.5) && (minlx < maxtx - 0.5 && maxlx > mintx + 0.5)
            else -> error("invalid")
        }
    }

    val path = (tiles + listOf(tiles.first())).zipWithNext()
    println(path)
    val validPairs = pairs.filter { (t1, t2) ->
        path.none { (from, to) ->
            // that intersect
            intersects(t1, t2, from, to)
        }
    }
    println(validPairs)

    val vmax = validPairs.maxBy { (a, b) ->
        abs(a.x.toLong() - b.x.toLong() + 1) * abs(a.y.toLong() - b.y.toLong() + 1)
    }
    println(vmax)
    // 1539204402 too low

    println(intersects(Vector(7, 1), Vector(11, 7), Vector(9, 7), Vector(9, 5)))

    println(intersects(Vector(9, 7), Vector(2, 3), Vector(9, 5), Vector(2, 5)))

    val grid = Grid(Vector(tiles.maxOf { it.x }+1, tiles.maxOf { it.y }+1)) {
        if (it in tiles) '#' else '.'
    }
    println(grid)

    /*

    // It can currently pick a box on the wrong side of the path. However since my answer was too low. I must be missing
    // a solution, I.e. also removing too much, not just too little

    // demo example goes around a loop starting right
    // real examples goes around down and then left

    val pairIndices =
        tiles.flatMapIndexed { i1, t1 -> tiles.flatMapIndexed { i2, t2 -> if (i1 < i2) listOf(i1 to i2) else listOf() } }

    val START_LENGTH = 999999.0
    val sources = tiles.map { t ->
        Source(
            t, listOf(
                Lazer(t, H, START_LENGTH),
                Lazer(t, H, -START_LENGTH),
                Lazer(t, V, START_LENGTH),
                Lazer(t, V, -START_LENGTH),
            )
        )
    }

    val path = (tiles + listOf(tiles.first())).zipWithNext().map { (a, b) ->
        when {
            a.x != b.x && a.y != b.y -> error("Not straight line")
            a.x != b.x -> Lazer(a, H, b.x - a.x)
            a.y != b.y -> Lazer(a, V, b.y - a.y)
            else -> error("Same position")
        }
    }
    println(path)

    // cut off lazers
    println(path[0] intersects path[1])

    for (step in path) {
        val possibleIntersections = sources.flatMap { it.lazers }.filter { it.dir != step.dir }
        for (lazer in possibleIntersections) {
            if (lazer intersects step) {
                when (lazer.dir) {
                    H -> lazer.length = step.pos.x - lazer.pos.x
                    V -> lazer.length = step.pos.y - lazer.pos.y
                    else -> {
                        error("Exhaustive")
                    }
                }
            }
        }
        assert(possibleIntersections.none { it intersects step })
    }
    println(sources)

     */
}

/*
infix fun Lazer.intersects(other: Lazer): Boolean {
    if (this.dir == other.dir) return false
    val (hl, vl) = when {
        this.dir == H && other.dir == V -> Pair(this, other)
        this.dir == V && other.dir == H -> Pair(other, this)
        else -> error("Lazers go same direction")
    }
    val hlMinX = min(hl.pos.x, hl.pos.x + hl.length)
    val hlMaxX = max(hl.pos.x, hl.pos.x + hl.length)
    val vlMinY = min(vl.pos.y, vl.pos.y + hl.length)
    val vlMaxY = max(vl.pos.y, vl.pos.y + hl.length)
    return (vl.pos.x in hlMinX + 0.5..hlMaxX - 0.5)
            && (hl.pos.y in vlMinY + 0.5..vlMaxY)
}
*/