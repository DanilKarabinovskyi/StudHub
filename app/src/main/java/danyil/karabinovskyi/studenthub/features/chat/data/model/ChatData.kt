package danyil.karabinovskyi.studenthub.features.chat.data.model

import danyil.karabinovskyi.studenthub.core.data.entity.Channel
import danyil.karabinovskyi.studenthub.core.data.entity.Message
import danyil.karabinovskyi.studenthub.core.data.entity.User

data class ChatData(
    val id: Int,
    val imageUrl: String?,
    val last_message: Message?,
    val members: List<Member>,
    val role: Any,
    val title: String
) {
    fun mapToChannel(): Channel {
        return Channel(
            id = id,
            type = "",
            name = title,
            image = imageUrl?:"",
            lastMessageAt = last_message?.updatedAt,
            createdAt = null,
            updatedAt = null,
            memberCount = members.size,
            lastMessage = last_message,
            messages = mutableListOf(),
            members = mutableListOf(),
            createdBy = User(),
            unreadCount = null,
            hidden = null,
            cooldown = 0,
            ownCapabilities = setOf(),
        )
    }
}

