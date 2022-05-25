package danyil.karabinovskyi.studenthub.features.posts.data.remote.entity

import danyil.karabinovskyi.studenthub.features.posts.domain.entity.Post

data class PostsResponse(
    val chunks: List<Chunk>,
    val createdAt: String,
    val id: Int,
    val tags: List<String>,
    val title: String

){
    fun mapToUI() {
//        return Post(
//            todo
//        )
    }
}