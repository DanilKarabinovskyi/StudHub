package danyil.karabinovskyi.studenthub.features.posts.domain.entity

data class Attachment(
    val extension: String,
    val filename: String,
    val id: Int,
    val postId: Int,
    val url: String
)