package danyil.karabinovskyi.studenthub.components.avatar
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import danyil.karabinovskyi.studenthub.core.data.entity.Channel
import danyil.karabinovskyi.studenthub.core.data.entity.User
import danyil.karabinovskyi.studenthub.ui.theme.StudentHubTheme


@Composable
public fun ChannelAvatar(
    channel: Channel,
    currentUser: User?,
    modifier: Modifier = Modifier,
    shape: Shape = StudentHubTheme.shapes.avatar,
    textStyle: TextStyle = StudentHubTheme.typography.title3Bold,
    groupAvatarTextStyle: TextStyle = StudentHubTheme.typography.captionBold,
    contentDescription: String? = null,
    onClick: (() -> Unit)? = null,
) {
    val members = channel.members
    val memberCount = members.size

    when {

        channel.image.isNotEmpty() -> {
            Avatar(
                modifier = modifier,
                imageUrl = channel.image,
                initials = channel.initials,
                textStyle = textStyle,
                shape = shape,
                contentDescription = contentDescription,
                onClick = onClick
            )
        }

        memberCount == 1 -> {
            val user = members.first().user

            UserAvatar(
                modifier = modifier,
                user = user,
                shape = shape,
                contentDescription = user.fullName(),
                onClick = onClick
            )
        }

        memberCount == 2 && members.any { it.user.id == currentUser?.id } -> {
            val user = members.first { it.user.id != currentUser?.id }.user

            UserAvatar(
                modifier = modifier,
                user = user,
                shape = shape,
                contentDescription = user.firstName,
                onClick = onClick
            )
        }

        else -> {
            val users = members.filter { it.user.id != currentUser?.id }.map { it.user }

            GroupAvatar(
                users = users,
                modifier = modifier,
                shape = shape,
                textStyle = groupAvatarTextStyle,
                onClick = onClick,
            )
        }
    }
}

