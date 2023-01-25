@file:OptIn(ExperimentalMaterial3Api::class)

package space.intbh.wtl.ui.flowerList.composant

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import space.intbh.wtl.database.DescriptionData
import space.intbh.wtl.model.FlowerModel
import space.intbh.wtl.util.getAvailabilityText

@Composable
fun FlowerCard(
    flower: FlowerModel,
    descriptionData: DescriptionData?,
    flipped: Boolean,
    onTap: (FlowerModel) -> Unit
) {
    Box(
        Modifier
            .padding(10.dp)
            .height(IntrinsicSize.Min)
    ) {
        Card(onClick = { onTap(flower) }) {
            Box {
                Recto(flower)
                if (flipped)
                    Verso(descriptionData)
            }
        }
    }
}

@Composable
fun Recto(flower: FlowerModel) {
    Column {
        Image(
            painter = BitmapPainter(flower.picture),
            contentDescription = flower.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.5f)
        )
        LinearYearView(months = flower.months)
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
fun Verso(descriptionData: DescriptionData?) {
    Card(Modifier.fillMaxSize()) {
        Box(Modifier.padding(10.dp)) {
            Column {
                if (descriptionData == null)
                    CircularProgressIndicator()
                else {
                    Text(
                         "Wikipedia: "+ descriptionData.title ,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        descriptionData.description,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}