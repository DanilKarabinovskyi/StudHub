package danyil.karabinovskyi.studenthub.components.buttons

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import danyil.karabinovskyi.studenthub.R
import danyil.karabinovskyi.studenthub.ui.theme.FunctionalDarkGrey
import danyil.karabinovskyi.studenthub.ui.theme.Shadow2

private enum class Visibility {
    VISIBLE,
    GONE
}

enum class ButtonType {
    SHOW_UNREAD,
    JUMP_TO_BOTTOM
}

@Composable
fun JumpToBottom(
    enabled: Boolean,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    ChatButton(enabled, onClicked, modifier, stringResource(id = R.string.jump_to_bottom))
}

@Composable
fun JumpToUnreadMessages(
    enabled: Boolean,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    ChatButton(enabled, onClicked, modifier, stringResource(id = R.string.jump_to_unread), type = ButtonType.SHOW_UNREAD)
}

@Composable
fun ChatButton(
    enabled: Boolean,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    type: ButtonType = ButtonType.JUMP_TO_BOTTOM
) {
    val transition = updateTransition(
        if (enabled) Visibility.VISIBLE else Visibility.GONE,
        label = ""
    )
    val bottomOffset by transition.animateDp(label = "") {
        if (it == Visibility.GONE) {
            (-16).dp
        } else {
            16.dp
        }
    }
    if (bottomOffset > 0.dp) {
        ExtendedFloatingActionButton(
            icon = {
                Icon(
                    imageVector = if (type == ButtonType.JUMP_TO_BOTTOM) Icons.Filled.ArrowDownward else Icons.Filled.ArrowUpward,
                    modifier = Modifier.height(18.dp),
                    contentDescription = null,
                )
            },
            text = {
                Text(text = text)
            },
            onClick = onClicked,
            contentColor = Shadow2,
            backgroundColor = FunctionalDarkGrey,
            modifier = modifier
                .offset(x = 0.dp, y = -bottomOffset)
                .height(36.dp)
        )
    }
}