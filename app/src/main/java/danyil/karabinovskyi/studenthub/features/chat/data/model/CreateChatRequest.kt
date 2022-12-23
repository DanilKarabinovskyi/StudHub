package danyil.karabinovskyi.studenthub.features.chat.data.model

import android.net.Uri

data class CreateChatRequest(
    val title: String,
    val userIds: List<Int>,
    val fileUri: Uri?
)
