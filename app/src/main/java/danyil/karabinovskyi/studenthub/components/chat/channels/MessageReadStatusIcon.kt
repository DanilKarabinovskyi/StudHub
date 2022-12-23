package danyil.karabinovskyi.studenthub.components.chat.channels

import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import danyil.karabinovskyi.studenthub.R
import danyil.karabinovskyi.studenthub.common.utils.SyncStatus
import danyil.karabinovskyi.studenthub.core.data.entity.Channel
import danyil.karabinovskyi.studenthub.core.data.entity.Message
import danyil.karabinovskyi.studenthub.core.data.entity.User
import danyil.karabinovskyi.studenthub.ui.theme.StudentHubTheme

@Composable
fun MessageReadStatusIcon(
    message: Message,
    modifier: Modifier = Modifier,
) {
    MessageReadStatusIcon(
        message = message,
        isMessageRead = message.isMessageRead?:false,
        modifier = modifier,
    )
}

@Composable
fun MessageReadStatusIcon(
    message: Message,
    isMessageRead: Boolean,
    modifier: Modifier = Modifier,
) {
    val syncStatus = message.syncStatus

    when {
        isMessageRead -> {
            Icon(
                modifier = modifier,
                painter = painterResource(id = R.drawable.message_seen),
                contentDescription = null,
                tint = StudentHubTheme.colors.textPrimary,
            )
        }
        syncStatus == SyncStatus.SYNC_NEEDED || syncStatus == SyncStatus.AWAITING_ATTACHMENTS -> {
            Icon(
                modifier = modifier,
                painter = painterResource(id = R.drawable.ic_clock),
                contentDescription = null,
                tint = StudentHubTheme.colors.textPrimary,
            )
        }
        syncStatus == SyncStatus.COMPLETED -> {
            Icon(
                modifier = modifier,
                painter = painterResource(id = R.drawable.message_sent),
                contentDescription = null,
                tint = StudentHubTheme.colors.textPrimary,
            )
        }
    }
}

