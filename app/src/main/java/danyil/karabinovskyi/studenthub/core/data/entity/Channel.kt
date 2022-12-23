package danyil.karabinovskyi.studenthub.core.data.entity

import java.util.Date

data class Channel(
    var id: Int = 0,
    var type: String = "",
    var name: String = "",
    var image: String = "",
    var lastMessageAt: Date? = null,
    var createdAt: Date? = null,
    var updatedAt: Date? = null,
    var memberCount: Int = 0,
    var lastMessage: Message? = null,
    var messages: List<Message> = mutableListOf(),
    var members: List<Member> = mutableListOf(),
    var createdBy: User = User(),
    var unreadCount: Int? = null,
    var hidden: Boolean? = null,
    val cooldown: Int = 0,
    var ownCapabilities: Set<String> = setOf(),
)  {

    val initials = name

    val lastUpdated: Date?
        get() = lastMessageAt?.takeIf { createdAt == null || it.after(createdAt) } ?: createdAt

    val hasUnread: Boolean
        get() = unreadCount?.let { it > 0 } ?: false

}
