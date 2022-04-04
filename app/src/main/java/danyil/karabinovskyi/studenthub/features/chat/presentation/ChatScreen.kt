package danyil.karabinovskyi.studenthub.features.chat.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import danyil.karabinovskyi.studenthub.R
import danyil.karabinovskyi.studenthub.components.toolbar.StandardToolbar

@Composable
fun ChatScreen() {
    StandardToolbar(
        title = {
            Text(
                text = stringResource(id = R.string.all_chats),
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