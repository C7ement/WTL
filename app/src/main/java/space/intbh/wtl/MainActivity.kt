@file:OptIn(ExperimentalFoundationApi::class)

package space.intbh.wtl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import space.intbh.wtl.model.FlowerModel
import space.intbh.wtl.repository.FlowerRepository
import space.intbh.wtl.ui.FlowerListViewModel
import space.intbh.wtl.ui.theme.WTLTheme


@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WTLTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background

                ) {
                    FlowerListScreen()
                }
            }
        }
    }
}

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
                itemContent = { Flower(uiState.flowerList[it]) })
        }
    } else {
        CircularProgressIndicator()
    }
}

@Composable
fun Flower(flower: FlowerModel) {
    Box(
        Modifier.padding(10.dp)
    ) {
        Card(
        ) {
            Column {
                Image(
                    painter = BitmapPainter(flower.picture),
                    contentDescription = flower.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.5f)
                )
                Box(
                    Modifier.padding(10.dp)
                ) {
                    Text(
                        flower.name,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WTLTheme {
        FlowerListScreen()
    }
}