package danyil.karabinovskyi.studenthub.features.posts.data.remote.entity

data class PostsResponse(
    val chunks: List<Chunk>,
    val createdAt: String,
    val id: Int,
    val tags: List<String>,
    val title: String
)