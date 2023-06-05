package danyil.karabinovskyi.studenthub.features.posts.domain.entity

import android.net.Uri

data class CreatePostRequest(
    val id: Int?,
    val title: String,
    val description: String,
    val tags: List<String>,
    val postImage: Uri?,
    val attachments: List<String?>
)
