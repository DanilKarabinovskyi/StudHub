package danyil.karabinovskyi.studenthub.features.profile.data.remote

import danyil.karabinovskyi.studenthub.common.model.BasicApiResponse
import danyil.karabinovskyi.studenthub.features.chat.data.remote.entity.UserResponse
import retrofit2.http.GET

interface UserApi {

    @GET("user/me")
    suspend fun getUser() : BasicApiResponse<UserResponse>
}