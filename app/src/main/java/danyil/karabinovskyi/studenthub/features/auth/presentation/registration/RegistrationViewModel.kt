package danyil.karabinovskyi.studenthub.features.auth.presentation.registration

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import danyil.karabinovskyi.studenthub.common.extensions.convertToByteArray
import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.common.model.UiEvent
import danyil.karabinovskyi.studenthub.core.model.states.StandardTextFieldState
import danyil.karabinovskyi.studenthub.features.auth.domain.usecase.RegistrationUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase
) : ViewModel() {

    val state: MutableState<RegistrationState> = mutableStateOf(RegistrationState())

    private val _emailState = mutableStateOf(StandardTextFieldState())
    val emailState: State<StandardTextFieldState> = _emailState

    private val _groupState = mutableStateOf(StandardTextFieldState())
    val groupState: State<StandardTextFieldState> = _groupState

    private val _passwordState = mutableStateOf(StandardTextFieldState())
    val passwordState: State<StandardTextFieldState> = _passwordState

    private val _confirmPasswordState = mutableStateOf(StandardTextFieldState())
    val confirmPasswordState: State<StandardTextFieldState> = _confirmPasswordState

    private val _photoState = mutableStateOf(File(""))
    val photoState: State<File> = _photoState

    private val _registerState = mutableStateOf(RegistrationState())
    val registerState: State<RegistrationState> = _registerState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.EnteredGroup -> {
                _groupState.value = _groupState.value.copy(
                    text = event.value
                )
            }
            is RegistrationEvent.EnteredEmail -> {
                _emailState.value = _emailState.value.copy(
                    text = event.value
                )
            }
            is RegistrationEvent.EnteredPassword -> {
                _passwordState.value = _passwordState.value.copy(
                    text = event.value
                )
            }
            is RegistrationEvent.EnteredConfirmedPassword -> {
                _confirmPasswordState.value = _confirmPasswordState.value.copy(
                    text = event.value
                )
            }
            is RegistrationEvent.SavePhoto -> {
                _photoState.value = event.value
            }
            is RegistrationEvent.Register -> {
                registrate()
            }
            else -> {}
        }
    }

    private fun registrate() {

        viewModelScope.launch {
            val registerResult = gotFileAsRequestBody()?.let {
                registrationUseCase(
                    _passwordState.value.text.toRequestBody(),
                    _emailState.value.text.toRequestBody(),
                    groupState.value.text.toRequestBody(),
                    it
                )
            }
            when (registerResult?.result) {
                is Resource.Success -> {
                    val a = registerResult.result
                }
                is Resource.Error -> {
                    val a = registerResult.result
                }
                null -> {
                }
            }
        }
    }

    private fun gotFileAsRequestBody(): MultipartBody.Part {
        val requestFile: RequestBody = _photoState.value.let {
            RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                it
            )
        }
        return requestFile.let {
            MultipartBody.Part.createFormData(
                "ticketImage",
                _photoState.value.name,
                it
            )
        }

    }

    fun convertImage(bitmap: ImageBitmap): ByteArray {
        val bitmapToConvert = bitmap.asAndroidBitmap()
        return bitmapToConvert.convertToByteArray()
    }
}