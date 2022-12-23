package danyil.karabinovskyi.studenthub.features.posts.data.remote.request

data class LikePostRequest(
    val isLiked: Boolean,
    val postId: Int
)
