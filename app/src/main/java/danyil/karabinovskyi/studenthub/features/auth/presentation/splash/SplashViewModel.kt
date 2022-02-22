package danyil.karabinovskyi.studenthub.features.auth.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.common.model.UiEvent
import danyil.karabinovskyi.studenthub.features.auth.domain.usecase.AuthenticateUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authenticateUseCase: AuthenticateUseCase
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<SplashEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            delay(700)
            when(val result = authenticateUseCase()) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        if(result.data?.verified == true) SplashEvent.ToHome else SplashEvent.ToLogin
                    )
                }
                is Resource.Error -> {
                    _eventFlow.emit(
                        SplashEvent.ToLogin
                    )
                }
            }
        }
    }
}