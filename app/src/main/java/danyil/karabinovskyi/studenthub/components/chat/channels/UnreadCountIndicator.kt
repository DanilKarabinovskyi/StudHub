package danyil.karabinovskyi.studenthub.components.chat.channels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import danyil.karabinovskyi.studenthub.ui.theme.StudentHubTheme


@Composable
public fun UnreadCountIndicator(
    unreadCount: Int,
    modifier: Modifier = Modifier,
    color: Color = StudentHubTheme.colors.brand,
) {

    val displayText = if (unreadCount > LimitTooManyUnreadCount) UnreadCountMany else unreadCount.toString()
    val shape = RoundedCornerShape(9.dp)

    Box(
        modifier = modifier
            .defaultMinSize(minWidth = 18.dp, minHeight = 18.dp)
            .background(shape = shape, color = color)
            .padding(horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = displayText,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

private const val UnreadCountMany = "99+"
private const val LimitTooManyUnreadCount = 99
