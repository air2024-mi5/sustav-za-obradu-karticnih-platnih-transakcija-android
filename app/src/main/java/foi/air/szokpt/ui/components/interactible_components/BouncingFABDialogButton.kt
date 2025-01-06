package foi.air.szokpt.ui.components.interactible_components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.TextGray
import foi.air.szokpt.ui.theme.TextWhite

@Composable
fun BouncingFABDialogButton(
    isExpanded: Boolean = false,
    onToggle: () -> Unit,
    baseIcon: ImageVector = Icons.Default.KeyboardArrowUp,
    expandedIcon: ImageVector = Icons.Default.KeyboardArrowDown
){
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.15f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = 400f
        ),
        finishedListener = { isPressed = false }
    )
    val iconScale by animateFloatAsState(
        targetValue = if (isPressed) 1.5f else 1.1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        finishedListener = { isPressed = false }
    )
    val elevation by animateDpAsState(
        targetValue = if (isPressed) 12.dp else 6.dp, // Increase elevation on press (Shadows)
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        )
    )

    FloatingActionButton(
        onClick = {
            isPressed = true
            onToggle()
        },
        modifier = Modifier
            .scale(scale),
        containerColor = Primary,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = elevation,
            pressedElevation = elevation,
        )
    ) {
        Icon(
            modifier = Modifier
                .scale(iconScale),
            imageVector = if (isExpanded) expandedIcon else baseIcon,
            contentDescription = if (isExpanded) "Collapse" else "Expand",
            tint = TextWhite
        )
    }
}
