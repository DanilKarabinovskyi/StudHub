package danyil.karabinovskyi.studenthub.features.auth.data.remote.entity

import com.google.gson.annotations.SerializedName

data class VerifyResponse(
        @SerializedName("verified") var verified: Boolean,
)
