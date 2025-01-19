package foi.air.szokpt.ui.components.processing_components.dailyProcessingDashboardView

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import foi.air.szokpt.ui.components.TileSegment
import foi.air.szokpt.ui.components.interactible_components.OutlineBouncingButton
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.Secondary
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.theme.TileSizeMode
import foi.air.szokpt.views.ROUTE_LATEST_PROCESSING_DETAILS

@Composable
fun LatestProcessingTile(navController: NavController) {
    TileSegment(
        tileSizeMode = TileSizeMode.WRAP_CONTENT,
        innerPadding = 15.dp,
        outerMargin = 4.dp,
        minWidth = 250.dp,
        minHeight = 20.dp,
        color = BGLevelOne
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                modifier = Modifier.padding(7.dp),
                text = "Latest Processing",
                color = TextWhite,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 15.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = "4.11.2024 21:26",
                        color = TextWhite,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Process #993",
                        color = TextWhite,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "ID 22573912321",
                        color = TextWhite,
                        fontSize = 14.sp
                    )
                }

                OutlineBouncingButton(
                    modifier = Modifier.width(100.dp),
                    inputText = "",
                    inputIcon = Icons.AutoMirrored.Rounded.ArrowForward,
                    contentColor = Primary,
                    borderColor = Secondary,
                ) {
                    navController.navigate(ROUTE_LATEST_PROCESSING_DETAILS)
                }
            }
        }
    }
}