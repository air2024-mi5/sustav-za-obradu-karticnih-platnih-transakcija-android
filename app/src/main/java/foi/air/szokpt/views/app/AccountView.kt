package foi.air.szokpt.views.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AccountView(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .padding(16.dp),
            text = "Account",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )

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
}

@Composable
fun RegisterNewAccount(){}