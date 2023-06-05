package danyil.karabinovskyi.studenthub.components.text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import danyil.karabinovskyi.studenthub.ui.theme.StudentHubTheme

@Composable
fun StudText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = StudentHubTheme.colorsV2.textHighEmphasis,
    style: TextStyle = StudentHubTheme.typography.bodySmall,
    fontWeight: FontWeight = FontWeight.Bold,
    fontSize: TextUnit = 12.sp,
    maxLines:Int = 1,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        fontWeight = fontWeight,
        fontSize = fontSize,
        style = style,
        maxLines = maxLines,
    )
}