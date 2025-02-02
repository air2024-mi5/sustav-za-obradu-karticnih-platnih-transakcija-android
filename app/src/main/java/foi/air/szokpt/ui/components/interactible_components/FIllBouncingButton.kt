package foi.air.szokpt.ui.components.interactible_components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import foi.air.szokpt.ui.theme.Primary
import foi.air.szokpt.ui.theme.TextWhite


/**
 * A custom bouncing button component that provides animated effects for scaling
 * the button, icon, and spacer when pressed.
 *
 * @param modifier Modifier to be applied to the outer Box container
 * @param inputText Text to display on the button
 * @param inputIcon Icon to display beside the button text
 * @param buttonColor Background color of the button
 * @param contentColor Color of the button's content (text and icon)
 * @param useSpacerAnimation If true, applies a bounce effect to the spacer width
 * @param useIconAnimation If true, applies a bounce effect to the icon scaling
 * @param onClick Action to perform when the button is clicked
 */
@Composable
fun FillBouncingButton(
    modifier: Modifier = Modifier,
    inputText: String = "Button",
    inputIcon: ImageVector = Icons.Filled.FavoriteBorder,
    buttonColor: Color = Primary,
    contentColor: Color = TextWhite,
    useSpacerAnimation: Boolean = true,
    useIconAnimation: Boolean = true,
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
        targetValue = if (isPressed and useIconAnimation) 1.2f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessMediumLow
        )
    )
    val spacerScale by animateDpAsState(
        targetValue = if (isPressed and useSpacerAnimation) 8.dp else 4.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = 200f
        )
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Button(
            onClick = {
                isPressed = true
                onClick()
            },
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
            modifier = Modifier
                .scale(scale)
                .padding(8.dp)
        ) {
            Icon(
                imageVector = inputIcon,
                contentDescription = "Favorite Icon",
                tint = contentColor,
                modifier = Modifier
                    .size(24.dp)
                    .scale(iconScale)
            )
            Spacer(modifier = Modifier.width(spacerScale))
            Text(
                text = inputText,
                color = contentColor,
                fontSize = 16.sp,
                modifier = Modifier,
            )
        }
    }
}

@Composable
@Preview
fun FillButtonPreview(){
    FillBouncingButton(inputText = "Test Preview") {  }
}