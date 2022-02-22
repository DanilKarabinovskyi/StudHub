package danyil.karabinovskyi.studenthub.features.posts.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import danyil.karabinovskyi.studenthub.components.surface.Surface
import danyil.karabinovskyi.studenthub.components.text_fields.TransparentTextField

@Composable
fun ArticlesScreen(
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        TransparentTextField(
            textLabel = "Article",
            keyboardType = KeyboardType.Number,
            keyboardActions = KeyboardActions.Default,
            imeAction = ImeAction.Default,
            onValueChange = {  }
        )
    }
}