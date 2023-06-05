package danyil.karabinovskyi.studenthub.features.posts.domain.entity

import danyil.karabinovskyi.studenthub.core.domain.model.Comment

data class Post(
    val id: Int,
    val authorId: Int,
    val username: String,
    val imageUrl: String,
    val profilePictureUrl: String,
    val description: String,
    val attachments: List<Attachment>?,
    val title: String,
    val tags: List<String>,
    var likesCount: Int,
    val commentsCount: Int,
    var isLiked: Boolean,
    val isOwnPost: Boolean = false,
    val comments: List<Comment> = listOf(),
)
