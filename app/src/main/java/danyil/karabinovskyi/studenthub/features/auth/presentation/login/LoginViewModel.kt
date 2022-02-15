package danyil.karabinovskyi.studenthub.features.auth.presentation.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.features.auth.domain.request.LoginRequest
import danyil.karabinovskyi.studenthub.features.auth.domain.usecase.LoginUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    val state: MutableState<LoginState> = mutableStateOf(LoginState())

    fun onLogin(code: String, password: String) {
        login(loginRequest = LoginRequest(code, password))
    }

    private fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            state.value = state.value.copy(displayProgressBar = true)
            val loginResult = loginUseCase(
                loginRequest
            )
            state.value = state.value.copy(displayProgressBar = false)
//            if(loginResult.emailError != null) {
//                _emailState.value = emailState.value.copy(
//                    error = loginResult.emailError
//                )
//            }
//            if(loginResult.passwordError != null) {
//                _passwordState.value = _passwordState.value.copy(
//                    error = loginResult.passwordError
//                )
//            }
            when (loginResult.result) {
                is Resource.Success -> {
                    state.value = state.value.copy(successLogin = true)
                }
                is Resource.Error -> {
                    state.value = state.value.copy(successLogin = false)

                }
            }
        }
    }

    fun hideErrorDialog() {
        state.value = state.value.copy(
            errorMessage = null
        )
    }

}