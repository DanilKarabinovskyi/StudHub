package danyil.karabinovskyi.studenthub.features.posts.data.remote.entity

import danyil.karabinovskyi.studenthub.features.posts.domain.entity.Attachment
import danyil.karabinovskyi.studenthub.features.posts.domain.entity.Post

data class PostResponse(
    val image: String,
    val text: String,
    val createdAt: String,
    val profilePictureUrl:String,
    val username: String,
    val likesCount: Int,
    val commentsCount: Int,
    val attachments: List<Attachment>?,
    val id: Int,
    val authorId: Int,
    val tags: List<String>,
    val title: String,
    val isLiked: Boolean = false,
    val isOwnPost:Boolean = false
) {
    fun mapToUI() : Post {
        return Post(
            id = id,
            authorId = authorId,
            username = username,
            imageUrl = image,
            title = title,
            profilePictureUrl = profilePictureUrl,
            description = text,
            attachments = attachments,
            likesCount = likesCount,
            commentsCount = commentsCount,
            isLiked = isLiked,
            isOwnPost = isOwnPost,
            tags = tags
        )
    }
}