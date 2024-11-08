package foi.air.szokpt.views.test_views

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Button(onClick = { navController.navigate("details") }) {
        Text("Details")
    }
}

@Composable
fun DetailsScreen(navController: NavController) {
    Text("Details Screen")
    Button(onClick = { navController.popBackStack() }) {
        Text("Back")
    }
}