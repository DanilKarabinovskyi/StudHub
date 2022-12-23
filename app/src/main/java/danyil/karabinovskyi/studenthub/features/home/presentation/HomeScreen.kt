package danyil.karabinovskyi.studenthub.features.home.presentation

import androidx.compose.material.ScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import coil.ImageLoader
import danyil.karabinovskyi.studenthub.features.chat.presentation.ChatsScreen
import danyil.karabinovskyi.studenthub.features.dashboard.presentation.DashboardScreen
import danyil.karabinovskyi.studenthub.features.feed.presentation.FeedScreen
import danyil.karabinovskyi.studenthub.features.home.data.HomeSections
import danyil.karabinovskyi.studenthub.features.posts.presentation.PostsScreen


fun NavGraphBuilder.addHomeGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    imageLoader: ImageLoader
) {

    composable(HomeSections.FEED.route) { from ->
        FeedScreen()
    }
    composable(HomeSections.CHATS.route) { from ->
        ChatsScreen(onNavigate = navController::navigate)
    }
    composable(HomeSections.POSTS.route) { from ->
        PostsScreen(
            onNavigate = navController::navigate,
            imageLoader = imageLoader
        )
    }
    composable(HomeSections.MORE.route) {
        DashboardScreen()
    }
}