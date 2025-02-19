package com.audioiconography.app.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650A4)
val PurpleGrey40 = Color(0xFF625B71)
val Pink40 = Color(0xFF7D5260)

val LightPrimary = Color(0xFF6200EE)
val LightOnPrimary = Color(0xFFFFFFFF)
val LightBackground = Color(0xFFFFFFFF)
val LightOnBackground = Color(0xFF000000)

val DarkPrimary = Color(0xFFBB86FC)
val DarkOnPrimary = Color(0xFF000000)
val DarkBackground = Color(0xFF121212)
val DarkOnBackground = Color(0xFFFFFFFF)

val LightColorScheme = androidx.compose.material3.lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightOnPrimary,
    background = LightBackground,
    onBackground = LightOnBackground
)

val DarkColorScheme = androidx.compose.material3.darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    background = DarkBackground,
    onBackground = DarkOnBackground
)
