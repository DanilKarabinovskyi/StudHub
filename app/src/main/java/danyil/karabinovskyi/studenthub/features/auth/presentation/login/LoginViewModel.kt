package danyil.karabinovskyi.studenthub.features.auth.presentation.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.common.model.UiEvent
import danyil.karabinovskyi.studenthub.core.model.states.StandardTextFieldState
import danyil.karabinovskyi.studenthub.features.auth.domain.request.LoginRequest
import danyil.karabinovskyi.studenthub.features.auth.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _codeState = mutableStateOf(StandardTextFieldState())
    val codeState: State<StandardTextFieldState> = _codeState

    private val _passwordState = mutableStateOf(StandardTextFieldState())
    val passwordState: State<StandardTextFieldState> = _passwordState

    val state: MutableState<LoginState> = mutableStateOf(LoginState())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private fun login() {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)
            val loginResult = loginUseCase(
                LoginRequest(
                    code = codeState.value.text,
                    password = codeState.value.text
                )
            )
            state.value = state.value.copy(isLoading = false)
            _codeState.value = codeState.value.copy(
                error = loginResult.codeError
            )
            _passwordState.value = _passwordState.value.copy(
                error = loginResult.passwordError
            )

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

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredCode -> {
                _codeState.value = codeState.value.copy(
                    text = event.code
                )
            }
            is LoginEvent.EnteredPassword -> {
                _passwordState.value = passwordState.value.copy(
                    text = event.password
                )
            }
            is LoginEvent.Login -> {
                login()
            }
            else -> {}
        }
    }

}