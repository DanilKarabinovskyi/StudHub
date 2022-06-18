package danyil.karabinovskyi.studenthub.core.app

import android.content.res.Resources
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import danyil.karabinovskyi.studenthub.common.SnackbarManager
import danyil.karabinovskyi.studenthub.features.home.data.HomeSections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

object MainDestinations {
    const val SPLASH_ROUTE = "splash"
    const val CHOOSE_ROLE_ROUTE = "choose_role"
    const val REGISTRATION_ROUTE = "registration"
    const val LOGIN_ROUTE = "login"
    const val HOME_ROUTE = "home"
    const val CHAT_ROUTE = "chat"
    const val POSTS_ROUTE = "posts"
    const val EVENTS_ROUTE = "events"
    const val POSTS_DETAIL = "posts_detail"
    const val POSTS_CREATE_EDIT = "posts_create_edit"
}

@Composable
fun rememberStudentHubAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    imageLoader: ImageLoader,
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) =
    remember(scaffoldState, navController,imageLoader, snackbarManager, resources, coroutineScope) {
        StudentHubAppState(scaffoldState, navController, imageLoader, snackbarManager, resources, coroutineScope)
    }

@Stable
class StudentHubAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    val imageLoader: ImageLoader,
    private val snackbarManager: SnackbarManager,
    private val resources: Resources,
    coroutineScope: CoroutineScope
) {
    // Process snackbars coming from SnackbarManager
    init {
        coroutineScope.launch {
            snackbarManager.messages.collect { currentMessages ->
                if (currentMessages.isNotEmpty()) {
                    val message = currentMessages[0]
                    val text = resources.getText(message.messageId)

                    // Display the snackbar on the screen. `showSnackbar` is a function
                    // that suspends until the snackbar disappears from the screen
                    scaffoldState.snackbarHostState.showSnackbar(text.toString())
//                     Once the snackbar is gone or dismissed, notify the SnackbarManager
                    snackbarManager.setMessageShown(message.id)
                }
            }
        }
    }


    val bottomBarTabs = HomeSections.values()
    private val bottomBarRoutes = bottomBarTabs.map { it.route }

    val shouldShowBottomBar: Boolean
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route in bottomBarRoutes

    val currentRoute: String?
        get() = navController.currentDestination?.route

    fun upPress() {
        navController.navigateUp()
    }

    fun navigateToBottomBarRoute(route: String) {
        if (route != currentRoute) {
            navController.navigate(route) {
                launchSingleTop = true
                restoreState = true
                // Pop up backstack to the first destination and save state. This makes going back
                // to the start destination when pressing back in any other bottom tab.
                popUpTo(findStartDestination(navController.graph).id) {
                    saveState = true
                }
            }
        }
    }

}

private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)

private tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}

@Composable
@ReadOnlyComposable
private fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}
