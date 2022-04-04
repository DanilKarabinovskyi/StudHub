package danyil.karabinovskyi.studenthub.features.posts.domain.entity

import danyil.karabinovskyi.studenthub.features.posts.data.remote.entity.Chunk

data class CreatePostRequest(
    val chunks: List<Chunk>,
    val createdAt: String,
    val tags: List<String>,
    val title: String
)
