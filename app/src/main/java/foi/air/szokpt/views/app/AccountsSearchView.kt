package foi.air.szokpt.views.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import foi.air.szokpt.ui.components.TileSegment
import foi.air.szokpt.ui.components.accounts_components.accountSearchView.AccountsSearchPanel
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.theme.TileSizeMode


@Composable
fun AccountSearchView(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .padding(16.dp),
            text = "Search All Accounts",
            color = TextWhite,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
        TileSegment(
            tileSizeMode = TileSizeMode.WRAP_CONTENT,
            innerPadding = 0.dp,
            outerMargin = 8.dp,
            minWidth = 250.dp,
            minHeight = 90.dp,
            color = BGLevelOne
        ) {
            AccountsSearchPanel(
                navController,
            )
        }
    }
}