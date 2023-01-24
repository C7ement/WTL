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
    return joinStrings(timeRanges)
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

fun joinStrings(ranges: List<TimeRange>): String {
    return when (ranges.size) {
        0 -> ""
        1 -> ranges.first().toString() + "."
        else -> {
            var allButLast = ranges.subList(0, ranges.size - 1)
            var last = ranges.last()
            if (ranges.last().length == 2) {
                 allButLast = allButLast + TimeRange.oneMonth(ranges.last().start)
                 last = TimeRange.oneMonth(ranges.last().end)
            }
            allButLast.joinToString(", ") + " et $last."
        }
    }.replaceFirstChar { it.uppercase() }
}