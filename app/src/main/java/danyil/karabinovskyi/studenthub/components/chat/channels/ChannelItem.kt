package danyil.karabinovskyi.studenthub.components.chat.channels

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import danyil.karabinovskyi.studenthub.R
import danyil.karabinovskyi.studenthub.common.utils.Timestamp
import danyil.karabinovskyi.studenthub.components.avatar.ChannelAvatar
import danyil.karabinovskyi.studenthub.core.data.entity.Channel
import danyil.karabinovskyi.studenthub.core.data.entity.User
import danyil.karabinovskyi.studenthub.ui.theme.ProfilePictureSizeMedium
import danyil.karabinovskyi.studenthub.ui.theme.StudentHubTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChannelItem(
    channel: Channel,
    currentUser: User?,
    onChannelClick: (Channel) -> Unit,
    onChannelLongClick: (Channel) -> Unit,
    modifier: Modifier = Modifier,
    leadingContent: @Composable RowScope.(Channel) -> Unit = {
        DefaultChannelItemLeadingContent(
            channelItem = it,
            currentUser = currentUser
        )
    },
    centerContent: @Composable RowScope.(Channel) -> Unit = {
        DefaultChannelItemCenterContent(
            channel = channel,
        )
    },
    trailingContent: @Composable RowScope.(Channel) -> Unit = {
        DefaultChannelItemTrailingContent(
            channel = channel,
        )
    },
) {
    val description = stringResource(id = R.string.chat)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .semantics { contentDescription = description }
            .combinedClickable(
                onClick = { onChannelClick(channel) },
                onLongClick = { onChannelLongClick(channel) },
                indication = rememberRipple(),
                interactionSource = remember { MutableInteractionSource() }
            ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            leadingContent(channel)

            centerContent(channel)

            trailingContent(channel)
        }
    }
}

@Composable
internal fun DefaultChannelItemLeadingContent(
    channelItem: Channel,
    currentUser: User?,
) {
    ChannelAvatar(
        modifier = Modifier
            .padding(
                start = 4.dp,
                end = 4.dp,
                top = 4.dp,
                bottom = 4.dp
            )
            .size(ProfilePictureSizeMedium),
        channel = channelItem,
        currentUser = currentUser
    )
}

@Composable
internal fun RowScope.DefaultChannelItemCenterContent(
    channel: Channel,
) {
    Column(
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp)
            .weight(1f)
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        val channelName: (@Composable (modifier: Modifier) -> Unit) = @Composable {
            Text(
                modifier = it,
                text = channel.name,
                style = StudentHubTheme.typography.bodyBold,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = StudentHubTheme.colors.textPrimary,
            )
        }
        channelName(Modifier)

        val lastMessageText = channel.lastMessage?.message
        if (lastMessageText != null) {
            if (lastMessageText.isNotEmpty()) {
                Text(
                    text = lastMessageText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = StudentHubTheme.typography.bodyMedium,
                    color = StudentHubTheme.colors.textPrimary,
                )
            }
        }
    }
}

@Composable
internal fun RowScope.DefaultChannelItemTrailingContent(
    channel: Channel,
) {
    val lastMessage = channel.lastMessage

    if (lastMessage != null) {
        Column(
            modifier = Modifier
                .padding(
                    start = 4.dp,
                    end = 4.dp,
                    top = 4.dp,
                    bottom = 4.dp
                )
                .wrapContentHeight()
                .align(Alignment.Bottom),
            horizontalAlignment = Alignment.End
        ) {
            val unreadCount = channel.unreadCount

            if (unreadCount != null && unreadCount > 0) {
                UnreadCountIndicator(
                    modifier = Modifier.padding(bottom = 4.dp),
                    unreadCount = unreadCount
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                MessageReadStatusIcon(
                    message = lastMessage,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(16.dp)
                )
                Timestamp(date = channel.lastUpdated)
            }
        }
    }
}