package danyil.karabinovskyi.studenthub.features.chat.data.model

data class Profile(
    val createdAt: String,
    val email: String,
    val first_name: String,
    val groupId: Int,
    val id: Int,
    val imageUrl: String,
    val last_name: String,
    val updatedAt: String
)