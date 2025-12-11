package day11

import utils.HashmapGraph
import java.io.File

data class PathInfo(val none: Int, val dac: Int, val fft: Int, val dacNfft: Int) {}

fun main() {
    val lines = File("src/day11/input-mini.txt").readLines()
    println(lines)

    val graph = HashmapGraph<String>(
        lines.associate { l ->
            val (from, tos) = l.split(": ")
            from to tos.split(" ")
        }
    )
    println(graph)

    val numPaths = graph.graph.values.flatten().toSet().associateWith { PathInfo(0, 0, 0, 0) }.toMutableMap()

    fun explore(node: String) {
        val (fromNone, fromDac, fromFft, fromDacNfft) = numPaths[node] ?: error("Node $node not found")
        for (dest in graph.getNeighbors(node)) {
            val (destNone, destDac, destFft, destDacNfft) = numPaths[dest] ?: error("Node $dest not found")
            when (dest) {
                /*
                "dac" -> {
                    numPaths[dest] = PathInfo(destNone, fromNone + fromDac + destDac, destFft, fromFft + fromDacNfft + destDacNfft)
                }
                "fft" -> {
                    numPaths[dest] = PathInfo(destNone, destDac, fromNone + fromFft + destFft, fromDac + fromDacNfft + destDacNfft)
                }
                 */
                else -> {
                    numPaths[dest] = PathInfo(fromNone + destNone, fromDac + destDac, fromFft + destFft, fromDacNfft + destDacNfft)
                }
            }
            explore(dest)
        }
    }
    println("starting explore")
    numPaths["you"] = PathInfo(1, 0, 0, 0)
    explore("you")
    println("printing paths")
    println(numPaths)
    println(numPaths["out"])
}