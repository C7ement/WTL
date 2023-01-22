package space.intbh.wtl.ui.flowerList.composant

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import space.intbh.wtl.model.Month
import kotlin.math.cos
import kotlin.math.sin

const val DEG_TO_RAD = Math.PI / 180f

@Composable
fun CircularYearView(months: List<Month>) {
    val monthIndexes = months.map { it.ordinal }
    val color = MaterialTheme.colorScheme.secondary
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        val center = size.width / 2
        val outerRadius = center * .8f
        val innerRadius = outerRadius * .8f

        for (i in 0..11) {
            if (!monthIndexes.contains(i)) continue
            val degree = -i * 30 + 90

            val xStart = center + (innerRadius * cos(degree * DEG_TO_RAD)).toFloat()
            val yStart = center + (innerRadius * sin(degree * DEG_TO_RAD)).toFloat()

            val xEnd = center + (outerRadius * cos(degree * DEG_TO_RAD)).toFloat()
            val yEnd = center + (outerRadius * sin(degree * DEG_TO_RAD)).toFloat()

            val backgroundPath = Path().apply {
                moveTo(xStart, yStart)
                lineTo(xEnd, yEnd)
                arcToRad(
                    Rect(Offset.Zero, size),
                    (degree * DEG_TO_RAD).toFloat(),
                    (-30 * DEG_TO_RAD).toFloat(),
                    false
                )
                arcToRad(
                    Rect(Offset(size.width * 0.1f, size.height * 0.1f), size * 0.8f),
                    ((degree - 30) * DEG_TO_RAD).toFloat(),
                    (30 * DEG_TO_RAD).toFloat(),
                    false
                )
                close()
            }
            drawPath(
                backgroundPath, color = color,
            );
        }
    }
}