package foi.air.szokpt.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ExitToApp
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.BGLevelThree
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.TextGray

/**
 * A composable function that renders an animated bottom navigation bar with three items.
 * Each item scales up its icon and shows its label when selected, and scales down when unselected.
 *
 * @param selectedIndex An integer representing the index of the currently selected item.
 * @param onItemSelected A lambda function that is invoked with the index of the selected item.
 *                        This allows parent composables to respond to navigation item selection.
 */
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import foi.air.szokpt.views.ROUTE_ACCOUNT
import foi.air.szokpt.views.ROUTE_DAILY_PROCESS
import foi.air.szokpt.views.ROUTE_DASHBOARD
import foi.air.szokpt.views.ROUTE_REPORTS

@Composable
fun AnimatedNavigationBar(
    navController: NavController
) {
    val selectedColor = Primary
    val unselectedColor = TextGray
    val backgroundColor: Color = BGLevelOne
    val selectedBackgroundColor = BGLevelThree

    val itemColors = NavigationBarItemDefaults.colors(
        indicatorColor = selectedBackgroundColor,
        selectedIconColor = selectedColor,
        unselectedIconColor = unselectedColor,
        selectedTextColor = selectedColor,
        unselectedTextColor = unselectedColor
    )

    // Track the current back stack entry to know the selected destination
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry.value?.destination?.route

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .background(backgroundColor)
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {

            // Dashboard
            val (homeIconContent, homeLabelContent) = AnimatedNavigationBarItem(
                icon = Icons.Rounded.Home,
                label = "Dashboard",
                isSelected = currentDestination == ROUTE_DASHBOARD
            )
            NavigationBarItem(
                icon = homeIconContent,
                label = homeLabelContent,
                selected = currentDestination == ROUTE_DASHBOARD,
                onClick = {
                    if (currentDestination != ROUTE_DASHBOARD) {
                        navController.navigate(ROUTE_DASHBOARD)
                    }
                },
                colors = itemColors
            )

            // Reports & Transactyions
            val (reportsIconContent, reportsLabelContent) = AnimatedNavigationBarItem(
                icon = Icons.Rounded.Email,
                label = "Transactions",
                isSelected = currentDestination == ROUTE_REPORTS
            )
            NavigationBarItem(
                icon = reportsIconContent,
                label = reportsLabelContent,
                selected = currentDestination == ROUTE_REPORTS,
                onClick = {
                    if (currentDestination != ROUTE_REPORTS) {
                        navController.navigate(ROUTE_REPORTS)
                    }
                },
                colors = itemColors
            )

            // Daily Process
            val (dailyProcessIconContent, dailyProcessLabelContent) = AnimatedNavigationBarItem(
                icon = Icons.AutoMirrored.Rounded.ExitToApp,
                label = "Daily Process",
                isSelected = currentDestination == ROUTE_DAILY_PROCESS
            )
            NavigationBarItem(
                icon = dailyProcessIconContent,
                label = dailyProcessLabelContent,
                selected = currentDestination == ROUTE_DAILY_PROCESS,
                onClick = {
                    if (currentDestination != ROUTE_DAILY_PROCESS) {
                        navController.navigate(ROUTE_DAILY_PROCESS)
                    }
                },
                colors = itemColors
            )

            // Account
            val (accountIconContent, accountLableContent) = AnimatedNavigationBarItem(
                icon = Icons.Rounded.AccountCircle,
                label = "Account",
                isSelected = currentDestination == ROUTE_ACCOUNT
            )
            NavigationBarItem(
                icon = accountIconContent,
                label = accountLableContent,
                selected = currentDestination == ROUTE_ACCOUNT,
                onClick = {
                    if (currentDestination != ROUTE_ACCOUNT) {
                        navController.navigate(ROUTE_ACCOUNT)
                    }
                },
                colors = itemColors
            )
        }
    }
}

