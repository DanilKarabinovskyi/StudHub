package danyil.karabinovskyi.studenthub.core.app;

import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import danyil.karabinovskyi.studenthub.components.bottom_bar.BottomNavBar
import danyil.karabinovskyi.studenthub.components.scaffold.StudentHubScaffold
import danyil.karabinovskyi.studenthub.components.snackbar.Snackbar
import danyil.karabinovskyi.studenthub.core.navigation.addLogin
import danyil.karabinovskyi.studenthub.core.navigation.addRegistration
import danyil.karabinovskyi.studenthub.core.navigation.addSplash
import danyil.karabinovskyi.studenthub.features.home.data.HomeSections
import danyil.karabinovskyi.studenthub.features.home.presentation.addHomeGraph
import danyil.karabinovskyi.studenthub.ui.theme.StudentHubTheme

@Composable
fun StudentHubApp() {
    ProvideWindowInsets {
        StudentHubTheme {
            val appState = rememberStudentHubAppState()
            StudentHubScaffold(
                bottomBar = {
                    if (appState.shouldShowBottomBar) {
                        BottomNavBar(
                            tabs = appState.bottomBarTabs,
                            currentRoute = appState.currentRoute!!,
                            navigateToRoute = appState::navigateToBottomBarRoute
                        )
                    }
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
                    startDestination = MainDestinations.SPLASH_ROUTE,
                    modifier = Modifier.padding(innerPaddingModifier)
                ) {
                    studentHubNavGraph(
                        upPress = appState::upPress,
                        appState.navController
                    )
                }
            }
        }
    }
}

private fun NavGraphBuilder.studentHubNavGraph(
    upPress: () -> Unit,
    navController: NavHostController
) {
    addSplash(navController = navController)
    addLogin(navController = navController)
    addRegistration(navController = navController)
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = HomeSections.FEED.route
    ) {
        addHomeGraph()
    }
}


