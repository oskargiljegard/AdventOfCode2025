package utils

fun <A> List<A>.split(delimiter: A): List<List<A>> {
    val i = indexOf(delimiter)
    if (i == -1) return listOf(this)
    return listOf(take(i)) + drop(i + 1).split(delimiter)
}
