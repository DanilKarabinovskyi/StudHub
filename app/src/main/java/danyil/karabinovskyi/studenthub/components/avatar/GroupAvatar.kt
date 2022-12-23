package danyil.karabinovskyi.studenthub.components.avatar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import danyil.karabinovskyi.studenthub.core.data.entity.User
import danyil.karabinovskyi.studenthub.ui.theme.StudentHubTheme


private const val DefaultNumberOfAvatars = 4

@Composable
public fun GroupAvatar(
    users: List<User>,
    modifier: Modifier = Modifier,
    shape: Shape = StudentHubTheme.shapes.avatar,
    textStyle: TextStyle = StudentHubTheme.typography.captionBold,
    onClick: (() -> Unit)? = null,
) {
    val avatarUsers = users.take(DefaultNumberOfAvatars)
    val imageCount = avatarUsers.size

    val clickableModifier: Modifier = if (onClick != null) {
        modifier.clickable(
            onClick = onClick,
            indication = rememberRipple(bounded = false),
            interactionSource = remember { MutableInteractionSource() }
        )
    } else {
        modifier
    }

    Row(clickableModifier.clip(shape)) {
        Column(
            modifier = Modifier
                .weight(1f, fill = false)
                .fillMaxHeight()
        ) {
            for (imageIndex in 0 until imageCount step 2) {
                if (imageIndex < imageCount) {
                    UserAvatar(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize(),
                        user = avatarUsers[imageIndex],
                        shape = RectangleShape,
                        textStyle = textStyle,
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .weight(1f, fill = false)
                .fillMaxHeight()
        ) {
            for (imageIndex in 1 until imageCount step 2) {
                if (imageIndex < imageCount) {
                    UserAvatar(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize(),
                        user = avatarUsers[imageIndex],
                        shape = RectangleShape,
                        textStyle = textStyle,
                    )
                }
            }
        }
    }
}
