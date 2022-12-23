package danyil.karabinovskyi.studenthub.features.posts.data.remote.entity

import danyil.karabinovskyi.studenthub.core.domain.model.Comment
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.util.*

data class CommentResponse(
    val id: Int,
    val username: String,
    val profilePictureUrl: String,
    val timeStamp: String,
    val comment: String,
    val isLiked: Boolean,
    val likeCount: Int
) {
    fun mapToUI(): Comment {
        return Comment(
            id = id,
            username = username,
            profilePictureUrl = profilePictureUrl,
            formattedTime = (SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).run {
                parse(timeStamp).toLocaleString()
            }),
            comment = comment,
            isLiked = isLiked,
            likeCount = likeCount
        )
    }
}