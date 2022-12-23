package danyil.karabinovskyi.studenthub.features.chat.data.model

data class Member(
    val createdAt: String,
    val id: Int,
    val password: String,
    val profile: Profile,
    val profileId: Int,
    val ticket: String,
    val token: Any,
    val updatedAt: String
)