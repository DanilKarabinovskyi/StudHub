package danyil.karabinovskyi.studenthub.features.home.data

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import danyil.karabinovskyi.studenthub.R

enum class HomeSections(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    FEED(R.string.home_feed, Icons.Outlined.Home, "home/feed"),
    POSTS(R.string.home_posts, Icons.Outlined.Feed, "home/posts"),
    CHATS(R.string.home_chats, Icons.Outlined.Chat, "home/chats"),
    MORE(R.string.home_more, Icons.Outlined.MoreVert, "home/more")
}