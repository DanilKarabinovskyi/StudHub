package danyil.karabinovskyi.studenthub.components.avatar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import danyil.karabinovskyi.studenthub.R
import danyil.karabinovskyi.studenthub.common.utils.rememberStreamImagePainter
import danyil.karabinovskyi.studenthub.ui.theme.StudentHubTheme

@Composable
public fun Avatar(
    imageUrl: String,
    initials: String,
    modifier: Modifier = Modifier,
    shape: Shape = StudentHubTheme.shapes.avatar,
    textStyle: TextStyle = StudentHubTheme.typography.title3Bold,
    placeholderPainter: Painter? = null,
    contentDescription: String? = null,
    initialsAvatarOffset: DpOffset = DpOffset(0.dp, 0.dp),
    onClick: (() -> Unit)? = null,
) {
    if (LocalInspectionMode.current && imageUrl.isNotBlank()) {
        // Show hardcoded avatar from resources when rendering previews
        ImageAvatar(
            modifier = modifier,
            shape = shape,
            painter = painterResource(id = R.drawable.preview_avatar),
            contentDescription = contentDescription,
            onClick = onClick
        )
        return
    }
    if (imageUrl.isBlank()) {
        InitialsAvatar(
            modifier = modifier,
            initials = initials,
            shape = shape,
            textStyle = textStyle,
            onClick = onClick,
            avatarOffset = initialsAvatarOffset
        )
        return
    }

    val painter = rememberStreamImagePainter(
        data = imageUrl,
        placeholderPainter = painterResource(id = R.drawable.preview_avatar)
    )

    if (painter.state is AsyncImagePainter.State.Error) {
        InitialsAvatar(
            modifier = modifier,
            initials = initials,
            shape = shape,
            textStyle = textStyle,
            onClick = onClick,
            avatarOffset = initialsAvatarOffset
        )
    } else if (painter.state is AsyncImagePainter.State.Loading && placeholderPainter != null) {
        ImageAvatar(
            modifier = modifier,
            shape = shape,
            painter = placeholderPainter,
            contentDescription = contentDescription,
            onClick = onClick
        )
    } else {
        ImageAvatar(
            modifier = modifier,
            shape = shape,
            painter = painter,
            contentDescription = contentDescription,
            onClick = onClick
        )
    }
}