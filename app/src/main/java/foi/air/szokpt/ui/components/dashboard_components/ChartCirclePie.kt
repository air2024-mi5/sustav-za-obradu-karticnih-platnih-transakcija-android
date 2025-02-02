package foi.air.szokpt.ui.components.dashboard_components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import foi.air.szokpt.viewmodels.PieChartModel

@Composable
fun ChartCirclePie(
    charts: List<PieChartModel>,
    size: Dp,
    strokeWidth: Dp
) {
    Canvas(
        modifier = Modifier
            .size(size)
            .padding(5.dp),
        onDraw = {
            var startAngle = 0f
            var sweepAngle: Float

            charts.forEach { chart ->
                sweepAngle = (chart.scaledValue / 100) * 360

                val gradientBrush = Brush.sweepGradient(
                    colors = listOf(chart.color.copy(alpha = 0.8f), chart.color)
                )

                drawArc(
                    brush = gradientBrush,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(
                        width = strokeWidth.toPx(),
                        cap = StrokeCap.Butt,
                        join = StrokeJoin.Bevel
                    )
                )

                startAngle += sweepAngle
            }
        }
    )
}