package danyil.karabinovskyi.studenthub.core.navigation

import androidx.compose.runtime.LaunchedEffect
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
){
    composable(
        route = MainDestinations.LOGIN_ROUTE
    ){
        val viewModel: LoginViewModel = hiltViewModel()
        val email = viewModel.state.value.code
        val password = viewModel.state.value.password

        if(viewModel.state.value.successLogin){
            LaunchedEffect(key1 = Unit){
                navController.navigate(
                    MainDestinations.HOME_ROUTE + "/$email" + "/$password"
                ){
                    popUpTo(MainDestinations.LOGIN_ROUTE){
                        inclusive = true
                    }
                }
            }
        } else {
            LoginScreen(
                state = viewModel.state.value,
                onLogin = viewModel::onLogin,
                onNavigateToRegister = {
                    navController.navigate(MainDestinations.REGISTRATION_ROUTE)
                },
                onDismissDialog = viewModel::hideErrorDialog
            )
        }
    }
}

fun NavGraphBuilder.addRegistration(
    navController: NavHostController
){
    composable(
        route = MainDestinations.REGISTRATION_ROUTE
    ){
        val viewModel: RegistrationViewModel = hiltViewModel()

        RegistrationScreen(
            state = viewModel.state.value,
            onRegister = viewModel::register,
            onBack = {
                navController.navigateUp()
            },
            onDismissDialog = viewModel::hideErrorDialog
        )
    }
}