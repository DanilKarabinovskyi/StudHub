package danyil.karabinovskyi.studenthub.features.chat.domain.use_case

import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.core.data.ChatQuery
import danyil.karabinovskyi.studenthub.core.data.entity.User
import danyil.karabinovskyi.studenthub.features.auth.data.remote.entity.VerifyResponse
import danyil.karabinovskyi.studenthub.features.auth.domain.AuthRepository
import danyil.karabinovskyi.studenthub.features.chat.domain.ChatRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    suspend fun execute(chatQuery:ChatQuery): Resource<List<User>> {
        return repository.getUsers(chatQuery)
    }
}