package danyil.karabinovskyi.studenthub.core.data.entity

import android.os.Parcelable
import danyil.karabinovskyi.studenthub.features.chat.data.remote.entity.DataHolder
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int = 0,
    val ticket: String = "",
    val groupId: Int = 0,
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val imageUrl: String = "",
    val updatedAt: String = "",
    val createdAt: String = "",
    val group: DataHolder? = null,
    val faculty: DataHolder? = null,
    val university: DataHolder? = null,
) : Parcelable {
    fun fullName(): String {
        return "$firstName $lastName"
    }
}
