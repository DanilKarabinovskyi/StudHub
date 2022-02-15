package danyil.karabinovskyi.studenthub.features.auth.presentation.registration

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import danyil.karabinovskyi.studenthub.common.extensions.convertToByteArray
import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.features.auth.domain.request.RegistrationRequest
import danyil.karabinovskyi.studenthub.features.auth.domain.usecase.RegistrationUseCase
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import java.io.File
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase
): ViewModel() {

    val state: MutableState<RegistrationState> = mutableStateOf(RegistrationState())

    fun register(
        group: String,
        email: String,
        photo: File?,
        password: String,
        confirmPassword: String
    ) {
        val a = photo?.let { it  }
        val requestFile1: RequestBody? = a?.let {
            RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                it
            )
        }
        val body: MultipartBody.Part? =
            requestFile1?.let { MultipartBody.Part.createFormData("ticketImage", a.name, it) }
        if (body != null) {
            registrate(
                password.toRequestBody(), email.toRequestBody(), group.toRequestBody(), body

            )
        }
    }

    fun hideErrorDialog() {
        state.value = state.value.copy(
            errorMessage = null
        )
    }

    fun registrate(password:RequestBody,email:RequestBody, group: RequestBody,
                   file: MultipartBody.Part){
        viewModelScope.launch {
            val registerResult = registrationUseCase(
                password,email, group, file
            )
            when(registerResult.result) {
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

    fun convertImage(bitmap: ImageBitmap): ByteArray {
        val bitmapToConvert = bitmap.asAndroidBitmap()
        return bitmapToConvert.convertToByteArray()
    }
}