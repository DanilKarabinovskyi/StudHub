package danyil.karabinovskyi.studenthub.features.auth.domain.usecase

import danyil.karabinovskyi.studenthub.features.auth.domain.AuthRepository
import danyil.karabinovskyi.studenthub.features.auth.domain.screen_result.RegisterResult
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(password: RequestBody, email: RequestBody, group: RequestBody,
                                file: MultipartBody.Part) : RegisterResult {
        val result = authRepository.register(password,email, group,
            file)

        return RegisterResult(
            result = result
        )
    }
}