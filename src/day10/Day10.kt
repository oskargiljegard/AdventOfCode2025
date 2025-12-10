package day10

import utils.Graph
import utils.HashmapGraph
import utils.aStarShortestPath
import java.io.File

fun main() {
    val lines = File("src/day10/input.txt").readLines()
    println(lines)

    //val goals = lines.map { l -> l.split(" ")[0].drop(1).dropLast(1).toList() }
    val goals = lines.map { l -> l.split(" ").last().drop(1).dropLast(1).split(",").map { it.toInt() } }
    println(goals)

    val buttonsList = lines.map { l ->
        l.split(" ").drop(1).dropLast(1).map { b -> b.drop(1).dropLast(1).split(",").map { it.toInt() } }
    }

    val graphs = goals.zip(buttonsList).map { (g, btns) ->
        object : Graph<List<Int>> {
            override fun getNeighbors(node: List<Int>): Collection<List<Int>> {
                return btns.filter { b -> !b.all { num -> node[num] >= g[num] } }.map { b -> press2(node, b) }
            }
        }
    }

    val shortest = graphs.zip(goals).map { (graph, goal) ->
        val size = graph.aStarShortestPath(
            List(goal.size) { 0 },
            goal,
            calculateDistance = { a, b -> 1.0 },
            estimateDistanceToEnd = { node ->
                val dists = node.mapIndexed { i, v -> node[i] - goal[i] }
                if (dists.any { it < 0 }) {
                    return@aStarShortestPath 99999999.0
                }
                return@aStarShortestPath dists.max().toDouble()
            }
        )!!.size
        println(size)
        return@map size
    }
    println(shortest)
    println(shortest.sum())

    /*
    val statesList = goals.zip(buttonsList).map { (g, btns) ->
        val visited = mutableSetOf<List<Int>>()
        fun explore(state: List<Int>) {
            if (visited.contains(state)) return
            visited.add(state)
            for (b in btns) {
                if (b.all { num -> state[num] >= g[num] }) continue
                val newState = press2(state, b)
                explore(newState)
            }
        }
        explore(List(g.size) { 0 })
        visited
    }
    println(statesList.map { it.size })
     */

    /*
    //val goals = lines.map { l -> l.split(" ")[0].drop(1).dropLast(1).toList() }
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

     */
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