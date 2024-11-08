package foi.air.szokpt.views.test_views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Button(onClick = { navController.navigate("details") }) {
                Text("Details")
            }
            Text(text = "Home")
        }
    }
}

@Composable
fun DetailsScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text("Details Screen")
            Button(onClick = { navController.popBackStack() }) {
                Text("Back")
            }
        }
    }
}


@Composable
fun DashboardScreen(navController: NavController) {
    Text(text = "Dashboard Screen")
}

@Composable
fun ReportsScreen(navController: NavController) {
    Text(text = "Reports Screen")
}

@Composable
fun DailyProcessScreen(navController: NavController) {
    Text(text = "Daily Process Screen")
}