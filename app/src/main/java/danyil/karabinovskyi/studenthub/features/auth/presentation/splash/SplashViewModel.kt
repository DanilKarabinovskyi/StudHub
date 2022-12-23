package danyil.karabinovskyi.studenthub.features.auth.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.common.model.UiEvent
import danyil.karabinovskyi.studenthub.core.data.AppData
import danyil.karabinovskyi.studenthub.core.domain.usecase.GetAppDataUseCase
import danyil.karabinovskyi.studenthub.features.auth.domain.usecase.AuthenticateUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authenticateUseCase: AuthenticateUseCase,
    private val getAppDataUseCase: GetAppDataUseCase
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<SplashEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var verified = false

    init {
        viewModelScope.launch {
            getAppData()
            delay(500)
            when(val result = authenticateUseCase()) {
                is Resource.Success -> {
                    verified = result.data?.verified == true
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

    private suspend fun getAppData(){
        when(val result = getAppDataUseCase.execute()){
            is Resource.Success -> {
                AppData.postTags = result.data?.formValues?.postTags!!
            }
            else -> {}
        }
    }
}