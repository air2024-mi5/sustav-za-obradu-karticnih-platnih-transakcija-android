package foi.air.szokpt.ui.components.accounts_components.accountDashboardView

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import foi.air.szokpt.R
import foi.air.szokpt.ui.components.TileSegment
import foi.air.szokpt.ui.components.interactible_components.OutlineBouncingButton
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.Secondary
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.theme.TileSizeMode
import foi.air.szokpt.views.ROUTE_ALL_ACCOUNT_SEARCH

@Composable
fun AccountsSearchTile(navController: NavController) {
    TileSegment(
        tileSizeMode = TileSizeMode.WRAP_CONTENT,
        innerPadding = 10.dp,
        outerMargin = 4.dp,
        minWidth = 250.dp,
        minHeight = 20.dp,
        color = BGLevelOne
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "All Accounts",
                    color = TextWhite,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
                OutlineBouncingButton(
                    modifier = Modifier,
                    inputText = "",
                    inputIcon = ImageVector.vectorResource(id = R.drawable.member_search),
                    contentColor = Primary,
                    borderColor = Secondary,
                ) {
                    navController.navigate(ROUTE_ALL_ACCOUNT_SEARCH)
                }
            }
        }
    }
}

