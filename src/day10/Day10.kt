package day10

import utils.HashmapGraph
import utils.aStarShortestPath
import java.io.File

fun main() {
    val lines = File("src/day10/input-mini.txt").readLines()
    println(lines)

    val goals = lines.map { l -> l.split(" ")[0].drop(1).dropLast(1).toList() }
    println(goals)

    val buttonsList = lines.map { l ->
        l.split(" ").drop(1).dropLast(1).map { b -> b.drop(1).dropLast(1).split(",").map { it.toInt() } }
    }
    println(buttonsList)

    val statesList = goals.zip(buttonsList).map { (g, btns) ->
        val visited = mutableSetOf<List<Char>>()
        fun explore(state: List<Char>) {
            if (visited.contains(state)) return
            visited.add(state)
            for (b in btns) {
                val newState = press(state, b)
                explore(newState)
            }
        }
        explore(g)
        visited
    }
    println(statesList.map { it.size })

    val graphs = buttonsList.zip(statesList).map { (btns, states) ->
        val graph =
            states.associate { s ->
                s to btns.map { b ->
                    press(s, b)
                }
            }
        HashmapGraph<List<Char>>(
            graph
        )
    }
    val shortest = graphs.zip(goals).map { (graph, goal) ->
        graph.aStarShortestPath(
            List(goal.size) { '.' },
            goal,
            calculateDistance = { a, b -> 1.0 },
            estimateDistanceToEnd = { a -> 9999999.0 }
        )!!.size
    }
    println(shortest)
    println(shortest.sum())
}

fun press(state: List<Char>, btn: List<Int>): List<Char> {
    val newState = state.toMutableList()
    for (num in btn) {
        when (newState[num]) {
            '.' -> newState[num] = '#'
            '#' -> newState[num] = '.'
            else -> error("Invalid state")
        }
    }
    return newState
}

fun press2(state: List<Int>, btn: List<Int>): List<Int> {
    val newState = state.toMutableList()
    for (num in btn) {
        newState[num]++
    }
    return newState
}