package com.ananta.pasal.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryGreen,
    onPrimary = Color(0xFF1B5E20),
    primaryContainer = Color(0xFF2E7D32),
    onPrimaryContainer = Color(0xFFC8E6C9),

    secondary = DarkGreen,
    onSecondary = Color(0xFF1B5E20),
    secondaryContainer = Color(0xFF388E3C),
    onSecondaryContainer = Color(0xFFC8E6C9),

    background = Color(0xFF1A1C1A),
    onBackground = Color(0xFFE2E3DE),

    surface = Color(0xFF252725),
    onSurface = Color(0xFFE2E3DE),
    surfaceVariant = Color(0xFF3A3C3A),
    onSurfaceVariant = Color(0xFFC2C8C2),

    error = Color(0xFFEF9A9A),
    onError = Color(0xFF7F0000),
    errorContainer = Color(0xFFB71C1C),
    onErrorContainer = Color(0xFFFFCDD2),

    tertiary = PrimaryGreen,
    onTertiary = Color(0xFF1B5E20),
    tertiaryContainer = Color(0xFF2E7D32),
    onTertiaryContainer = Color(0xFFC8E6C9)
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    onPrimary = White,
    primaryContainer = Color(0xFFC8E6C9),
    onPrimaryContainer = Color(0xFF1B5E20),

    secondary = DarkGreen,
    onSecondary = White,
    secondaryContainer = Color(0xFFA5D6A7),
    onSecondaryContainer = Color(0xFF1B5E20),

    background = Background,
    onBackground = Color.Black,

    surface = White,
    onSurface = Color.Black,
    surfaceVariant = Color(0xFFEEF2EE),
    onSurfaceVariant = Color(0xFF444844),

    error = Color(0xFFD32F2F),
    onError = White,
    errorContainer = Color(0xFFFFCDD2),
    onErrorContainer = Color(0xFF7F0000),

    tertiary = DarkGreen,
    onTertiary = White,
    tertiaryContainer = Color(0xFFA5D6A7),
    onTertiaryContainer = Color(0xFF1B5E20)
)

@Composable
fun PasalTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}