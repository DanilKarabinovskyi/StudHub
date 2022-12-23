package danyil.karabinovskyi.studenthub.features.posts.domain.entity

import android.net.Uri
import danyil.karabinovskyi.studenthub.features.posts.data.remote.entity.Chunk

data class CreatePostRequest(
    val id: Int?,
    val title: String,
    val description: String,
    val tags: List<String>,
    val postImage: Uri?
)
