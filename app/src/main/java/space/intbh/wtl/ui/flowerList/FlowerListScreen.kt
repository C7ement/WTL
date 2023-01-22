@file:OptIn(ExperimentalFoundationApi::class)

package space.intbh.wtl.ui.flowerList

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import space.intbh.wtl.repository.FlowerRepository


@Composable
fun FlowerListScreen() {
    val context = LocalContext.current
    val flowerRepository = FlowerRepository(context.resources, context.assets)
    val viewModel = FlowerListViewModel(flowerRepository)
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.flowerList.isNotEmpty()) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2)
        ) {
            items(uiState.flowerList.size,
                itemContent = { FlowerCard(uiState.flowerList[it]) })
        }
    } else {
        CircularProgressIndicator()
    }
}