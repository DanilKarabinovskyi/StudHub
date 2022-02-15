package danyil.karabinovskyi.studenthub.features.auth.domain.usecase

import danyil.karabinovskyi.studenthub.common.model.SimpleResource
import danyil.karabinovskyi.studenthub.features.auth.domain.AuthRepository
import javax.inject.Inject

class AuthenticateUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): SimpleResource {
        return repository.authenticate()
    }
}