package danyil.karabinovskyi.studenthub.features.chat.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import danyil.karabinovskyi.studenthub.components.surface.Surface
import danyil.karabinovskyi.studenthub.components.text_fields.TransparentTextField

@Composable
fun ChatScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        TransparentTextField(
            textLabel = "Chat",
            keyboardType = KeyboardType.Number,
            keyboardActions = KeyboardActions.Default,
            imeAction = ImeAction.Default,
            onValueChange = { }
        )
    }
}