package foi.air.szokpt.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Provides the animated content (icon and label) for a navigation bar item.
 *
 * @param icon The icon to display in the navigation item.
 * @param label The label text to display below the icon.
 * @param isSelected A boolean indicating if this item is currently selected.
 * @return A pair of composable lambdas, where the first is the icon content
 *         and the second is the label content.
 */
@Composable
fun AnimatedNavigationBarItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean
): Pair<@Composable () -> Unit, @Composable () -> Unit> {
    val iconScale by animateFloatAsState(
        targetValue = if (isSelected) 1.3f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = 300f
        )
    )
    val labelScale by animateFloatAsState(
        targetValue = if (isSelected) 1.25f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = 250f
        )
    )

    val iconContent: @Composable () -> Unit = {
        Icon(
            icon,
            contentDescription = label,
            modifier = Modifier.scale(iconScale)
        )
    }

    val labelContent: @Composable () -> Unit = {
        Text(
            text = label,
            modifier = Modifier.scale(labelScale)
        )
    }

    return iconContent to labelContent
}