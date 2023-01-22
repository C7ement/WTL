package space.intbh.wtl.ui.flowerList

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import space.intbh.wtl.repository.FlowerRepository

class FlowerListViewModel(
     flowerRepository: FlowerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        FlowerListUiState(flowerRepository.getFlowerList())
    )
    val uiState: StateFlow<FlowerListUiState> = _uiState.asStateFlow()
}