package space.intbh.wtl.ui.flowerList

import space.intbh.wtl.model.FlowerModel
import space.intbh.wtl.model.Month

data class FlowerListUiState(
    val flowerList: List<FlowerModel>,
    val selectedMonths: Set<Month> = setOf(),
    val flippedCards: Set<FlowerModel> = setOf(),
    val flowerDescriptions: Map<String, String> = emptyMap(),
) {

    val matchingFlowers: List<FlowerModel>
        get() = if (selectedMonths.isEmpty()) flowerList else flowerList.filter { flower ->
            flower.months.any {
                selectedMonths.contains(it)
            }
        }
}