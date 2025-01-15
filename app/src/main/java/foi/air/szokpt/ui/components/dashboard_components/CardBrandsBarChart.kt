import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import foi.air.szokpt.helpers.TransactionUtils
import foi.air.szokpt.models.CardBrandInformation

@Composable
fun CardBrandsBarChart(
    modifier: Modifier = Modifier,
    stats: List<CardBrandInformation>,
    transactionUtils: TransactionUtils
) {
    val maxCount = stats.maxOfOrNull { it.count } ?: 0
    if (maxCount == 0) return

    var selectedIndex by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            var totalWidth by remember { mutableFloatStateOf(0f) }
            var availableWidth by remember { mutableFloatStateOf(0f) }
            var barWidth by remember { mutableFloatStateOf(0f) }
            val spacing = 50f

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .padding(bottom = 10.dp)
                    .pointerInput(Unit) {
                        detectHoldGesture(
                            onHold = { offset ->
                                val clickedIndex =
                                    ((offset.x - spacing / 2) / (barWidth + spacing)).toInt()
                                if (clickedIndex in stats.indices) {
                                    selectedIndex = clickedIndex
                                }
                            },
                            onRelease = {
                                selectedIndex = null
                            }
                        )
                    }
            ) {
                val minBarWidth = 40f
                totalWidth = size.width
                availableWidth = totalWidth - (spacing * (stats.size))
                barWidth = (availableWidth / stats.size).coerceAtLeast(minBarWidth)

                stats.forEachIndexed { index, stat ->
                    if (stat.count != 0) {
                        val x = index * (barWidth + spacing) + (spacing / 2)
                        val height = ((stat.count.toFloat() / maxCount) * (size.height - 20f))

                        drawRect(
                            color = stat.color,
                            topLeft = Offset(x, size.height - height - 20f),
                            size = Size(barWidth, height),
                        )

                        if (selectedIndex == index) {
                            drawContext.canvas.nativeCanvas.drawText(
                                "${stat.brand}: ${stat.count}",
                                x + barWidth / 2,
                                size.height - height - 30f,
                                android.graphics.Paint().apply {
                                    color = android.graphics.Color.WHITE
                                    textAlign = android.graphics.Paint.Align.CENTER
                                    textSize = 30f
                                }
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                stats.forEach { stat ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width((barWidth / 2).dp)


                    ) {
                        Image(
                            painter = painterResource(
                                id = transactionUtils.getCardBrandDrawable(stat.brand)
                            ),
                            contentDescription = "Card ${stat.brand}",
                            modifier = Modifier
                                .width(35.2.dp)
                        )
                    }
                }
            }
        }
    }
}

suspend fun PointerInputScope.detectHoldGesture(
    onHold: (Offset) -> Unit,
    onRelease: () -> Unit
) {
    awaitPointerEventScope {
        while (true) {
            val down = awaitFirstDown()
            val downTime = System.currentTimeMillis()
            var isHolding = false

            while (true) {
                val event = awaitPointerEvent()
                val currentTime = System.currentTimeMillis()

                if (currentTime - downTime >= 100 && !isHolding) { // 100ms hold time
                    isHolding = true
                    onHold(down.position)
                }

                if (event.changes.any { it.changedToUp() }) {
                    onRelease()
                    break
                }
            }
        }
    }
}


