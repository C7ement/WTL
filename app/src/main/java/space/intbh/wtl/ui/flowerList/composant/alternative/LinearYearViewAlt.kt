package space.intbh.wtl.ui.flowerList.composant

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import space.intbh.wtl.model.Month


@Composable
fun LinearYearViewAlt(months: List<Month>) {
    Row {
        Month.values().map {
            Text(
                it.name.slice(IntRange(0, 0)),
                Modifier.weight(1f),
                style = MaterialTheme.typography.bodySmall,
                color = if (months.contains(it)) it.color else Color.Transparent
            )
        }
    }
}
