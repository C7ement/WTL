package space.intbh.wtl.ui.flowerList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import space.intbh.wtl.model.FlowerModel
import space.intbh.wtl.model.Month
import space.intbh.wtl.repository.FlowerRepository
import space.intbh.wtl.repository.WikipediaRepository

class FlowerListViewModel(
    flowerRepository: FlowerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        FlowerListUiState(flowerRepository.getFlowerList())
    )
    val uiState: StateFlow<FlowerListUiState> = _uiState.asStateFlow()

    init {
        loadDescriptions()
    }

    fun selectMonth(month: Month) {
        _uiState.update { current ->
            current.copy(selectedMonths = current.selectedMonths.plus(month))
        }
    }

    fun unselectMonth(month: Month) {
        _uiState.update { current ->
            current.copy(selectedMonths = current.selectedMonths.minus(month))
        }
    }

    fun flipCard(flower: FlowerModel) {
        val flipped = uiState.value.flippedCards.contains(flower)
        _uiState.update {
            it.copy(
                flippedCards = if (flipped)
                    it.flippedCards.minus(flower)
                else it.flippedCards.plus(
                    (flower)
                )
            )
        }
    }

    fun loadDescriptions() {
        viewModelScope.launch {
            val flowers = uiState.value.flowerList
            for (flower in flowers) {
                val article =
                    WikipediaRepository().searchAndGEtPage(flower.name)
                _uiState.update { current ->
                    current.copy(flowerDescriptions = current.flowerDescriptions.plus(flower.name to article))
                }
            }

        }
    }

}