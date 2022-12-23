package danyil.karabinovskyi.studenthub.features.auth.domain.usecase


import danyil.karabinovskyi.studenthub.common.model.AuthError
import danyil.karabinovskyi.studenthub.features.auth.domain.AuthRepository
import danyil.karabinovskyi.studenthub.features.auth.domain.request.LoginRequest
import danyil.karabinovskyi.studenthub.features.auth.domain.screen_result.LoginResult
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: AuthRepository) {

    suspend operator fun invoke(loginRequest: LoginRequest): LoginResult {
        val codeError = if(loginRequest.code.isBlank()) AuthError.FieldEmpty else null
        val passwordError = if(loginRequest.password.isBlank()) AuthError.FieldEmpty else null

        if(codeError != null || passwordError != null) {
            return LoginResult(codeError, passwordError)
        }

        return LoginResult(
            result = repository.login(loginRequest)
        )
    }
}