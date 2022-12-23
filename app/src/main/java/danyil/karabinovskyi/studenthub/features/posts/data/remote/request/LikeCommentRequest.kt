package danyil.karabinovskyi.studenthub.features.posts.data.remote.request

data class LikeCommentRequest(
    val isLiked: Boolean,
    val commentId: Int
)
