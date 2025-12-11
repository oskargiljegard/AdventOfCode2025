package day11

import utils.HashmapGraph
import java.io.File

data class PathInfo(val none: Int, val dac: Int, val fft: Int, val dacNfft: Int) {}

fun main() {
    val lines = File("src/day11/input-mini2.txt").readLines()
    println(lines)

    val graph = HashmapGraph<String>(
        lines.associate { l ->
            val (from, tos) = l.split(": ")
            from to tos.split(" ")
        }
    )
    println(graph)

    val numPaths = graph.graph.values.flatten().toSet().associateWith { PathInfo(0, 0, 0, 0) }.toMutableMap()

    val visited = mutableSetOf<Pair<String, String>>()
    fun explore(node: String) {
        val (fromNone, fromDac, fromFft, fromDacNfft) = numPaths[node] ?: error("Node $node not found")
        for (dest in graph.getNeighbors(node)) {
            if (visited.contains(node to dest))
                continue
            visited.add(node to dest)
            var (destNone, destDac, destFft, destDacNfft) = numPaths[dest] ?: error("Node $dest not found")
            when (dest) {
                "fft" -> {
                    //destNone += fromNone
                    //destDac += fromDac
                    destFft += fromFft + fromNone
                    destDacNfft += fromDacNfft + fromDac
                }
                "dac" -> {
                    //destNone += fromNone
                    destDac += fromDac + fromNone
                    //destFft += fromFft
                    destDacNfft += fromDacNfft + fromFft
                }
                else -> {
                    destNone += fromNone
                    destDac += fromDac
                    destFft += fromFft
                    destDacNfft += fromDacNfft
                }
            }
            numPaths[dest] = PathInfo(destNone, destDac, destFft, destDacNfft)
            explore(dest)
        }
    }
    println("starting explore")
    numPaths["svr"] = PathInfo(1, 0, 0, 0)
    explore("svr")
    println("printing paths")
    println(numPaths)
    println(numPaths["out"])
}