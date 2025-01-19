package foi.air.szokpt.views.app

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import foi.air.szokpt.models.LatestProcess
import foi.air.szokpt.ui.components.interactible_components.OutlineBouncingButton
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.Secondary
import foi.air.szokpt.ui.theme.TextWhite

@Composable
fun LatestProcessDetailsView(
    navController: NavController,
    latestProcess: LatestProcess
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Details of latest process",
            color = TextWhite.copy(alpha = 0.7f),
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Process #${latestProcess.processNumber}",
            color = TextWhite,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(16.dp),
            color = BGLevelOne.copy(alpha = 0.5f),
            border = BorderStroke(1.dp, TextWhite.copy(alpha = 0.1f))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Process with ID ${latestProcess.fullProcessId} - Completed",
                        color = TextWhite,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Finished on ${latestProcess.processDate}",
                        color = TextWhite,
                        fontSize = 14.sp
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    OutlineBouncingButton(
                        modifier = Modifier.weight(1f),
                        inputText = "Export to PDF",
                        inputIcon = Icons.Default.ExitToApp,
                        contentColor = Primary,
                        borderColor = Secondary,
                        fontSize = 15.sp,
                        spacer = 10.dp
                    ) {
                        // TODO Handle PDF download
                    }

                    OutlineBouncingButton(
                        modifier = Modifier.weight(1f),
                        inputText = "Export to Excel",
                        inputIcon = Icons.Default.ExitToApp,
                        contentColor = Primary,
                        borderColor = Secondary,
                        fontSize = 15.sp,
                        spacer = 6.dp
                    ) {
                        // TODO Handle excel download
                    }
                }
            }
        }
    }
}