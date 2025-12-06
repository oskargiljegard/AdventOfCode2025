package day05

import utils.split
import java.io.File

fun main() {
    val (rLines, iLines) = File("src/day05/input.txt").readLines().split("")
    val ranges = rLines.map { line ->
        val (start, end) = line.split("-")
        return@map Pair(start.toLong(), end.toLong())
    }
    val ids = iLines.map { it.toLong() }
    println(ids.count { id -> ranges.any { range -> id in range.first..range.second } })

    val remainingRanges = ranges.sortedBy { it.first }.toMutableList()
    val mergedRanges = mutableListOf<Pair<Long, Long>>()
    val firstRange = remainingRanges.removeAt(0)
    mergedRanges.add(firstRange)
    for (curr in remainingRanges) {
        val lastMerged = mergedRanges.last()
        if (curr.first <= lastMerged.second + 1) {
            mergedRanges[mergedRanges.size - 1] = Pair(lastMerged.first, maxOf(lastMerged.second, curr.second))
        } else {
            mergedRanges.add(curr)
        }
    }

    println(mergedRanges.sumOf { it.second - it.first + 1 })
}
