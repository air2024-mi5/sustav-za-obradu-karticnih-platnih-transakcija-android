package foi.air.szokpt.ui.components

/**
 * A composable function that renders an animated bottom navigation bar with three items.
 * Each item scales up its icon and shows its label when selected, and scales down when unselected.
 *
 * @param selectedIndex An integer representing the index of the currently selected item.
 * @param onItemSelected A lambda function that is invoked with the index of the selected item.
 *                        This allows parent composables to respond to navigation item selection.
 */
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ExitToApp
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import foi.air.szokpt.R
import foi.air.szokpt.context.Auth
import foi.air.szokpt.ui.theme.BGLevelOne
import foi.air.szokpt.ui.theme.BGLevelThree
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.TextGray
import foi.air.szokpt.views.ROUTE_ACCOUNT
import foi.air.szokpt.views.ROUTE_DAILY_PROCESSING
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
            val (homeIconContent, homeLabelContent) = AnimatedNavigationBarItem(
                icon = ImageVector.vectorResource(id = R.drawable.dashboard_panel),
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

            val (reportsIconContent, reportsLabelContent) = AnimatedNavigationBarItem(
                icon = ImageVector.vectorResource(id = R.drawable.task_checklist),
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

            val (dailyProcessIconContent, dailyProcessLabelContent) = AnimatedNavigationBarItem(
                icon = ImageVector.vectorResource(id = R.drawable.file_recycle),
                label = "Processing",
                isSelected = currentDestination == ROUTE_DAILY_PROCESSING
            )
            NavigationBarItem(
                icon = dailyProcessIconContent,
                label = dailyProcessLabelContent,
                selected = currentDestination == ROUTE_DAILY_PROCESSING,
                onClick = {
                    if (currentDestination != ROUTE_DAILY_PROCESSING) {
                        navController.navigate(ROUTE_DAILY_PROCESSING)
                    }
                },
                colors = itemColors
            )
            if (Auth.logedInUserData!!.role == "admin") {
                val (accountIconContent, accountLableContent) = AnimatedNavigationBarItem(
                    icon = ImageVector.vectorResource(id = R.drawable.member_list),
                    label = "Accounts",
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
}

