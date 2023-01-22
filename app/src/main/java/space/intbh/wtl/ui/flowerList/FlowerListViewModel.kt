package space.intbh.wtl.ui.flowerList

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import space.intbh.wtl.model.FlowerModel
import space.intbh.wtl.model.Month
import space.intbh.wtl.repository.FlowerRepository

class FlowerListViewModel(
    flowerRepository: FlowerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        FlowerListUiState(flowerRepository.getFlowerList())
    )
    val uiState: StateFlow<FlowerListUiState> = _uiState.asStateFlow()


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

    fun toggleFlowerDetail(flower: FlowerModel) {
        val selected = uiState.value.selectedFlowers.contains(flower)
        _uiState.update { current ->
            current.copy(
                selectedFlowers =
                if (selected) current.selectedFlowers.minus(flower)
                else current.selectedFlowers.plus(flower)
            )
        }
    }
}