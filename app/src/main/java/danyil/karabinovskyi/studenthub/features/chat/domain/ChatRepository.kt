package danyil.karabinovskyi.studenthub.features.chat.domain

import danyil.karabinovskyi.studenthub.common.model.BasicApiResponse
import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.core.data.ChatQuery
import danyil.karabinovskyi.studenthub.core.data.entity.User
import danyil.karabinovskyi.studenthub.features.chat.data.model.AddToChatRequest
import danyil.karabinovskyi.studenthub.features.chat.data.model.CreateChatRequest

interface ChatRepository {

    suspend fun createChat(
        request:CreateChatRequest
    ) : Resource<Boolean>

    suspend fun getUsers(
        chatQuery: ChatQuery
    ): Resource<List<User>>

    suspend fun addToChat(addToChatRequest: AddToChatRequest) : Resource<Boolean>
}