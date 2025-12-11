package day11

import utils.HashmapGraph
import java.io.File

data class PathInfo(val none: Int, val dac: Int, val fft: Int, val dacNfft: Int) {}

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

    val numPaths = graph.graph.values.flatten().toSet().associateWith { PathInfo(0, 0, 0, 0) }.toMutableMap()

    fun explore(node: String, fft: Boolean=false, dac: Boolean=false) {
        val (fromNone, fromDac, fromFft, fromDacNfft) = numPaths[node] ?: error("Node $node not found")
        for (dest in graph.getNeighbors(node)) {
            val (destNone, destDac, destFft, destDacNfft) = numPaths[dest] ?: error("Node $dest not found")
            if (fft && dac) {
                numPaths[dest] = PathInfo(destNone, destDac, destFft, destDacNfft + 1)
            } else if (fft) {
                numPaths[dest] = PathInfo(destNone, destDac, destFft + 1, destDacNfft)
            } else if (dac) {
                numPaths[dest] = PathInfo(destNone, destDac + 1, destFft, destDacNfft)
            } else {
                numPaths[dest] = PathInfo(destNone + 1, destDac, destFft, destDacNfft)
            }
            explore(dest, fft || dest == "fft", dac || dest == "dac")
        }
    }
    println("starting explore")
    numPaths["svr"] = PathInfo(1, 0, 0, 0)
    explore("svr")
    println("printing paths")
    println(numPaths)
    println(numPaths["out"])
}