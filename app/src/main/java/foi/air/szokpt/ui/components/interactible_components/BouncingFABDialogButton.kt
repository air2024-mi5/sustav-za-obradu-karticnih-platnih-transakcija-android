package foi.air.szokpt.ui.components.interactible_components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.TextWhite

@Composable
fun BouncingFABDialogButton(){
    var isPressed by remember { mutableStateOf(false) }

    // Animations for scaling the FAB
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = 500f
        ),
        finishedListener = { isPressed = false }
    )
    val iconScale by animateFloatAsState(
        targetValue = if (isPressed) 1.2f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = 700f
        ),
        finishedListener = { isPressed = false }
    )

    FloatingActionButton(
        onClick = {
            isPressed = true // Trigger the animation
        },
        modifier = Modifier
            .scale(scale) // Apply the scale animation
            .padding(16.dp), // Padding for spacing
        containerColor = Primary
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowUp,
            contentDescription = "Filter",
            tint = TextWhite
        )
    }
}
