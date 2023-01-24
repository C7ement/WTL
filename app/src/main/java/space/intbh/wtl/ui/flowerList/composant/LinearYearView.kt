package space.intbh.wtl.ui.flowerList.composant

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import space.intbh.wtl.model.Month


@Composable
fun LinearYearView(months: List<Month>) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(5.dp)
    ) {
        val width = size.width / 12
        val height = size.height

        for (month in Month.values()) {
            if (!months.contains(month)) continue
            val index = month.ordinal
            val backgroundPath = Path().apply {
                moveTo(width*index, 0f)
                lineTo(width*(index+1), 0f)
                lineTo(width*(index+1), height)
                lineTo(width*(index), height)
                close()
            }
            drawPath(
                backgroundPath, color = month.color,
            );
        }
    }
}
