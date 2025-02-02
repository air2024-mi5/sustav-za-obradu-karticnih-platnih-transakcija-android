import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foi.air.szokpt.models.CardBrandInformation
import foi.air.szokpt.ui.components.dashboard_components.GradientBarComponent
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.utils.TransactionUtils

@Composable
fun CardBrandsBarChart(
    modifier: Modifier = Modifier,
    stats: List<CardBrandInformation>,
) {
    val maxCount = stats.maxOfOrNull { it.count }?.coerceAtLeast(1) ?: 1

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        stats.forEach { stat ->
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = stat.count.toString(),
                    color = TextWhite,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                val barFraction = stat.count.toFloat() / maxCount.toFloat()
                GradientBarComponent(
                    heightFraction = barFraction,
                    maxHeight = 100,
                    colorStart = stat.color,
                    colorEnd = stat.color.copy(alpha = 0.7f),
                    cornerRadius = 5.dp
                )

                Image(
                    painter = painterResource(id = TransactionUtils.getCardBrandDrawable(stat.brand)),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .padding(top = 8.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}