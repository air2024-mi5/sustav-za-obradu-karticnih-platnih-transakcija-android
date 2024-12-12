package foi.air.szokpt.views.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import foi.air.szokpt.helpers.SharedAccountViewModel
import foi.air.szokpt.ui.components.interactible_components.OutlineBouncingButton
import foi.air.szokpt.ui.theme.TextWhite

@Composable
fun UserAccountView(navController: NavController, sharedViewModel: SharedAccountViewModel){
    val account = sharedViewModel.selectedAccount
    if (account != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Name: ${account.name}", color = TextWhite)
            Text("Last Name: ${account.lastName}", color = TextWhite)
            Text("Username: ${account.userName}", color = TextWhite)
            Text("Role: ${account.role}", color = TextWhite)
            OutlineBouncingButton(
                onClick = { navController.popBackStack() },
                inputText = "Back"
            )
        }
    } else {
        Text("No account selected", color = TextWhite)
    }
}