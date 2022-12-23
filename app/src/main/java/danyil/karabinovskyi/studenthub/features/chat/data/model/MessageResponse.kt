package danyil.karabinovskyi.studenthub.features.chat.data.model

import danyil.karabinovskyi.studenthub.core.data.entity.Message
import danyil.karabinovskyi.studenthub.core.data.entity.User
import java.text.SimpleDateFormat
import java.util.*

class MessageResponse(
    val id: Int = 0,
    val author: AuthorResponse,
    val userReadIds: List<Int> = listOf<Int>(),
    val message: String = "",
    val image:String = "",
    val createdAt: String? = null,
    val updatedAt: Date? = null
) {
    fun toMessage(): Message {
        return Message(
            id = id,
            message = message,
            type = "",
            image = image,
            isMessageRead = userReadIds.isNotEmpty(),
            syncStatus = null,
            createdAt = createdAt.parseTime(),
            updatedAt = updatedAt,
            deletedAt = "",
            user = User(
                id = author.id,
                firstName = author.first_name,
                lastName = author.last_name,
                imageUrl = author.imageUrl
            ),
        )
    }
}

fun String?.parseTime():String{
    return (SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).run {
        val formatter = SimpleDateFormat("dd-MMM-yy hh:mm:ss");
        val strDate = formatter.format(parse(this@parseTime));
        strDate
    })
}