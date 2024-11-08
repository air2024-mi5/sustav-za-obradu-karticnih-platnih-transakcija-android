package foi.air.szokpt.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Home
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
@Composable
fun AnimatedNavigationBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
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

    // For the radius at the top (Left & Right) of the NavBar
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .background(backgroundColor)
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {

            // Dashboard Item
            val (homeIconContent, homeLabelContent) = AnimatedNavigationBarItem(
                icon = Icons.Rounded.Home,
                label = "Dashboard",
                isSelected = selectedIndex == 0
            )
            NavigationBarItem(
                icon = homeIconContent,
                label = homeLabelContent,
                selected = selectedIndex == 0,
                onClick = { onItemSelected(0) },
                colors = itemColors
            )

            val (reportsIconContent, reportsLabelContent) = AnimatedNavigationBarItem(
                icon = Icons.Rounded.Email,
                label = "Reports & Trans.",
                isSelected = selectedIndex == 1
            )
            NavigationBarItem(
                icon = reportsIconContent,
                label = reportsLabelContent,
                selected = selectedIndex == 1,
                onClick = { onItemSelected(1) },
                colors = itemColors
            )

            // Daily Process Item
            val (dailyProcessIconContent, dailyProcessLabelContent) = AnimatedNavigationBarItem(
                icon = Icons.AutoMirrored.Rounded.ExitToApp,
                label = "Daily Process",
                isSelected = selectedIndex == 2
            )
            NavigationBarItem(
                icon = dailyProcessIconContent,
                label = dailyProcessLabelContent,
                selected = selectedIndex == 2,
                onClick = { onItemSelected(2) },
                colors = itemColors
            )
        }
    }
}
