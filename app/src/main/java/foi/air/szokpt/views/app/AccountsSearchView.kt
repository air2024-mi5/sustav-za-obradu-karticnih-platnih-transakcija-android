package foi.air.szokpt.views.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import foi.air.szokpt.ui.components.TileSegment
import foi.air.szokpt.ui.components.accounts_components.accountsSearchView.AccountsSearchPanel
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.TileSizeMode
import foi.air.szokpt.viewmodels.AccountsViewModel


@Composable
fun AccountSearchView(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TileSegment(
            tileSizeMode = TileSizeMode.WRAP_CONTENT,
            innerPadding = 0.dp,
            outerMargin = 8.dp,
            minWidth = 250.dp,
            minHeight = 90.dp,
            color = BGLevelOne
        ) {
            val viewModel: AccountsViewModel = viewModel()
            AccountsSearchPanel(
                navController,
                viewModel
            )
        }
    }
}