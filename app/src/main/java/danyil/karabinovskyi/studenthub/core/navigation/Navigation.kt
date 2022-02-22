package danyil.karabinovskyi.studenthub.core.navigation

import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import danyil.karabinovskyi.studenthub.core.app.MainDestinations
import danyil.karabinovskyi.studenthub.features.auth.presentation.login.LoginScreen
import danyil.karabinovskyi.studenthub.features.auth.presentation.login.LoginViewModel
import danyil.karabinovskyi.studenthub.features.auth.presentation.registration.RegistrationScreen
import danyil.karabinovskyi.studenthub.features.auth.presentation.registration.RegistrationViewModel
import danyil.karabinovskyi.studenthub.features.auth.presentation.splash.SplashScreen

fun NavGraphBuilder.addLogin(
    navController: NavHostController
) {
    composable(
        route = MainDestinations.LOGIN_ROUTE
    ) {
        LoginScreen(
            rememberScaffoldState(),
            onLogin = {
                navigateWithPop(
                    pop = MainDestinations.LOGIN_ROUTE,
                    destination = MainDestinations.HOME_ROUTE,
                    inclusive = true,
                    navController =  navController)
            },
            onNavigateToRegister = {
                navController.navigate(MainDestinations.REGISTRATION_ROUTE)
            },
        )

    }
}


fun NavGraphBuilder.addSplash(
    navController: NavHostController
) {
    composable(
        route = MainDestinations.SPLASH_ROUTE
    ) {
        SplashScreen(
            NavigateToLogin = {
                navigateWithPop(
                    pop = MainDestinations.SPLASH_ROUTE,
                    destination = MainDestinations.LOGIN_ROUTE,
                    inclusive = true,
                    navController =  navController)
            },
            NavigateToHome = {
                navigateWithPop(
                    pop = MainDestinations.SPLASH_ROUTE,
                    destination = MainDestinations.HOME_ROUTE,
                    inclusive = true,
                    navController =  navController)
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

private fun navigateWithPop(
    pop:String,
    destination:String,
    inclusive: Boolean = true,
    navController: NavHostController){

    navController.popBackStack(
        route = pop,
        inclusive = inclusive
    )
    navController.navigate(destination)
}