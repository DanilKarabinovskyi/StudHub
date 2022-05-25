package danyil.karabinovskyi.studenthub.features.posts.data.remote.request

data class CreateCommentRequest(
    val comment: String,
    val postId: String,
)
