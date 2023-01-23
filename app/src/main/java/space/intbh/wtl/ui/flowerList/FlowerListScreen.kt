@file:OptIn(
    ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)

package space.intbh.wtl.ui.flowerList

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import space.intbh.wtl.repository.FlowerRepository
import space.intbh.wtl.ui.flowerList.composant.FlowerCard
import space.intbh.wtl.ui.flowerList.composant.MonthSelectorPopup


@Composable
fun FlowerListScreen() {
    val context = LocalContext.current
    val flowerRepository = FlowerRepository(context.resources, context.assets)
    val viewModel = FlowerListViewModel(flowerRepository)
    val uiState by viewModel.uiState.collectAsState()
    val openDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Fleurs d'Aujourd'hui",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
            )
        },
        floatingActionButton = {
            val fabColor = MaterialTheme.colorScheme.background.copy(alpha = 0.9f)
            ExtendedFloatingActionButton(
                containerColor = fabColor,
                text = { Text("Filtrer") },
                onClick = { openDialog.value = true },
                icon = { Icon(Icons.Filled.Search, "") }
            )
        }
    ) { padding ->
        if (uiState.flowerList.isNotEmpty()) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                Modifier.padding(padding)
            ) {
                items(uiState.matchingFlowers.size,
                    itemContent = {
                        FlowerCard(
                            uiState.flowerList[it],
                            desc = uiState.flowerDescriptions[uiState.flowerList[it].name] ?: "",
                            flipped = viewModel.uiState.value.flippedCards.contains(uiState.flowerList[it]),
                            onTap = viewModel::flipCard
                        )
                    }
                )
            }
        } else {
            CircularProgressIndicator(Modifier.padding(padding))
        }
        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = { openDialog.value = false }
            ) {
                MonthSelectorPopup(uiState.selectedMonths,
                    onSelect = { viewModel.selectMonth(it) },
                    onUnselect = { viewModel.unselectMonth(it) },
                    onDismiss = { openDialog.value = false })
            }
        }
    }
}