package day08

import java.io.File

fun main() {
    val lines = File("src/day08/input.txt").readLines()

    val boxes = lines.map { line ->
        val (x, y, z) = line.split(",")
        Triple(x.toLong(), y.toLong(), z.toLong())
    }
    println(boxes)

    val boxPairs =
        boxes.flatMapIndexed { i1, b1 ->
            boxes.flatMapIndexed { i2, b2 ->
                if (i1 <= i2) emptyList() else listOf(
                    Pair(
                        b1,
                        b2
                    )
                )
            }
        }
            .sortedBy { (b1, b2) ->
                val dx = b1.first - b2.first
                val dy = b1.second - b2.second
                val dz = b1.third - b2.third
                dx * dx + dy * dy + dz * dz
            }
    println(boxPairs)
    //val connections = boxPairs.take(1000)
    val connections = boxPairs.toMutableList()
    fun getNeighbors(from: Triple<Long, Long, Long>): List<Triple<Long, Long, Long>> =
        connections.flatMap { (b1, b2) ->
            when {
                b1 == from -> listOf(b2)
                b2 == from -> listOf(b1)
                else -> emptyList()
            }
        }

    val unconnected = boxes.toSet().toMutableSet()
    val connected = mutableSetOf<Triple<Long, Long, Long>>()
    while (unconnected.isNotEmpty()) {
        val (b1, b2) = connections.removeFirst()
        if (b1 in connected && b2 in connected) continue
        if (b1 in unconnected) {
            unconnected.remove(b1)
            connected.add(b1)
        }
        if (b2 in unconnected) {
            unconnected.remove(b2)
            connected.add(b2)
        }
        if (unconnected.isEmpty()) {
            println(b1)
            println(b2)
            println(b1.first*b2.first)
        }
    }

    /*
    val visited = mutableSetOf<Triple<Long, Long, Long>>()
    fun spread(from: Triple<Long, Long, Long>): Set<Triple<Long, Long, Long>> {
        if (from in visited) return setOf(from)
        visited.add(from)
        return setOf(from) + getNeighbors(from).map { n -> spread(n) }.flatten().toSet()
    }
    val circuits = mutableListOf<Set<Triple<Long, Long, Long>>>()
    for (box in boxes) {
        if (box in visited) continue
        circuits.add(spread(box))
    }
    //println(circuits.sortedByDescending { it.size }.map { it.size })
    println(circuits.sortedByDescending { it.size }.map { it.size }.take(3).reduce { a, b -> a * b })

     */
}
