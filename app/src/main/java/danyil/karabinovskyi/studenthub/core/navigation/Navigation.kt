package danyil.karabinovskyi.studenthub.core.navigation

import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import danyil.karabinovskyi.studenthub.core.app.MainDestinations
import danyil.karabinovskyi.studenthub.features.auth.presentation.login.LoginScreen
import danyil.karabinovskyi.studenthub.features.auth.presentation.login.LoginViewModel
import danyil.karabinovskyi.studenthub.features.auth.presentation.registration.RegistrationScreen
import danyil.karabinovskyi.studenthub.features.auth.presentation.registration.RegistrationViewModel

fun NavGraphBuilder.addLogin(
    navController: NavHostController
) {
    composable(
        route = MainDestinations.LOGIN_ROUTE
    ) {
        LoginScreen(
            rememberScaffoldState(),
            onLogin = {
                navController.navigate(MainDestinations.HOME_ROUTE)
            },
            onNavigateToRegister = {
                navController.navigate(MainDestinations.REGISTRATION_ROUTE)
            },
        )

    }
}

@OptIn(ExperimentalComposeUiApi::class)
fun NavGraphBuilder.addRegistration(
    navController: NavHostController
) {
    composable(
        route = MainDestinations.REGISTRATION_ROUTE
    ) {

        RegistrationScreen(
            onBack = {
                navController.navigateUp()
            }
        )
    }
}