package danyil.karabinovskyi.studenthub.components.avatar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import danyil.karabinovskyi.studenthub.core.data.entity.User
import danyil.karabinovskyi.studenthub.ui.theme.StudentHubTheme


@Composable
public fun UserAvatar(
    user: User,
    modifier: Modifier = Modifier,
    shape: Shape = StudentHubTheme.shapes.avatar,
    textStyle: TextStyle = StudentHubTheme.typography.title3Bold,
    contentDescription: String? = null,
    initialsAvatarOffset: DpOffset = DpOffset(0.dp, 0.dp),
    onClick: (() -> Unit)? = null,
) {
    Box(modifier = modifier) {
        Avatar(
            modifier = Modifier.fillMaxSize(),
            imageUrl = user.imageUrl,
            initials = user.fullName(),
            textStyle = textStyle,
            shape = shape,
            contentDescription = contentDescription,
            onClick = onClick,
            initialsAvatarOffset = initialsAvatarOffset
        )
    }
}
