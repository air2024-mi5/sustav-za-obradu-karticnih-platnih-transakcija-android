package foi.air.szokpt.ui.components.interactible_components


import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import foi.air.szokpt.ui.theme.Primary

@Composable
fun TextBouncingButton(
    modifier: Modifier = Modifier,
    inputText: String = "Button",
    inputIcon: ImageVector = Icons.Filled.FavoriteBorder,
    buttonColor: Color = Primary,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }

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
            stiffness = Spring.StiffnessMediumLow
        )
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        TextButton(
            onClick = {
                isPressed = true
                onClick() // From the argument. Passed onClick to execute
            },
            modifier = Modifier
                .scale(scale)
                .padding(0.dp)
        ) {
            Icon(
                imageVector = inputIcon,
                contentDescription = "Favorite Icon",
                tint = buttonColor,
                modifier = Modifier
                    .size(24.dp)
                    .scale(iconScale)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = inputText,
                color = buttonColor,
                fontSize = 16.sp,
                modifier = Modifier
            )
        }
    }
}

@Preview
@Composable
fun PreviewTextBouncingButton(){
    TextBouncingButton {  }
}