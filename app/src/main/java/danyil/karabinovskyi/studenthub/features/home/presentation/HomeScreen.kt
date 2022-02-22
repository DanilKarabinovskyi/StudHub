package danyil.karabinovskyi.studenthub.features.home.presentation

import androidx.compose.compiler.plugins.kotlin.EmptyFunctionMetrics.composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import danyil.karabinovskyi.studenthub.features.chat.presentation.ChatScreen
import danyil.karabinovskyi.studenthub.features.dashboard.presentation.DashboardScreen
import danyil.karabinovskyi.studenthub.features.feed.presentation.FeedScreen
import danyil.karabinovskyi.studenthub.features.home.data.HomeSections
import danyil.karabinovskyi.studenthub.features.posts.presentation.ArticlesScreen

fun NavGraphBuilder.addHomeGraph(
    modifier: Modifier = Modifier
) {
    composable(HomeSections.FEED.route) { from ->
        FeedScreen()
    }
    composable(HomeSections.CHATS.route) { from ->
        ChatScreen()
    }
    composable(HomeSections.POSTS.route) { from ->
        ArticlesScreen()
    }
    composable(HomeSections.MORE.route) {
        DashboardScreen()
    }
}