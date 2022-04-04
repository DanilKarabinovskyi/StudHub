package danyil.karabinovskyi.studenthub.components.text_fields

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import danyil.karabinovskyi.studenthub.R
import danyil.karabinovskyi.studenthub.core.model.states.StandardTextFieldState
import danyil.karabinovskyi.studenthub.features.auth.presentation.registration.RegistrationEvent
import danyil.karabinovskyi.studenthub.ui.theme.SpaceLarge


@Composable
fun SendTextField(
    state: StandardTextFieldState,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    hint: String = "",
    canSendMessage: Boolean = true,
    isLoading: Boolean = false,
    focusRequester: FocusRequester = FocusRequester()
) {
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .fillMaxWidth()
            .padding(SpaceLarge),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TransparentTextField(
            text = state.text,
            onValueChange = onValueChange,
            backgroundColor = MaterialTheme.colors.background,
            modifier = Modifier
                .weight(1f),
            hint = hint,
            focusRequester = focusRequester,
            textLabel = "Comment",
            keyboardType = KeyboardType.Text,
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            imeAction = ImeAction.Next
        )
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .size(24.dp),
                strokeWidth = 2.dp
            )
        } else {
            IconButton(
                onClick = onSend,
                enabled = state.error == null || !canSendMessage
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    tint = if (state.error == null && canSendMessage) {
                        MaterialTheme.colors.primary
                    } else MaterialTheme.colors.background,
                    contentDescription = stringResource(id = R.string.send_comment)
                )
            }
        }
    }
}