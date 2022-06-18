package danyil.karabinovskyi.studenthub.core.domain.model

data class Comment(
    val id: Int,
    val username: String,
    val profilePictureUrl: String,
    val formattedTime: String,
    val comment: String,
    val isLiked: Boolean,
    val likeCount: Int
)
