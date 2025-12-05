package day05

import java.io.File

fun main() {
    val lines = File("src/day05/input.txt").readText()
    val ranges = lines.split("\n\n")[0].split("\n").map { line ->
        val (start, end) = line.split("-")
        return@map Pair(start.toLong(), end.toLong())
    }
    val ids = lines.split("\n\n")[1].split("\n").map { line -> line.toLong()}
    /*
    ids.count { id -> ranges.any { range -> id in range.first..range.second } }.also { println(it) }
     */

    val remainingRanges = mutableListOf<Pair<Long, Long>>()
    remainingRanges.addAll(ranges.sortedBy { it.first })
    val firstRange = remainingRanges.removeAt(0)
    val mergedRanges = mutableListOf<Pair<Long, Long>>()
    mergedRanges.add(firstRange)

    while (remainingRanges.isNotEmpty()) {
        val curr = remainingRanges.removeAt(0)
        val lastMerged = mergedRanges.last()
        if (curr.first <= lastMerged.second + 1) {
            mergedRanges[mergedRanges.size - 1] = Pair(lastMerged.first, maxOf(lastMerged.second, curr.second))
        } else {
            mergedRanges.add(curr)
        }
    }

    val allIds = mergedRanges.sumOf { it.second - it.first + 1 }
    println(allIds)

}
