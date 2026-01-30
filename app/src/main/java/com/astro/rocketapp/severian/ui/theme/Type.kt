package com.astro.rocketapp.severian.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.astro.rocketapp.severian.R

val LexendRegular = FontFamily(
    Font(R.font.lexend_regular)
)

val LexendBold = FontFamily(
    Font(R.font.lexend_semibold)
)

val LexendLight = FontFamily(
    Font(R.font.lexend_light)
)


val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = LexendBold,
        fontSize = 24.sp,
        letterSpacing = 0.sp
    ),
    bodySmall = TextStyle(
        fontFamily = LexendLight,
        fontSize = 12.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = LexendRegular,
        fontSize = 16.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = LexendLight,
        fontSize = 20.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = LexendBold,
        fontSize = 32.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = LexendBold,
        fontSize = 40.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = LexendBold,
        fontSize = 20.sp,
    )
)