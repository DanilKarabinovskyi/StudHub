package danyil.karabinovskyi.studenthub.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.features.auth.domain.usecase.AuthenticateUseCase
import danyil.karabinovskyi.studenthub.features.auth.domain.usecase.RegistrationUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authenticateUseCase: AuthenticateUseCase
): ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            when(authenticateUseCase()) {
                is Resource.Success -> {
                    _isLoading.value = false
                }
                is Resource.Error -> {
                    _isLoading.value = false
                }
            }
        }
    }
}