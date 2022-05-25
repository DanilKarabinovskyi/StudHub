package danyil.karabinovskyi.studenthub.features.posts.data.remote.request

data class LikeUpdateRequest(
    val parentId: String,
    val parentType: Int
)
