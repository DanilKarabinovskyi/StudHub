package danyil.karabinovskyi.studenthub.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class Typography(
    val title1: TextStyle,
    val title3: TextStyle,
    val title3Bold: TextStyle,
    val title4: TextStyle,
    val bodyLarge: TextStyle,
    val bodyMedium: TextStyle,
    val bodySmall: TextStyle,
    val bodyItalic: TextStyle,
    val bodyBold: TextStyle,
    val footnote: TextStyle,
    val footnoteItalic: TextStyle,
    val footnoteBold: TextStyle,
    val captionBold: TextStyle,
    val tabBar: TextStyle,
    val singleEmoji: TextStyle,
    val emojiOnly: TextStyle,
) {

    companion object {
        fun defaultTypography(fontFamily: FontFamily? = null): Typography =
            Typography(
                title1 = TextStyle(
                    fontSize = 24.sp,
                    lineHeight = 34.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = fontFamily
                ),
                title3 = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 25.sp,
                    fontWeight = FontWeight.W400,
                    fontFamily = fontFamily
                ),
                title3Bold = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 25.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = fontFamily
                ),
                title4 = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 25.sp,
                    fontWeight = FontWeight.W400,
                    fontFamily = fontFamily
                ),
                bodyLarge = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                ),
                bodyMedium = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                ),
                bodySmall = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                ),
                bodyItalic = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W400,
                    fontStyle = FontStyle.Italic,
                    fontFamily = fontFamily
                ),
                bodyBold = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = fontFamily
                ),
                footnote = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.W400,
                    fontFamily = fontFamily
                ),
                footnoteItalic = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.W400,
                    fontStyle = FontStyle.Italic,
                    fontFamily = fontFamily
                ),
                footnoteBold = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = fontFamily
                ),
                captionBold = TextStyle(
                    fontSize = 10.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = fontFamily
                ),
                tabBar = TextStyle(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.W400,
                    fontFamily = fontFamily
                ),
                singleEmoji = TextStyle(
                    fontSize = 50.sp,
                    fontFamily = fontFamily
                ),
                emojiOnly = TextStyle(
                    fontSize = 50.sp,
                    fontFamily = fontFamily
                )
            )
    }
}
