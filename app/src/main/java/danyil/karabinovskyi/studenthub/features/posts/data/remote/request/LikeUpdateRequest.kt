package danyil.karabinovskyi.studenthub.features.posts.data.remote.request

data class LikeUpdateRequest(
    val isLiked: Boolean,
    val postId: Int
)
