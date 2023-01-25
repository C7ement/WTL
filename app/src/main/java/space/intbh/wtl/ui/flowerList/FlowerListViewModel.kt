package space.intbh.wtl.ui.flowerList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import space.intbh.wtl.database.AppDatabase
import space.intbh.wtl.database.DescriptionData
import space.intbh.wtl.model.FlowerModel
import space.intbh.wtl.model.Month
import space.intbh.wtl.repository.FlowerRepository
import space.intbh.wtl.repository.WikipediaRepository

class FlowerListViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "cached_data_db"
    ).build()

    private val flowerRepository = FlowerRepository(application.resources, application.assets)
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
        loadFlowerDescriptionIfNeeded(flower);
    }

    private fun loadFlowerDescriptionIfNeeded(flower: FlowerModel) {

        if (uiState.value.flowerDescriptions.contains(flower.name)) return

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val cached = database.descriptionDataDao().getById(flower.name)

                val descriptionData = if (cached != null) {
                    cached
                } else {

                    val res = WikipediaRepository().searchAndGetPage(flower.name)

                    val descriptionData = DescriptionData(
                        flower.name,
                        res.title,
                        res.extract
                    )
                    database.descriptionDataDao().insert(descriptionData)
                    descriptionData
                }
                _uiState.update { current ->
                    current.copy(
                        flowerDescriptions = current.flowerDescriptions
                            .plus(descriptionData.flowerName to descriptionData)
                    )
                }
            }
        }
    }
}