package danyil.karabinovskyi.studenthub.features.posts.data.remote.entity

import danyil.karabinovskyi.studenthub.features.posts.domain.entity.Post

data class PostResponse(
    val image: String,
    val text: String,
    val createdAt: String,
    val id: Int,
    val authorId: Int,
    val tags: List<String>,
    val title: String

) {
    fun mapToUI() : Post {
        return Post(
            id = id,
            userId = text,
            username = text,
            imageUrl = text,
            profilePictureUrl = text,
            description = text,
            likeCount = id,
            commentCount = id,
            isLiked = false,
            isOwnPost = false
        )
    }
}