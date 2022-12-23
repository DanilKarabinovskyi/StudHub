package danyil.karabinovskyi.studenthub.components.messages

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import danyil.karabinovskyi.studenthub.common.utils.rememberStreamImagePainter
import danyil.karabinovskyi.studenthub.core.data.entity.Message
import danyil.karabinovskyi.studenthub.ui.theme.ProfilePictureSizeSmall
import danyil.karabinovskyi.studenthub.ui.theme.SpaceSmall
import danyil.karabinovskyi.studenthub.ui.theme.StudentHubTheme

@Composable
fun Message(
    onAuthorClick: (String) -> Unit,
    msg: Message,
    isUserMe: Boolean,
    isFirstMessageByAuthor: Boolean,
    isLastMessageByAuthor: Boolean
) {
    val borderColor = StudentHubTheme.colorsV2.borders

    val painter = rememberStreamImagePainter(
        data = msg.user.imageUrl,
        placeholderPainter = painterResource(id = danyil.karabinovskyi.studenthub.R.drawable.preview_avatar)
    )
    val spaceBetweenAuthors = if (isLastMessageByAuthor) Modifier.padding(top = 8.dp) else Modifier
    Row(modifier = spaceBetweenAuthors) {
        if (isLastMessageByAuthor) {
            // Avatar
            Image(
                modifier = Modifier
                    .clickable(onClick = {
//                        onAuthorClick(msg.author) todo
                    })
                    .padding(horizontal = SpaceSmall)
                    .size(ProfilePictureSizeSmall)
                    .border(1.5.dp, borderColor, CircleShape)
                    .border(3.dp, borderColor, CircleShape)
                    .clip(CircleShape)
                    .align(Alignment.Top),
                painter = painter,
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
        } else {
            // Space under avatar
            Spacer(modifier = Modifier.width(ProfilePictureSizeSmall + SpaceSmall * 2))
        }
        AuthorAndTextMessage(
            msg = msg,
            isUserMe = isUserMe,
            isFirstMessageByAuthor = isFirstMessageByAuthor,
            isLastMessageByAuthor = isLastMessageByAuthor,
            authorClicked = onAuthorClick,
            modifier = Modifier
                .padding(end = 16.dp)
                .weight(1f)
        )
    }
}

@Composable
fun ChatItemBubble(
    message: Message,
    isUserMe: Boolean,
    authorClicked: (String) -> Unit
) {
    val backgroundBubbleColor = if (isUserMe) {
        StudentHubTheme.colorsV2.ownMessagesBackground
    } else {
        StudentHubTheme.colorsV2.otherMessagesBackground
    }

    Column {
        Surface(
            color = backgroundBubbleColor,
            shape = ChatBubbleShape
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = message.message,
                overflow = TextOverflow.Ellipsis,
                style = StudentHubTheme.typography.bodyMedium,
                color = StudentHubTheme.colors.textPrimary,
            )
        }

        message.image?.let {
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                color = backgroundBubbleColor,
                shape = ChatBubbleShape
            ) {
                Image(
                    painter = rememberStreamImagePainter(
                        data = it,
                    ),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.size(160.dp),
                    contentDescription = stringResource(id = danyil.karabinovskyi.studenthub.R.string.attached_image)
                )
            }
        }
    }
}

@Composable
fun AuthorAndTextMessage(
    msg: Message,
    isUserMe: Boolean,
    isFirstMessageByAuthor: Boolean,
    isLastMessageByAuthor: Boolean,
    authorClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        if (isLastMessageByAuthor) {
            AuthorNameTimestamp(msg)
        }
        ChatItemBubble(msg, isUserMe, authorClicked = authorClicked)
        if (isFirstMessageByAuthor) {
            // Last bubble before next author
            Spacer(modifier = Modifier.height(8.dp))
        } else {
            // Between bubbles
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
private fun AuthorNameTimestamp(msg: Message) {
    Row(modifier = Modifier.semantics(mergeDescendants = true) {}) {
        Text(
            text = msg.user.fullName(),
            style = StudentHubTheme.typography.bodyMedium,
            modifier = Modifier
                .alignBy(LastBaseline)
                .paddingFrom(LastBaseline, after = 8.dp) // Space to 1st bubble
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = msg.createdAt,
            style = StudentHubTheme.typography.bodySmall,
            modifier = Modifier.alignBy(LastBaseline),
            color = StudentHubTheme.colorsV2.textLowEmphasis
        )
    }
}


private val ChatBubbleShape = RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp)