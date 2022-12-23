package danyil.karabinovskyi.studenthub.features.chat.domain.use_case

import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.core.data.ChatQuery
import danyil.karabinovskyi.studenthub.core.data.entity.User
import danyil.karabinovskyi.studenthub.features.chat.data.model.AddToChatRequest
import danyil.karabinovskyi.studenthub.features.chat.domain.ChatRepository
import javax.inject.Inject

class AddUserToChatUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    suspend fun execute(addToChatRequest: AddToChatRequest): Resource<Boolean> {
        return repository.addToChat(addToChatRequest)
    }
}