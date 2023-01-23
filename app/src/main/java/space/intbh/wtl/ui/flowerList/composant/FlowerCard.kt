@file:OptIn(ExperimentalMaterial3Api::class)

package space.intbh.wtl.ui.flowerList.composant

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import space.intbh.wtl.model.FlowerModel
import space.intbh.wtl.util.getAvailabilityText

@Composable
fun FlowerCard(flower: FlowerModel, desc: String, flipped: Boolean, onTap: (FlowerModel) -> Unit) {
    Box(Modifier.padding(10.dp)) {
        Card(onClick = { onTap(flower) }) {
            Box {
                Recto(flower)
                if (flipped)
                    Verso(flower, desc)
            }
        }
    }
}

@Composable
fun Recto(flower: FlowerModel) {
    Column {
        Box {
            Image(
                painter = BitmapPainter(flower.picture),
                contentDescription = flower.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
            )
            Box(
                Modifier
                    .size(35.dp)
                    .align(Alignment.BottomEnd)
                    .padding(5.dp)
            ) {
                CircularYearView(flower.months)
            }
        }
        Box(Modifier.padding(10.dp)) {
            Column {
                Text(
                    flower.name,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    getAvailabilityText(flower.months),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun Verso(flower: FlowerModel, desc: String) {
    Card(Modifier.fillMaxSize()) {
        Box(Modifier.padding(10.dp)) {
            Column {
                Text(
                    flower.name,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    desc,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}