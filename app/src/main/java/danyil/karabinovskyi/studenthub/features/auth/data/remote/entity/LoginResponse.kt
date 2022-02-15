package danyil.karabinovskyi.studenthub.features.auth.data.remote.entity

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token") var token: String = "null"
)