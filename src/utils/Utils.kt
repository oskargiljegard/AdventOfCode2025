package utils

fun <A> List<A>.split(delimiter: A): List<List<A>> = split { it == delimiter }

fun <A> List<A>.split(predicate: (A) -> Boolean): List<List<A>> {
    val i = indexOfFirst(predicate)
    if (i == -1) return listOf(this)
    return listOf(take(i)) + drop(i + 1).split(predicate)
}

fun <A> List<List<A>>.transpose(): List<List<A>> {
    return List(this[0].size) { y ->
        List(size) { x ->
            this[x][y]
        }
    }
}