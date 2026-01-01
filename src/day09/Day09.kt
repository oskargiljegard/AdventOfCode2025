package day09

import utils.Vector
import java.io.File
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.max

data class Candiate(val c1: Vector, val c2: Vector, val i1: Int, val i2: Int)

enum class Dir {
    UP, RIGHT, DOWN, LEFT
}

fun Dir.turnRight(): Dir {
    when (this) {
        Dir.UP -> return Dir.RIGHT
        Dir.RIGHT -> return Dir.DOWN
        Dir.DOWN -> return Dir.LEFT
        Dir.LEFT -> return Dir.UP
    }
}

fun Dir.turnLeft(): Dir {
    when (this) {
        Dir.RIGHT -> return Dir.UP
        Dir.UP -> return Dir.LEFT
        Dir.LEFT -> return Dir.DOWN
        Dir.DOWN -> return Dir.RIGHT
    }
}


fun main() {
    val lines = File("src/day09/input-mini.txt").readLines()
    val tiles = lines.map { line ->
        val (x, y) = line.split(",")
        Vector(x.toDouble(), y.toDouble())
    }
    val pairs =
        tiles.flatMapIndexed { i1, t1 ->
            tiles.flatMapIndexed { i2, t2 ->
                if (i1 < i2) listOf(
                    Candiate(
                        t1,
                        t2,
                        i1,
                        i2
                    )
                ) else listOf()
            }
        }
    val max = pairs.maxOf { (a, b) ->
        abs(a.x.toLong() - b.x.toLong() + 1) * abs(a.y.toLong() - b.y.toLong() + 1)
    }
    println(max)

    println("Num pairs: ${pairs.size}")

    val tilesWithNext = (tiles + listOf(tiles.first())).zipWithNext()

    val leftMostTileIndex = tilesWithNext
        .zip(0..<tilesWithNext.size)
        .filter { (ts, i) -> ts.first.intX == ts.second.intX }
        .minBy { (ts, i) -> ts.first.intX }
        .second


    val tilesWithNextInRightOrder = tilesWithNext.subList(leftMostTileIndex, tilesWithNext.size) + tilesWithNext.subList(0, leftMostTileIndex)
    val linesWithDirs = mutableListOf<Triple<Vector, Vector, Dir>>()
    var lastTs: Pair<Vector, Vector>? = null
    var lastDir: Dir? = null
    for ((t1, t2) in tilesWithNextInRightOrder) {
        var dir: Dir
        if (lastDir == null) {
            dir = Dir.LEFT
        } else {
            val didTurnRight = didTurnRight(lastTs!!.first, t1, t2)
            dir = if (didTurnRight) lastDir.turnRight() else lastDir.turnLeft()
        }

        linesWithDirs.add(Triple(t1, t2, dir))

        lastDir = dir
        lastTs = t1 to t2
    }

    val validPairs = pairs.filter { (c1, c2, i1, i2) ->
        val maxX = max(c1.intX, c2.intX)
        val maxY = max(c1.intY, c2.intY)
        val minX = min(c1.intX, c2.intX)
        val minY = min(c1.intY, c2.intY)

        return@filter linesWithDirs.all { (t1, t2, dirToOutside) -> isValid(c1, c2, t1, t2, dirToOutside) }
    }
    println("Num valid pairs: ${validPairs.size}")


    val validMax = validPairs.maxBy { (a, b) ->
        abs(a.x.toLong() - b.x.toLong() + 1) * abs(a.y.toLong() - b.y.toLong() + 1)
    }
    println(validMax)

    // too low 1539204402
}

fun getSlice(tiles: List<Vector>, from: Int, to: Int): List<Vector> {
    if (from == to) return listOf(tiles[from])
    if (from < to) return tiles.subList(from, to)

    return tiles.subList(from, tiles.size) + tiles.subList(0, to)
}

fun isValid(c1: Vector, c2: Vector, p1: Vector, p2: Vector, dirToOutside: Dir): Boolean {
    if (p1.intY != p2.intY) {
        if (p1.intX != p2.intX) error("Bad points")
        val newDir = when (dirToOutside) {
            Dir.LEFT -> Dir.UP
            Dir.RIGHT -> Dir.DOWN
            else -> error("Bad dir")
        }
        return isValid(c1.swapped(), c2.swapped(), p1.swapped(), p2.swapped(), newDir)
    }

    val maxX = max(c1.intX, c2.intX)
    val maxY = max(c1.intY, c2.intY)
    val minX = min(c1.intX, c2.intX)
    val minY = min(c1.intY, c2.intY)

    val y = p1.intY

    if (p1.intX >= maxX && p2.intX >= maxX) return true

    if (p1.intX <= minX && p2.intX <= minX) return true

    if (y == minY) {
        // dir to outside may not be down
        if (dirToOutside == Dir.DOWN) return false
    }
    if (y == maxY) {
        // dir to outside may not be up
        if (dirToOutside == Dir.UP) return false
    }

    if (y <= minY || y >= maxY) return true

    return false
}

fun didTurnRight(t0: Vector, t1: Vector, t2: Vector): Boolean {
    if (t0.intY != t1.intY) {
        if (t0.intX != t1.intX) error("Bad points")
        return !didTurnRight(t0.swapped(), t1.swapped(), t2.swapped())
    }
    if (t0.intX > t1.intX) return !didTurnRight(t0.copy(x=-t0.x), t1.copy(x=-t1.x), t2.copy(x=-t2.x))
    if (t1.intY == t2.intY) error("Bad points, extra long line in Y")
    if (t1.intX != t2.intX) error("Bad points, extra long line in X")

    if (t1.intY < t2.intY) return true
    return false
}

/*
..............
.......0...1..
..............
..6....7......
..............
..5......4....
..............
.........3.2..
..............



7,1
11,1
11,7
9,7
9,5
3,5
3,4
2,4
2,3
7,3
 */