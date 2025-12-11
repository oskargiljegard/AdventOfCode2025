package day11

import utils.Graph
import utils.HashmapGraph
import utils.aStarShortestPath
import java.io.File

fun main() {
    val lines = File("src/day11/input.txt").readLines()
    println(lines)

    val graph = HashmapGraph<String>(
        lines.associate { l ->
            val (from, tos) = l.split(": ")
            from to tos.split(" ")
        }
    )
    println(graph)

    val numPaths = graph.graph.keys.associateWith { 0 }.toMutableMap()

    fun explore(node: String) {
        numPaths[node] = numPaths.getOrDefault(node, 0) + 1
        for (n in graph.getNeighbors(node)) {
            explore(n)
        }
    }
    println("starting explore")
    explore("you")
    println("printing paths")
    println(numPaths["out"])
}