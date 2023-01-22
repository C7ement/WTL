package space.intbh.wtl.util

import space.intbh.wtl.model.Month

fun getAvailabilityText(months: List<Month>): String {
    val sortedIndexes = months.map { it.ordinal }.sorted().distinct()
    if (sortedIndexes.size == 12) return "Toute l'année."
    if (sortedIndexes.containsAll(setOf(0, 11))) {
        val firstMonths = sortedIndexes
            .minus(0)
            .fold(listOf(0), ::addIfIsNext)
        val lastMonths = sortedIndexes
            .minus(11).reversed()
            .fold(listOf(11), ::addIfIsPrevious)
        if (lastMonths.size + firstMonths.size == months.size)
            return fromAToB(lastMonths.last(), firstMonths.last())
    } else {
        val areMonthAdjacent =
            ((sortedIndexes.last() - sortedIndexes.first()) == sortedIndexes.size - 1)
        if (areMonthAdjacent)
            return fromAToB(sortedIndexes.first(), sortedIndexes.last())
    }
    return sortedIndexes.map { Month.values()[it] }.joinToString()
}

fun fromAToB(a: Int, b: Int): String {
    return "De ${Month.values()[a]} à ${Month.values()[b]}."
}

fun addIfIsNext(list: List<Int>, value: Int): List<Int> {
    val isAdjacentValue = list.last() + 1 == value
    return if (isAdjacentValue) list + value else list
}
fun addIfIsPrevious(list: List<Int>, value: Int): List<Int> {
    val isAdjacentValue = list.last() - 1 == value
    return if (isAdjacentValue) list + value else list
}

