package day09

import utils.Vector
import java.io.File
import kotlin.math.abs

fun main() {
    val fileLines = File("src/day09/input.txt").readLines()
    val tiles = fileLines.map { line ->
        val (x, y) = line.split(",")
        Vector(x.toDouble(), y.toDouble())
    }
    val pairs =
        tiles.flatMapIndexed { i1, t1 ->
            tiles.flatMapIndexed { i2, t2 ->
                if (i1 < i2) listOf(
                    t1 to t2
                ) else listOf()
            }
        }.sortedByDescending { (a, b) -> area(a, b) }
    println("Part 1: ${area(pairs.first().first, pairs.first().second)}")

    println("Num pairs: ${pairs.size}")

    // too low 1539204402

    val lines = (tiles + tiles[0]).zipWithNext().map { (a, b) -> fixLine(a, b) }
    val hLines = lines.filter { (a, b) -> a.intY == b.intY }
    val vLines = lines.filter { (a, b) -> a.intX == b.intX }

    val maxValid = pairs.first { (c1, c3) ->
        if (c1.x == c3.x || c1.y == c3.y) return@first false
        val tl = Vector(minOf(c1.x, c3.x), minOf(c1.y, c3.y))
        val br = Vector(maxOf(c1.x, c3.x), maxOf(c1.y, c3.y))

        val tli = tl + Vector(1, 1)
        val bri = br - Vector(1, 1)
        val tri = Vector(bri.x, tli.y)
        val bli = Vector(tli.x, bri.y)
        val hEdges = listOf(tli to tri, bli to bri)
        val vEdges = listOf(tli to bli, tri to bri)

        if (vLines.any { (la, lb) -> hEdges.any { (ea, eb) -> intersects(ea, eb, la, lb) } }) return@first false
        if (hLines.any { (la, lb) -> vEdges.any { (ea, eb) -> intersects(la, lb, ea, eb) } }) return@first false

        return@first true
    }
    println("Part 2: ${area(maxValid.first, maxValid.second)}")

}

fun area(c1: Vector, c2: Vector): Long {
    return abs(c1.x.toLong() - c2.x.toLong() + 1) * abs(c1.y.toLong() - c2.y.toLong() + 1)
}

fun fixLine(a: Vector, b: Vector): Pair<Vector, Vector> {
    return when {
        a.intX == b.intX && a.intY == b.intY -> error("Zero length line from $a to $b")
        a.intX == b.intX -> if (a.intY < b.intY) a to b else b to a
        a.intY == b.intY -> if (a.intX < b.intX) a to b else b to a
        else -> error("Non axis aligned line from $a to $b")
    }
}

// Requires the first line to be horizontal and the second to be vertical
fun intersects(a: Vector, b: Vector, c: Vector, d: Vector): Boolean {
    require(a.intY == b.intY)
    require(a.intX <= b.intX)

    require(c.intX == d.intX)
    require(c.intY <= d.intY)

    return a.intX < c.intX && c.intX < b.intX
            && c.intY < a.intY && a.intY < d.intY
}