package danyil.karabinovskyi.studenthub.features.chat.data.remote.entity

import danyil.karabinovskyi.studenthub.core.data.entity.User

data class UserResponse(
    val createdAt: String,
    val first_name: String,
    val id: Int,
    val email: String,
    val imageUrl: String,
    val last_name: String,
    val ticket: String,
    val updatedAt: String,
    val group: DataHolder,
    val faculty: DataHolder,
    val university: DataHolder,
){
    fun toUser():User{
        return  User(
            id = id,
            groupId = 0,
            email  = "",
            firstName = first_name,
            lastName = last_name,
            imageUrl = imageUrl,
            updatedAt = updatedAt,
            createdAt = createdAt,
            group = group,
            faculty = faculty,
            university = university,
        )
    }
}