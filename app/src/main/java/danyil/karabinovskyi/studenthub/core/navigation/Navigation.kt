package danyil.karabinovskyi.studenthub.core.navigation

import android.content.Intent
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.*
import androidx.navigation.compose.composable
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import danyil.karabinovskyi.studenthub.core.app.MainDestinations
import danyil.karabinovskyi.studenthub.features.auth.presentation.login.LoginScreen
import danyil.karabinovskyi.studenthub.features.auth.presentation.registration.RegistrationScreen
import danyil.karabinovskyi.studenthub.features.auth.presentation.splash.SplashScreen
import danyil.karabinovskyi.studenthub.features.posts.presentation.detail.PostDetailScreen

//todo replace main navigation with navigation in every feature package
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

@OptIn(ExperimentalCoilApi::class)
fun NavGraphBuilder.addPostsDetail(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    imageLoader: ImageLoader
) {
    composable(
        route = MainDestinations.POSTS_DETAIL + "/{postId}?shouldShowKeyboard={shouldShowKeyboard}",
        arguments = listOf(
            navArgument(
                name = "postId"
            ) {
                type = NavType.StringType
            },
            navArgument(
                name = "shouldShowKeyboard"
            ) {
                type = NavType.BoolType
                defaultValue = false
            }
        ),
        deepLinks = listOf(
            navDeepLink {
                action = Intent.ACTION_VIEW
                uriPattern = "https://studentHub.com/{postId}"
            }
        )
    ) {
        val shouldShowKeyboard = it.arguments?.getBoolean("shouldShowKeyboard") ?: false
        PostDetailScreen(
            scaffoldState = scaffoldState,
            onNavigateUp = navController::navigateUp,
            onNavigate = navController::navigate,
            shouldShowKeyboard = shouldShowKeyboard,
            imageLoader = imageLoader
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