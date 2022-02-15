package danyil.karabinovskyi.studenthub.core.app;

import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarHost
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import danyil.karabinovskyi.studenthub.components.scaffold.StudentHubScaffold
import danyil.karabinovskyi.studenthub.components.snackbar.Snackbar
import danyil.karabinovskyi.studenthub.core.navigation.addLogin
import danyil.karabinovskyi.studenthub.core.navigation.addRegistration
import danyil.karabinovskyi.studenthub.ui.theme.StudentHubTheme

@Composable
fun StudentHubApp() {
    ProvideWindowInsets {
        StudentHubTheme {
            val appState = rememberStudentHubAppState()
            StudentHubScaffold(
                bottomBar = {
//                    if (appState.shouldShowBottomBar) {
//                        JetsnackBottomBar(
//                            tabs = appState.bottomBarTabs,
//                            currentRoute = appState.currentRoute!!,
//                            navigateToRoute = appState::navigateToBottomBarRoute
//                        )
//                    }
                },
                snackbarHost = {
                    SnackbarHost(
                        hostState = it,
                        modifier = Modifier.systemBarsPadding(),
                        snackbar = { snackbarData -> Snackbar(snackbarData) }
                    )
                },
                scaffoldState = appState.scaffoldState
            ) { innerPaddingModifier ->
                NavHost(
                    navController = appState.navController,
                    startDestination = MainDestinations.LOGIN_ROUTE,
                    modifier = Modifier.padding(innerPaddingModifier)
                ) {
                    studentHubNavGraph(
                        onSnackSelected = appState::navigateToSnackDetail,
                        upPress = appState::upPress,
                        appState.navController
                    )
                }
            }
        }
    }
}

private fun NavGraphBuilder.studentHubNavGraph(
    onSnackSelected: (Long, NavBackStackEntry) -> Unit,
    upPress: () -> Unit,
    navController: NavHostController
) {
    addLogin(navController = navController)
    addRegistration(navController = navController)
//    navigation(
//        route = MainDestinations.HOME_ROUTE,
//        startDestination = HomeSections.FEED.route
//    ) {
//        addHomeGraph(onSnackSelected)
//    }
//    composable(
//        "${MainDestinations.SNACK_DETAIL_ROUTE}/{${MainDestinations.SNACK_ID_KEY}}",
//        arguments = listOf(navArgument(MainDestinations.SNACK_ID_KEY) { type = NavType.LongType })
//    ) { backStackEntry ->
//        val arguments = requireNotNull(backStackEntry.arguments)
//        val snackId = arguments.getLong(MainDestinations.SNACK_ID_KEY)
//        SnackDetail(snackId, upPress)
//    }
}


