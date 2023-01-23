package space.intbh.wtl.util

import space.intbh.wtl.model.Month

fun getAvailabilityText(months: List<Month>): String {
    val sortedIndexes = months.map { it.ordinal }.sorted().distinct()

    if (sortedIndexes.size == 12) return "Toute l'année."

    var timeRanges = sortedIndexes.fold(emptyList<TimeRange>()) { timeRanges, month ->
        if (timeRanges.isEmpty()) {
            listOf(TimeRange.oneMonth(month))
        } else if (month == timeRanges.last().end + 1) {
            timeRanges.last().end = month
            timeRanges
        } else {
            timeRanges + TimeRange.oneMonth(month)
        }
    }

    val includeJanuary = timeRanges.first().start == 0
    val includeDecember = timeRanges.last().end == 11
    if (includeJanuary && includeDecember && timeRanges.size > 1) {
        timeRanges.last().end = timeRanges.first().end
        timeRanges = timeRanges.subList(1, timeRanges.size)
    }
    timeRanges = replaceDuos(timeRanges)
    return joinStrings(timeRanges.map { it.toString() })
}

data class TimeRange(val start: Int, var end: Int) {
    companion object {
        fun oneMonth(month: Int): TimeRange {
            return TimeRange(month, month)
        }
    }

    val length: Int
        get() = Math.floorMod(end - start, 12) + 1

    override fun toString(): String {
        val startMonth = Month.getName(start)
        return when (length) {
            1 -> startMonth
            2 -> startMonth + ", " + Month.getName(end)
            else -> {
                val preposition =
                    if ("AO".contains(startMonth.first())) "d'"
                    else "de "
                "$preposition${startMonth} à ${Month.getName(end)}"
            }
        }
    }
}

fun replaceDuos(ranges: List<TimeRange>): List<TimeRange> {
    return ranges.fold(listOf()) { list, range ->
        if (range.length == 2)
            list
                .plus(TimeRange.oneMonth(range.start))
                .plus(TimeRange.oneMonth(range.end))
        else list + range
    }
}

fun joinStrings(strings: List<String>): String {
    strings.map {}
    return when (strings.size) {
        0 -> ""
        1 -> strings.first() + "."
        else -> {
            val allButLast = strings.subList(0, strings.size - 1)
            "${allButLast.joinToString(", ")} et ${strings.last()}."
        }
    }.replaceFirstChar { it.uppercase() }
}