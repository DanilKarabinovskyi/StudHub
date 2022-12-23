package danyil.karabinovskyi.studenthub.features.chat.domain.use_case

import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.features.chat.data.model.CreateChatRequest
import danyil.karabinovskyi.studenthub.features.chat.domain.ChatRepository
import javax.inject.Inject

class CreateChatUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    suspend fun execute(createChatRequest: CreateChatRequest): Resource<Boolean> {
        return repository.createChat(createChatRequest)
    }
}