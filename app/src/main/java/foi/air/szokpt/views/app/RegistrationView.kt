package foi.air.szokpt.views.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun RegistrationView(navController: NavController){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ){
        item(span = { GridItemSpan(2) }) {
            AllTransactionsTile()
        }
        item {
            ValueTile()
        }
        item {
            CardTypesTile()
        }
        item(span = { GridItemSpan(2) }) {
            TransactionsListTile()
        }
        item(span = { GridItemSpan(2) }) {
            TransactionOutcomes()
        }
        item(span = { GridItemSpan(2) }) {
            TransactionsByDayTile()
        }
    }
}