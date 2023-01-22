package space.intbh.wtl.ui.flowerList

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import space.intbh.wtl.model.FlowerModel
import space.intbh.wtl.ui.flowerList.composant.CircularYearView

@Composable
fun FlowerCard(flower: FlowerModel) {
    Box(
        Modifier.padding(10.dp)
    ) {
        Card {
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
                Box(
                    Modifier.padding(10.dp)
                ) {
                    Text(
                        flower.name,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    }
}