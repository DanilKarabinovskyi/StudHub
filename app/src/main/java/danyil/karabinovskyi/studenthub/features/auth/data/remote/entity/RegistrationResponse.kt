package danyil.karabinovskyi.studenthub.features.auth.data.remote.entity

import com.google.gson.annotations.SerializedName

data class RegistrationResponse (
    @SerializedName("token") var token: String? = null,
)