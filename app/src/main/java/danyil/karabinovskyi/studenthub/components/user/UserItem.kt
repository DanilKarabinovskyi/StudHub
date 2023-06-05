package danyil.karabinovskyi.studenthub.components.user

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import danyil.karabinovskyi.studenthub.components.avatar.UserAvatar
import danyil.karabinovskyi.studenthub.core.data.entity.User
import danyil.karabinovskyi.studenthub.ui.theme.*

@Composable
internal fun UserItem(
    modifier: Modifier = Modifier,
    user: User,
    onItemSelected: (id: Int) -> Unit = {},
) {
    val selected = remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .clickable {
                onItemSelected(user.id)
                selected.value = !selected.value
            }
            .background(
                color = if (selected.value)
                    StudentHubTheme.colorsV2.highlight
                else
                    StudentHubTheme.colorsV2.background
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserAvatar(
                modifier = Modifier
                    .size(ProfilePictureSizeSmall)
                    .padding(SpaceSmall),
                user = user,
                contentDescription = user.fullName()
            )

            Text(
                text = user.fullName(),
                style = StudentHubTheme.typography.title4,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                color = StudentHubTheme.colorsV2.textHighEmphasis,
            )
        }
        if(selected.value){
            Icon(
                modifier = Modifier.padding(end = SpaceMedium),
                imageVector = Icons.Default.Check,
                tint = StudentHubTheme.colors.iconSecondary,
                contentDescription = "Selected Icon"
            )
        }
    }
}
