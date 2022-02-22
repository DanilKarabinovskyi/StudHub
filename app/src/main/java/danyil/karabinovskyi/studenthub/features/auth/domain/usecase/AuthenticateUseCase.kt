package danyil.karabinovskyi.studenthub.features.auth.domain.usecase

import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.common.model.SimpleResource
import danyil.karabinovskyi.studenthub.features.auth.data.remote.entity.VerifyResponse
import danyil.karabinovskyi.studenthub.features.auth.domain.AuthRepository
import javax.inject.Inject

class AuthenticateUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): Resource<VerifyResponse> {
        return repository.authenticate()
    }
}