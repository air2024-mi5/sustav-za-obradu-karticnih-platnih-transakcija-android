package foi.air.szokpt.views.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import foi.air.szokpt.helpers.SharedAccountViewModel
import foi.air.szokpt.ui.components.TileSegment
import foi.air.szokpt.ui.components.interactible_components.OutlineBouncingButton
import foi.air.szokpt.ui.components.list_components.AccountListItem
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.BGLevelThree
import foi.air.szokpt.ui.theme.BGLevelTwo
import foi.air.szokpt.ui.theme.TextWhite
import foi.air.szokpt.ui.theme.TileSizeMode
import foi.air.szokpt.ui.theme.danger
import foi.air.szokpt.ui.theme.warning
import foi.air.szokpt.views.ROUTE_USER_ACCOUNT_OVERVIEW

@Composable
fun UserAccountView(navController: NavController, sharedViewModel: SharedAccountViewModel){
    val account = sharedViewModel.selectedAccount
    if (account != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()

        ) {
            Text(
                modifier = Modifier
                    .padding(16.dp),
                text = "Account Overview",
                color = TextWhite,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                item {
                    TileSegment(
                        tileSizeMode = TileSizeMode.WRAP_CONTENT,
                        innerPadding = 16.dp,
                        outerMargin = 6.dp,
                        minWidth = 250.dp,
                        minHeight = 90.dp,
                        color = BGLevelOne
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Name: ${account.name}", color = TextWhite)
                            Text("Last Name: ${account.lastName}", color = TextWhite)
                            Text("Username: ${account.userName}", color = TextWhite)
                            Text("Role: ${account.role}", color = TextWhite)
                        }
                    }
                }
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        OutlineBouncingButton(
                            onClick = { /* Trigger account blocking */ },
                            inputText = "Delete Acc.",
                            contentColor = danger,
                            borderColor = danger,
                            inputIcon = Icons.Rounded.Delete,
                        )
                        OutlineBouncingButton(
                            onClick = { /* Trigger account blocking */ },
                            inputText = "Deactivate",
                            contentColor = warning,
                            borderColor = warning,
                            inputIcon = Icons.Rounded.Clear,
                        )
                    }

                }
            }
        }

    }
    else {
        Text("No account selected", color = TextWhite)
    }
}