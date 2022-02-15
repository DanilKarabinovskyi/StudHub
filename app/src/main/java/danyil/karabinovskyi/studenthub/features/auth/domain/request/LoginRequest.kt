package danyil.karabinovskyi.studenthub.features.auth.domain.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("ticket") val code: String,
    @SerializedName("password") val password: String
)