package danyil.karabinovskyi.studenthub.features.feed.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import danyil.karabinovskyi.studenthub.R
import danyil.karabinovskyi.studenthub.components.surface.Surface
import danyil.karabinovskyi.studenthub.components.text_fields.TransparentTextField
import danyil.karabinovskyi.studenthub.components.toolbar.StandardToolbar

@Composable
fun FeedScreen(
) {
    StandardToolbar(
        title = {
            Text(
                text = stringResource(id = R.string.feed),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground
            )
        },
        modifier = Modifier.fillMaxWidth(),
        showBackArrow = false,
        navActions = {
            IconButton(onClick = {
//                onNavigate(Screen.SearchScreen.route)
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "",
                    tint = MaterialTheme.colors.onBackground
                )
            }
        }
    )
}