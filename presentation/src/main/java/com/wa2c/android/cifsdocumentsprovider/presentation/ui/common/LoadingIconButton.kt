package com.wa2c.android.cifsdocumentsprovider.presentation.ui.common

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.wa2c.android.cifsdocumentsprovider.presentation.R

@Composable
fun LoadingIconButton(
    painter: Painter = painterResource(id = R.drawable.ic_reload),
    contentDescription: String? = stringResource(id = R.string.host_reload_button),
    isLoading: Boolean,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = { onClick() }
    ) {
        val currentRotation = remember { mutableStateOf(0f) }
        val rotation = remember { Animatable(currentRotation.value) }

        Icon(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier
                .rotate(rotation.value)
        )

        // Loading animation
        LaunchedEffect(isLoading) {
            if (isLoading) {
                // Infinite repeatable rotation when is playing
                rotation.animateTo(
                    targetValue = currentRotation.value + 360f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(3000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    )
                ) {
                    currentRotation.value = value
                }
            } else {
                // Slow down rotation on pause
                rotation.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(
                        durationMillis = 0,
                        easing = LinearOutSlowInEasing
                    )
                ) {
                    currentRotation.value = 0f
                }
            }
        }
    }
}
