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
import java.util.concurrent.Executors

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

        val flowers = uiState.value.flowerList
        for (flower in flowers) {
            viewModelScope.launch {

                    withContext(Dispatchers.IO) {
                        val cached = database.descriptionDataDao().getById(flower.name)
                        if (cached == null) {
                        val searchResult =
                            WikipediaRepository().searchAndGEtPage(flower.name)

                        database.descriptionDataDao().insert(
                            DescriptionData(
                                flower.name,
                                "loaded" + searchResult.res2.extract
                            )
                        )

                        _uiState.update { current ->
                            current.copy(
                                flowerDescriptions = current.flowerDescriptions.plus(
                                    flower.name to searchResult.res2.extract
                                )
                            )
                        }
                    } else {
                            _uiState.update { current ->
                                current.copy(
                                    flowerDescriptions = current.flowerDescriptions.plus(
                                        flower.name to cached.description
                                    )
                                )
                            }
                        }
                }
            }

        }
    }

}