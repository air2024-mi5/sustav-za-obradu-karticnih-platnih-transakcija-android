package foi.air.szokpt.ui.components.dashboard_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import foi.air.szokpt.viewmodels.PieChartModel

@Composable
fun ChartWithLegend(
    charts: List<PieChartModel>,
    size: Dp = 130.dp,
    strokeWidth: Dp = 18.dp
) {
    Row(modifier = Modifier.padding(16.dp)) {
        ChartCirclePie(charts = charts, size = size, strokeWidth = strokeWidth)

        Spacer(modifier = Modifier.width(30.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            charts.forEach { chart ->
                LegendItem(label = chart.label, value = chart.value.toInt(), color = chart.color)
            }
        }
    }
}