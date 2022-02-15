package danyil.karabinovskyi.studenthub.features.auth.domain.request

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.http.Part

data class RegistrationRequest(
    @Part("password") val password: String = "",
    @Part("group") val group: String = "",
    @Part("email") val email: String = "",
    @Part("ticketImage") val photo: MultipartBody.Part? = null,
)
