package foi.air.szokpt.views.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import foi.air.szokpt.ui.components.accounts_components.accountDashboardView.AccountsSearchTile
import foi.air.szokpt.ui.components.accounts_components.accountDashboardView.RegistrationTile

@Composable
fun AccountView(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            item(span = { GridItemSpan(2) }) {
                RegistrationTile(navController)
            }
            item(span = { GridItemSpan(2) }) {
                AccountsSearchTile(navController)
            }
        }
    }
}

