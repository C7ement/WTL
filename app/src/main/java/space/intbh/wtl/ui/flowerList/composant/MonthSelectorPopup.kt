@file:OptIn(ExperimentalLayoutApi::class)

package space.intbh.wtl.ui.flowerList.composant

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import space.intbh.wtl.model.Month


@Composable
fun MonthSelectorPopup(
    selectedMonths: Set<Month>,
    onSelect: (Month) -> Unit,
    onUnselect: (Month) -> Unit,
    onDismiss: () -> Unit
) {
    Surface(

        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.background
    ) {
        val lightColor = MaterialTheme.colorScheme.secondary
        val darkColor = MaterialTheme.colorScheme.surfaceVariant
        Column(horizontalAlignment = Alignment.End) {
            FlowRow(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Month.values().forEach { month ->
                    val selected = selectedMonths.contains(month)
                    Box(Modifier.padding(8.dp)) {
                        ClickableText(
                            AnnotatedString(month.name),
                            modifier = Modifier
                                .background(
                                    color = if (selected) lightColor else Color.Transparent,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .border(
                                    BorderStroke(1.dp, lightColor),
                                    shape = RoundedCornerShape(4.dp),
                                )
                                .padding(8.dp),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            onClick = { if (selected) onUnselect(month) else onSelect(month) },
                            style = TextStyle(color = if (selected) darkColor else lightColor),
                        )
                    }
                }
            }
            OutlinedButton(onClick = onDismiss, Modifier.padding(8.dp)) {
                Text("Fermer")
            }
        }
    }
}