package danyil.karabinovskyi.studenthub.features.posts.data.remote.request

data class CreateCommentRequest(
    val text: String,
    val postId: Int,
)
