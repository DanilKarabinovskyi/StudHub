package danyil.karabinovskyi.studenthub.core.data.entity

import okhttp3.MultipartBody
import retrofit2.http.Part

data class UserRequest(
    @Part("password") val password: String = "",
    @Part("group") val group: String = "",
    @Part("email") val email: String = "",
    @Part("ticketImage") val photo: MultipartBody.Part? = null,
    )
